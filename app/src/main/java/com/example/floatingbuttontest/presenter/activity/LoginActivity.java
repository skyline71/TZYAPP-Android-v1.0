package com.example.floatingbuttontest.presenter.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.floatingbuttontest.R;
import com.example.floatingbuttontest.view.widget.EditTextWithDel;
import com.example.floatingbuttontest.view.widget.CircularImage;

/**
 * Created by dell on 2015/5/24.
 */
public class LoginActivity extends Activity {
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;

    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;

    private CheckBox rememberPass;
    private CheckBox auto_login;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = (EditTextWithDel) findViewById(R.id.account);
        passwordEdit = (EditTextWithDel) findViewById(R.id.password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        auto_login = (CheckBox) findViewById(R.id.auto_login);
        login = (Button) findViewById(R.id.login);
        CircularImage cover_user_photo = (CircularImage) findViewById(R.id.cover_user_photo);
        cover_user_photo.setImageResource(R.drawable.img_2);
        boolean isRemeber = pref.getBoolean("remember_password", false);
        if (isRemeber) {
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
            if (pref.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                auto_login.setChecked(true);
                //跳转界面
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        }

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.commit();
                    dialog = ProgressDialog.show(LoginActivity.this, "Doing something", "Please wait...", true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invaild", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rememberPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rememberPass.isChecked()) {

                    System.out.println("记住密码已选中");
                    pref.edit().putBoolean("ISCHECK", true).commit();

                } else {

                    System.out.println("记住密码没有选中");
                    pref.edit().putBoolean("ISCHECK", false).commit();

                }

            }
        });

        auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (auto_login.isChecked()) {
                    System.out.println("自动登录已选中");
                    pref.edit().putBoolean("AUTO_ISCHECK", true).commit();

                } else {
                    System.out.println("自动登录没有选中");
                    pref.edit().putBoolean("AUTO_ISCHECK", false).commit();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }
}
