package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.utils.MD5Utils;
import com.example.admin.jjypznglxt.utils.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class LoadingActivity extends Activity {

    private EditText et_username;//用户名
    private EditText et_password;//密码
    private EditText et_hospitalcode;//医院代码
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        String savedusername = sp.getString("username", "");
        String savedPwd = sp.getString("password", "");
        String savedhospitalcode = sp.getString("hospitalcode", "");
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        et_hospitalcode = (EditText) findViewById(R.id.et_hospitalcode);
        if(!TextUtils.isEmpty(savedhospitalcode) && !TextUtils.isEmpty(savedusername) && !TextUtils.isEmpty(savedPwd)){
            et_username.setText(savedusername);
//            et_password.setText(savedPwd);
            et_hospitalcode.setText(savedhospitalcode);
        }
        copyDB("jjxtc_gkzx.db");
    }
    public void onClick(View view){
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String hospitalcode = et_hospitalcode.getText().toString().trim();
        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(hospitalcode)){
            Toast.makeText(this, "代码或账号或密码为空", Toast.LENGTH_SHORT).show();
            return;
        }else if(!Util.isMobile(username)){
            Toast.makeText(this, "请输入正确格式的用户名", Toast.LENGTH_SHORT).show();
            return;
        }else if(password.length() < 6){
            Toast.makeText(this, "密码长度不能少于六位", Toast.LENGTH_SHORT).show();
            return;
        }else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("username", username);
            editor.putString("password", MD5Utils.ecoder(password));
            editor.putString("hospitalcode", hospitalcode);
            editor.commit();
            startActivity(new Intent(this, MainActivity.class));
//            startActivity(new Intent(this, ShowResultActivity.class));//测试数据库出入库
            finish();
        }
    }

    /**
     * 进入应用拷贝数据库文件到应用目录下供使用
     * @param dbName
     */
    private void copyDB(String dbName) {
        File file = new File(getFilesDir(), dbName);
        if (file.exists() && file.length() > 0) {
            System.out.println("数据库已经存在");
        } else {
            try {
                InputStream is = getAssets().open(dbName);
                FileOutputStream fos = new FileOutputStream(file);
                int len = 0;
                byte[] b = new byte[1024];
                while ((len = is.read(b)) != -1) {
                    fos.write(b, 0, len);
                }
                is.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
