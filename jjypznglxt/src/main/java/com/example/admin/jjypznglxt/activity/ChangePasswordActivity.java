package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.utils.MD5Utils;


public class ChangePasswordActivity extends Activity {

    private EditText et_oldpwd;
    private EditText et_newpwd;
    private EditText et_confirmPwd;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        et_oldpwd = (EditText) findViewById(R.id.et_oldpwd);
        et_newpwd = (EditText) findViewById(R.id.et_newpwd);
        et_confirmPwd = (EditText) findViewById(R.id.et_confirmPwd);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void ok(View view){
        String newPwd = et_newpwd.getText().toString().trim();
        String confirmPwd = et_confirmPwd.getText().toString().trim();
        String oldpwd = et_oldpwd.getText().toString().trim();
        String pwd = sp.getString("password", "");//还未做解密操作
        if(!pwd.equals(MD5Utils.ecoder(oldpwd))){
            Toast.makeText(this, "原密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if(!newPwd.equals(confirmPwd)){
                Toast.makeText(this, "新密码不匹配，请重新输入", Toast.LENGTH_SHORT).show();
                return;
            }else {
                if(newPwd.length()>=6) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", MD5Utils.ecoder(newPwd));
                    startActivity(new Intent(this, ChangedActivity.class));
                    finish();
                }else {
                    Toast.makeText(this, "密码长度不能低于六位", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }

    /**
     * 取消按钮
     * @param view
     */
    public void cancel(View view){
        finish();
    }
}
