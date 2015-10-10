package com.example.admin.jjypznglxt.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.bean.MedicineInfo;
import com.example.admin.jjypznglxt.bean.SmallCar;
import com.example.admin.jjypznglxt.dbcontrol.MedicineConntrol;
import com.example.admin.jjypznglxt.dbcontrol.SmallCarTableControl;
import com.example.admin.jjypznglxt.zxing.camera.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * add by hzn
 */
public class MedicineSupplyActivity extends Activity {

    private ListView lv_medicine_supply;
    private ImageView iv_back;
    private List<MedicineInfo> supplyMedicines = new ArrayList<MedicineInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_supply);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        String type = getIntent().getStringExtra("type");
        checkSupply();
        if(type.equals("buyao")){

        }
        lv_medicine_supply = (ListView) findViewById(R.id.lv_medicine_supply);
        MyAdapter myAdapter = new MyAdapter();
//        Toast.makeText(this, supplyMedicines.size()+"", Toast.LENGTH_SHORT).show();
        lv_medicine_supply.setAdapter(myAdapter);
//        myAdapter.notifyDataSetChanged();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 查询需要补给的药品信息
     */
    private void checkSupply() {
        String result = getIntent().getStringExtra("result");
        SmallCar smallCar = SmallCarTableControl.getInstance(this).queryByBarcode(result);
        List<MedicineInfo> medicineInfos = MedicineConntrol.getInstance(this).queryByCarId(smallCar.getId() + "");
        for(MedicineInfo medicineInfo : medicineInfos){
            int supply = medicineInfo.get_count() - medicineInfo.getMinCount();
            if(supply < 0){
                supplyMedicines.add(medicineInfo);//1
            }
        }
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return supplyMedicines.size() + 1;//0
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
                convertView = View.inflate(MedicineSupplyActivity.this, R.layout.lv_item_medicine_supply, null);
                holder = new ViewHolder();
                holder.medicine_name = (TextView) convertView.findViewById(R.id.medicine_name);
                holder.drawer_id = (TextView) convertView.findViewById(R.id.drawer_id);
                holder.supply_num = (TextView) convertView.findViewById(R.id.supply_num);
                holder.display = (TextView) convertView.findViewById(R.id.display);
                holder.supply = (Button) convertView.findViewById(R.id.supply);
                convertView.setTag(holder);
            }
            if(position == 0){//第一条标题背景为紫色
                convertView.setBackground(getResources().getDrawable(R.drawable.login_bg));
                holder.display.setVisibility(View.VISIBLE);
                holder.display.setTextColor(Color.WHITE);
                holder.supply.setVisibility(View.GONE);
                holder.medicine_name.setText("药品名称");
                holder.medicine_name.setTextColor(Color.WHITE);
                holder.drawer_id.setText("抽屉编号");
                holder.drawer_id.setTextColor(Color.WHITE);
                holder.supply_num.setText("补给数量");
                holder.supply_num.setTextColor(Color.WHITE);
            }else {
                holder.medicine_name.setText(supplyMedicines.get(position - 1).get_name());
                holder.drawer_id.setText(supplyMedicines.get(position - 1).get_drawerId() + "");
                holder.supply_num.setText(supplyMedicines.get(position - 1).getMinCount() - supplyMedicines.get(position - 1).get_count() + "");
            }

            holder.supply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MedicineSupplyActivity.this, CaptureActivity.class);
                    intent.putExtra("type", "supplyMedicine");
                    startActivity(intent);
                }
            });

            return convertView;
        }
    }

    private static class ViewHolder{
        TextView medicine_name;
        TextView drawer_id;
        TextView supply_num;
        TextView display;
        Button supply;
    }
}
