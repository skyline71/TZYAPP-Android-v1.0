package com.example.floatingbuttontest.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dell on 2015/5/31.
 */
public class DownloadContent {
        private ProgressDialog dialog;

        public DownloadContent(Context context){
            dialog = new ProgressDialog(context);
            dialog.setTitle("提示");
            dialog.setMessage("正在获取网络数据……");
        }
        public void downLoad(final String path,final DownLoadCallback callback){
            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message msg){
                    super.handleMessage(msg);
                    String result = (String) msg.obj;
                    callback.loadContent(result);
                    if(msg.what == 1) {
                        dialog.dismiss();

                    }

                }
            };
            class MyThread implements Runnable{

                @Override
                public void run() {
                    //HttpClient httpClient = new DefaultHttpClient();
                    HttpURLConnection connection = null;
                    //HttpPost httpPost = new HttpPost(path);
                    try{
                        URL url = new URL(path);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(8000);
                        connection.setReadTimeout(8000);
                        InputStream in = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine())!=null){
                            response.append(line);
                            Message message =Message.obtain();
                            message.obj = response.toString();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally {
                        if(connection == null) {
                            connection.disconnect();
                        }
                    }

                }
            }
            new Thread(new MyThread()).start();
            dialog.show();

        }

        public interface DownLoadCallback{
            public void loadContent(String result);
        }
    }

