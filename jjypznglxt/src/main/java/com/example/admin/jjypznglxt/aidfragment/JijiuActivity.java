package com.example.admin.jjypznglxt.aidfragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.jjypznglxt.R;

public class JijiuActivity extends FragmentActivity {
    private int index;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jijiu);
        index = this.getIntent().getIntExtra("position",0);
        type = getIntent().getStringExtra("type");
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initpage();
    }


    private void initpage() {
        Bundle data = new Bundle();
        data.putInt("index", index);
        data.putString("type", type);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        AidFirstFragment aidFirstFragment = new AidFirstFragment();
       aidFirstFragment.setArguments(data);
        transaction.replace(R.id.main_content, aidFirstFragment);
       transaction.commit();
//        Log.d("MainActivity","23533");
    }
}
