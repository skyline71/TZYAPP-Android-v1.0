package com.example.floatingbuttontest.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.floatingbuttontest.R;
import com.example.floatingbuttontest.model.ContentModel;
import com.example.floatingbuttontest.presenter.activity.MainActivity;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.xml.parsers.SAXParserFactory;

/**
 * Created by dell on 2015/5/31.
 */
public class UpdateContent {
        /* 下载中 */
        private static final int DOWNLOAD = 1;
        /* 下载结束 */
        private static final int DOWNLOAD_FINISH = 2;
        /* 保存解析的XML信息 */
        HashMap<String, String> mHashMap;
        /* 下载保存路径 */
        private String mSavePath;
        /* 记录进度条数量 */
        private int progress;
        /* 是否取消更新 */
        private boolean cancelUpdate = false;
        private boolean isParse = false;
        private String endresult;

        private Context mContext;
        /* 更新进度条 */
        private ProgressBar mProgress;
        private Dialog mDownloadDialog;
        private final String str = "http://hongyan.cqupt.edu.cn/app/cyxbsAppUpdate.xml";

    private Handler mHandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    // 正在下载
                    case DOWNLOAD:
                        // 设置进度条位置
                        mProgress.setProgress(progress);
                        break;
                    case DOWNLOAD_FINISH:
                        // 安装文件
                        installApk();
                        break;
                    default:
                        break;
                }
            }

            ;
        };

        public UpdateContent(Context context) {
            this.mContext = context;
        }

        /**
         * 检测软件更新
         */
        public void checkUpdate() {
            if (isUpdate()) {
                // 显示提示对话框
                showNoticeDialog();
            } else {
                Toast.makeText(mContext, R.string.soft_update_no, Toast.LENGTH_LONG).show();
            }
        }

        /**
         * 检查软件是否有更新版本
         *
         * @return
         */
        private boolean isUpdate()
        {
            // 获取当前软件版本
            int versionCode = getVersionCode(mContext);
            // 把version.xml放到网络上，然后获取文件信息
            //InputStream inStream = ParseXmlService.class.getClassLoader().getResourceAsStream(str);
            DownloadContent downloadContent = new DownloadContent(mContext);
            downloadContent.downLoad(str,new DownloadContent.DownLoadCallback() {
                        @Override
                        public void loadContent(String result) {
                            endresult = result;
                        }
                    });
            InputStream inputStream = new ByteArrayInputStream(endresult.getBytes());
            ParseXmlService service = new ParseXmlService();
            try
            {
                mHashMap = service.parseXml(inputStream);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            if (null != mHashMap)
            {
                int serviceCode = Integer.valueOf(mHashMap.get("versionCode"));
                if (serviceCode > versionCode)
                {
                    return true;
                }
            }
            return false;
        }

        private int getVersionCode(Context context) {
            int versionCode = 0;
            try {
                // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
                versionCode = context.getPackageManager().getPackageInfo("com.example.floatingbuttontest", 0).versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return versionCode;
        }

        private void showNoticeDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.soft_update_title);
            builder.setMessage(R.string.soft_update_info);
            builder.setPositiveButton(R.string.soft_update_updatebtn, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // 显示下载对话框
                    showDownloadDialog();
                }
            });
            builder.setNegativeButton(R.string.soft_update_later, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            Dialog noticeDialog = builder.create();
            noticeDialog.show();
        }

        private void showDownloadDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle(R.string.soft_updating);
            final LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.softupdate_progress, null);
            mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
            builder.setView(v);
            builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    cancelUpdate = true;
                }
            });
            mDownloadDialog = builder.create();
            mDownloadDialog.show();
            downloadApk();
        }

        /**
         * 下载apk文件
         */
        private void downloadApk() {
            new downloadApkThread().start();
        }

        private class downloadApkThread extends Thread {
            @Override
            public void run() {
                try {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        String sdpath = Environment.getExternalStorageDirectory() + "/";
                        mSavePath = sdpath + "download";
                        URL url = new URL(mHashMap.get("apkURL"));
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.connect();
                        int length = conn.getContentLength();
                        InputStream is = conn.getInputStream();
                        File file = new File(mSavePath);
                        if (!file.exists()) {
                            file.mkdir();
                        }
                        File apkFile = new File(mSavePath, mHashMap.get("versionName"));
                        FileOutputStream fos = new FileOutputStream(apkFile);
                        int count = 0;
                        byte buf[] = new byte[1024];
                        do {
                            int numread = is.read(buf);
                            count += numread;
                            progress = (int) (((float) count / length) * 100);
                            mHandler.sendEmptyMessage(DOWNLOAD);
                            if (numread <= 0) {
                                mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                                break;
                            }
                            fos.write(buf, 0, numread);
                        } while (!cancelUpdate);
                        fos.close();
                        is.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mDownloadDialog.dismiss();
            }
        }

        private void installApk() {
            File apkfile = new File(mSavePath, mHashMap.get("versionName"));
            if (!apkfile.exists()) {
                return;
            }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
            mContext.startActivity(i);
        }
    }

