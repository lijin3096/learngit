package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.pro.Images;
import com.example.admin.jjypznglxt.zxing.camera.CaptureActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    private GridView gridView;
    private ImageAdapter adapter;
    private int[] Beijing = {R.drawable.u6,R.drawable.u11,R.drawable.u13
            ,R.drawable.u18,R.drawable.u55,R.drawable.u57};
    private int[] Tubiao = {R.drawable.u69,R.drawable.u49,R.drawable.u51,
            R.drawable.u53,R.drawable.u65,R.drawable.u67};
    private String[] title = {"扫描条码","急救","信息通知","药物补给","修改密码","急救小推车签收"};
    private long mExitTime;//add by hzn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView)findViewById(R.id.zhuye);
        adapter = new ImageAdapter(title,Beijing,Tubiao,this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
//                        startActivity(new Intent(getApplicationContext(),CaptureActivity.class));
                        Intent intent = new Intent(getApplicationContext(),CaptureActivity.class);
                        intent.putExtra("type", "saomiao");
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(getApplicationContext(),FirstAidActivity.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("type", "急救");
                        intent2.putExtras(bundle2);
                        startActivity(intent2);
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(),InformationNoticeActivity.class));
                        break;
                    case 3:
                        Intent intent4 = new Intent(getApplicationContext(), CaptureActivity.class);
                        intent4.putExtra("type", "supply");
                        startActivity(intent4);
                        break;
                    case 4:
                        startActivity(new Intent(getApplicationContext(),ChangePasswordActivity.class));
                        break;
                    case 5:
//                        startActivity(new Intent(getApplicationContext(),CaptureActivity.class));
                        startActivity(new Intent(getApplicationContext(),SignSmallCarActivity.class));
                        break;
                }
            }
        });
    }

    private class ImageAdapter extends BaseAdapter{
        private LayoutInflater inflater;
        private List<Images> imagers;

        public ImageAdapter(String[] title,int[] Beijing,int[] Tubiao,Context context) {
            super();
            imagers = new ArrayList<Images>();
            inflater = LayoutInflater.from(context);
            for (int i = 0;i < Beijing.length;i++){
            Images images = new Images(Beijing[i],Tubiao[i],title[i]);
                imagers.add(images);
            }
        }

        @Override
        public int getCount() {
            return Beijing.length;
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
                convertView = inflater.inflate(R.layout.dtail,null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.beijing);
                viewHolder.imageView1 = (ImageView)convertView.findViewById(R.id.tubiao);
                viewHolder.textView = (TextView)convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
            viewHolder.textView.setText(imagers.get(position).getTitle());

            viewHolder.imageView.setImageResource(imagers.get(position).getBeijing());
            viewHolder.imageView1.setImageResource(imagers.get(position).getTubiao());
            return convertView;
        }
        private class ViewHolder{
            ImageView imageView;
            ImageView imageView1;
            TextView textView;
        }
    }

    /**
     * 按兩次返回退出程序   add by hzn
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
          if (keyCode == KeyEvent.KEYCODE_BACK) {
              if ((System.currentTimeMillis() - mExitTime) > 2000) {
                  Object mHelperUtils;
                  Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                  mExitTime = System.currentTimeMillis();
              } else {
                  finish();
              }
              return true;
          }
        return super.onKeyDown(keyCode, event);
     }
}
