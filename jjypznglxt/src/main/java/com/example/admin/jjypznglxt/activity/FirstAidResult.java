package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.dbcontrol.SmallCarTableControl;
import com.example.admin.jjypznglxt.zxing.camera.CaptureActivity;

import java.util.List;

public class FirstAidResult extends Activity {

    private TextView car_name;
    private TextView tv_car_num;
    private TextView tv_success;
    private TextView tv_car_id;
    private ListView lv_usage;
    private String type;
    private String result;
    private MyAdapter myAdapter;
    private Boolean num = false;//是否是急救所需药品的标志  true为是  false为不是
    private int mPosition = 0;//所扫药品是listview中哪一条目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid_result);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        result = bundle.getString("result");
        String aid = bundle.getString("急救项目");
        if(type.equals("急救")) {
            List<String> useableCar = (List<String>) bundle.getSerializable("cars");
        String ss = "";
        for (String s : useableCar){
            ss += s;
        }
        Toast.makeText(this, ss, Toast.LENGTH_SHORT).show();
        }
        // ToDo 根据扫描结果得到小推车编号 拆useableCar字符串判断该小推车编号是否在内
        car_name = (TextView) findViewById(R.id.car_name);
        tv_car_num = (TextView) findViewById(R.id.tv_car_num);
        tv_success = (TextView) findViewById(R.id.tv_success);
        tv_car_id = (TextView) findViewById(R.id.tv_car_id);
        lv_usage = (ListView) findViewById(R.id.lv_usage);
        if(myAdapter == null){
            myAdapter = new MyAdapter();
        }else {
            myAdapter.notifyDataSetChanged();
        }
        lv_usage.setAdapter(myAdapter);
        if(!TextUtils.isEmpty(result)) {
            tv_car_num.setText(SmallCarTableControl.getInstance(this).queryByBarcode(result).getNum());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 扫码按钮
     * @param view
     */
    public void button(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.putExtra("type", "saoyao2");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 0:
                num = data.getBooleanExtra("num", false);
                mPosition = data.getIntExtra("position", 0);
                myAdapter.notifyDataSetChanged();
                break;
        }
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            //根据服务端返回的用药信息确定有多少个条目 此处测试用四个
            return 4;
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
            if(convertView == null){
                convertView = View.inflate(FirstAidResult.this, R.layout.usage_item, null);
                holder = new ViewHolder();
                holder.tv_usage = (TextView) convertView.findViewById(R.id.tv_usage);
                holder.tv_drawerId = (TextView) convertView.findViewById(R.id.tv_drawerId);
                holder.cb_meccount = (CheckBox) convertView.findViewById(R.id.cb_meccount);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            if(position == 1){
                holder.tv_usage.setText("2.胺碘酮150～300mg");
                holder.tv_drawerId.setText("所在抽屉编号为：2");
            }else if(position == 2){
                holder.tv_usage.setText("3.利多卡因1.0～1.5mg／kg");
                holder.tv_drawerId.setText("所在抽屉编号为：3");
            }else if(position == 3){
                holder.tv_usage.setText("4.硫酸镁1～2g");
                holder.tv_drawerId.setText("所在抽屉编号为：4");
            }else if(position == 0){
                holder.tv_usage.setText("1.静脉注射肾上腺素lmg／次，每3～5分钟l次");
                holder.tv_drawerId.setText("所在抽屉编号为：1");
            }
            holder.cb_meccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        buttonView.setText("已用药量：1");
                    }else {
                        buttonView.setText("已用药量：0");
                    }
                }
            });
            if(num && mPosition == position){
                holder.cb_meccount.setChecked(true);
            }
            return convertView;
        }
    }

    private static class ViewHolder{
        TextView tv_usage;
        TextView tv_drawerId;
        CheckBox cb_meccount;
    }

}
