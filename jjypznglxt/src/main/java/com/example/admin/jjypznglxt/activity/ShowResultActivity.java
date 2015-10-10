package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.bean.MedicineInfo;
import com.example.admin.jjypznglxt.dbcontrol.EmeryTableControl;
import com.example.admin.jjypznglxt.dbcontrol.MedicineConntrol;

import java.util.List;


public class ShowResultActivity extends Activity {

    private EmeryTableControl etc = EmeryTableControl.getInstance(this);
    private MedicineConntrol ec = MedicineConntrol.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_result);
        TextView tv = (TextView) findViewById(R.id.tv);
        String type = getIntent().getStringExtra("type");
        if(TextUtils.isEmpty(type)){
            type = "未识别的类型";
        }
        String result = getIntent().getStringExtra("result");
        if(TextUtils.isEmpty(result)){
            result = "未查询到结果";
        }
        tv.setText("类型：" + type + "\n" + "结果：" + result);
        tv.setTextSize(20);
        tv.setTextColor(Color.BLACK);

        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void click1(View view){
        etc.add(10, "摔跤1");
        etc.add(11, "摔跤2");
//        ec.add("00012", "感冒药2", "10", 7, 2, "234213232", "2016/12/31");
//        ec.add("00013", "感冒药3", "10", 5, 2, "234213234", "2016/12/31");
    }

    public void click2(View view){
        etc.delete("摔跤1");
//        ec.deleteById("00012");
    }

    public void click3(View view){
        etc.update("摔跤2", "摔跤3");
//        ec.updateById("00013", "20");
    }

    public void click4(View view){
        List<String> result = etc.queryAll();
//        MedicineInfo info = ec.queryByBarcode("234213234");
    }
}
