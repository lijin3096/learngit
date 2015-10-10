package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.jjypznglxt.R;

public class SignSmallCarActivity extends Activity {

    private ImageView iv_back;
    private ListView lv_sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_smallcar);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lv_sign = (ListView) findViewById(R.id.lv_sign);
        lv_sign.setAdapter(new MyAdapter());
        lv_sign.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1){
                    Intent intent = new Intent(SignSmallCarActivity.this, SignProjectActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 确认签收
     * @param view
     */
    public void confirmSign(View view){
        Intent intent = new Intent(this, SignedActivity.class);
        startActivity(intent);
        finish();//确认签收后要销毁当前activity 即按返回不能再返回到这个页面
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView != null){
                holder = (ViewHolder) convertView.getTag();
            }else {
                convertView = View.inflate(SignSmallCarActivity.this, R.layout.item_sign, null);
                holder = new ViewHolder();
                holder.tv_sign_left = (TextView) convertView.findViewById(R.id.tv_sign_left);
                holder.tv_sign_right = (TextView) convertView.findViewById(R.id.tv_sign_right);
                convertView.setTag(holder);
            }
            if(position == 0){
                holder.tv_sign_left.setText("小推车编号为：");
                holder.tv_sign_right.setText("0126");
                holder.tv_sign_right.setTextColor(Color.BLACK);
                holder.tv_sign_left.setTextColor(Color.BLACK);
            }else if(position == 1){
                holder.tv_sign_right.setText("心脏骤停");
                holder.tv_sign_right.setTextColor(Color.BLUE);
                holder.tv_sign_left.setTextColor(Color.BLUE);
            }else if(position == 2){
                holder.tv_sign_right.setText("溺水");
            }else if(position == 3){
                holder.tv_sign_right.setText("中暑");
            }else if(position == 4){
                holder.tv_sign_right.setText("动物伤害");
            }
            return convertView;
        }
    }

    private static class ViewHolder{
        TextView tv_sign_left;
        TextView tv_sign_right;
    }
}
