package com.example.admin.jjypznglxt.aidfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.admin.jjypznglxt.R;
import com.example.admin.jjypznglxt.activity.FirstAidResult;
import com.example.admin.jjypznglxt.activity.MainActivity;
import com.example.admin.jjypznglxt.zxing.camera.CaptureActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AidFirstFragment extends Fragment {
    private TextView textView;
  private ListViewAdapter adapter;
    private View view;
    private int index;
    private ListView listView;
    private List<String> jiemian = new ArrayList<>();
    private List<List<String>> liebiao = new ArrayList<>();
    private List<String> Heart = new ArrayList<>() ;//心脏骤停
    private List<String> Down = new ArrayList<>();//溺亡
    private List<String> Shock = new ArrayList<>();//休克
    private List<String> Heartdis = new ArrayList<>();//心律失常
    private List<String> Zhiqiguan = new ArrayList<>();//支气管
    private List<String> Guomin = new ArrayList<>();//过敏
    private List<String> Hot = new ArrayList<>();//中暑
    private List<String> Du = new ArrayList<>();//急性中毒
    private List<String> Animainjure = new ArrayList<>();//过敏伤害
    private String[] aid = {"心脏骤停","溺亡","休克","心律失常","支气管哮喘","过敏反应"
            ,"中暑","急性中毒","过敏伤害"};
    private String type;
    public AidFirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       Bundle bundle = getArguments();
        index = bundle.getInt("index");
        type = bundle.getString("type");
        String a = String.valueOf(index);
//        Log.d("MainActivity", a);
        initList();
    }
    private void initList() {
        String car1 = "0126";
        String car2 = "4302";
        String car3 = "4218";
        String car4 = "3291";
        String car5 = "8473";
        String car6 = "9482";

        Heart.add(car1);
        Heart.add(car3);
        Heart.add(car5);
        liebiao.add(Heart);
        Down.add(car2);
        Down.add(car4);
        Down.add(car6);
        liebiao.add(Down);
        Shock.add(car1);
        Shock.add(car4);
        Shock.add(car5);
        Shock.add(car6);
        liebiao.add(Shock);
        Heartdis.add(car2);
        Heartdis.add(car3);
        Heartdis.add(car4);
        liebiao.add(Heartdis);
        Zhiqiguan.add(car3);
        Zhiqiguan.add(car4);
        Zhiqiguan.add(car5);
        liebiao.add(Zhiqiguan);
        Guomin.add(car2);
        Guomin.add(car5);
        Guomin.add(car6);
        liebiao.add(Guomin);
        Hot.add(car3);
        Hot.add(car4);
        Hot.add(car5);
        Hot.add(car6);
        liebiao.add(Hot);
        Du.add(car2);
        Du.add(car6);
        liebiao.add(Du);
        Animainjure.add(car3);
        Animainjure.add(car1);
        Animainjure.add(car5);
        liebiao.add(Animainjure);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_aid_first, container, false);
        Button scan = (Button) view.findViewById(R.id.scan);
        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("急救项目", "心脏骤停");
                intent.putExtra("type", type);
                bundle.putSerializable("cars", (Serializable)liebiao.get(index));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
       initView();
//        Log.d("MainActivity","jsahf");
        return view;
    }

    private void initView() {

        jiemian = liebiao.get(index);
        String s = aid[index];
        String a = String.valueOf(jiemian.size());
        textView = (TextView)view.findViewById(R.id.xiangmumingcheng);
        textView.setText(s);
        listView = (ListView)view.findViewById(R.id.xiangmuneirong);
        adapter = new ListViewAdapter(getActivity().getApplication());
        listView.setAdapter(adapter);
    }


    private class ListViewAdapter extends BaseAdapter {
        private LayoutInflater inflater;

        public ListViewAdapter(Context context) {
            super();
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return jiemian.size();
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
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = inflater.inflate(R.layout.aid_detail,null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView)convertView.findViewById(R.id.cart);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)convertView.getTag();
            }
           viewHolder.textView.setText(jiemian.get(position));
            return convertView;
        }
        private class ViewHolder{
            TextView textView;
        }
    }

}
