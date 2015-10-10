package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.pro.iformaiton;

import java.util.ArrayList;
import java.util.List;


public class InformationNoticeActivity extends Activity {
    private ListView listView;
    private InformationAdapter adapter;
    private String[] drug = {"0932号小推车的心脏骤停的码多分","0821号小推车的心律衰竭的码多分",
            "7362号小推车的溺水的码激素","0428号小推车的糖尿病的码多分"};
    private String[] description
            = {"药物即将过期","药物即将过期","药物已过期","药物药品不足"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_notice);
        listView = (ListView)findViewById(R.id.information);
        adapter = new InformationAdapter(drug,description,this);
        listView.setAdapter(adapter);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 底部确定按钮
     * @param view
     */
    public void confirm(View view){
        finish();
    }

    private class InformationAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<iformaiton> informations;
        public InformationAdapter(String[] drug,String[] descip,Context context){
            super();
            informations = new ArrayList<iformaiton>();
            inflater = LayoutInflater.from(context);
            for (int i = 0;i < drug.length;i++){
                iformaiton mation = new iformaiton(drug[i],descip[i]);
                informations.add(mation);
            }
        }


        @Override
        public int getCount() {
            return drug.length;
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
                convertView =inflater.inflate(R.layout.infomation_detail,null);
                viewHolder = new ViewHolder();
                viewHolder.textView1 = (TextView)convertView.findViewById(R.id.drug);
                viewHolder.textView2 = (TextView)convertView.findViewById(R.id.time);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.textView1.setText(informations.get(position).getDrug());
            viewHolder.textView2.setText(informations.get(position).getDescip());
            return convertView;
        }
        private class ViewHolder{
            TextView textView1;
            TextView textView2;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_information_notice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
