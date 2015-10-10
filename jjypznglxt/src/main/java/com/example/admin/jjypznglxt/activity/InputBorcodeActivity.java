package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.jjypznglxt.R;

public class InputBorcodeActivity extends Activity {

    private EditText et_borcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_borcode);
        et_borcode = (EditText) findViewById(R.id.et_borcode);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void query(View view) {
        String borcode = et_borcode.getText().toString().trim();
        if(TextUtils.isEmpty(borcode)){
            Toast.makeText(this, "条码数字编号为空", Toast.LENGTH_SHORT).show();
            return;
        }else {
            Intent intent = new Intent(this, ShowResultActivity.class);
            intent.putExtra("result", borcode);
            startActivity(intent);
        }
    }
}
