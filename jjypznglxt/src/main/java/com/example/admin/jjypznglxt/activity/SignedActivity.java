package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.jjypznglxt.R;

/**
 * add by hzn
 */
public class SignedActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signed);
    }

    /**
     * 返回主页
     * @param view
     */
    public void returnHome(View view){
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}