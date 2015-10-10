package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.aidfragment.JijiuActivity;
import com.example.admin.jjypznglxt.pro.FirstAid;

import java.util.ArrayList;
import java.util.List;


public class FirstAidActivity extends Activity {
    private ListView listView;
    private FirstAidAdapter adapter;
    private String[] aid = {"心脏骤停","溺亡","休克","心律失常","支气管哮喘","过敏反应"
            ,"中暑","急性中毒","过敏伤害"};
    private String[] aid2 = {"心脏骤停","溺亡","休克","心律失常","支气管哮喘","过敏反应"
            ,"急性中毒", "动物性伤害", "创伤"};
    private String type;
    private String[] cartnumb = {"1046","1093","4325","5250"};
    private String useableCar = cartnumb[0] +"-"+ cartnumb[1] + "-" + cartnumb[2] + "-" + cartnumb[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getString("type");
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = (ListView)findViewById(R.id.aidxiangmu);
        adapter = new FirstAidAdapter(aid, this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FirstAidActivity.this, JijiuActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("type", type);
                startActivity(intent);
            }
        });
    }

    private class FirstAidAdapter extends BaseAdapter{
        private List<FirstAid> firstAids;
        private LayoutInflater inflater;

        public FirstAidAdapter(String[] aid,Context context){
            super();
            firstAids = new ArrayList<FirstAid>();
            inflater = LayoutInflater.from(context);
            for (int i = 0;i < aid.length;i++){
                FirstAid firstAid = new FirstAid(aid[i]);
                firstAids.add(firstAid);
            }
        }
        @Override
        public int getCount() {
            return aid.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.firstaid_dtail,null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView)convertView.findViewById(R.id.aidname);
                viewHolder.point = (ImageView)convertView.findViewById(R.id.point);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.textView.setText(firstAids.get(position).getName());
            return convertView;
        }
        private class  ViewHolder{
            TextView textView;
            ImageView point;
        }
    }
}
