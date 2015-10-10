package com.example.admin.jjypznglxt.dbcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.admin.jjypznglxt.bean.SmallCar;

/**
 * Created by hzn on 2015/9/30.
 */
public class SmallCarTableControl {

    private Context mContext;

    private SmallCarTableControl(Context context){
        this.mContext = context;
    }

    private static SmallCarTableControl smallCarTableControl = null;

    public static SmallCarTableControl getInstance(Context context){
        if(smallCarTableControl == null) {
            synchronized (MedicineConntrol.class){
                if(smallCarTableControl == null){
                    smallCarTableControl = new SmallCarTableControl(context);
                }
            }
        }
        return smallCarTableControl;
    }

    /**
     * 往SmallCar表中添加一条数据
     * @param _id
     * @param _barcode
     * @param _num
     */
    public void add(String _id, String _barcode, String _num){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("_barcode", _barcode);
        values.put("_num", _num);
        db.insert("SmallCar", null, values);
        db.close();
        Toast.makeText(mContext, "插入成功", Toast.LENGTH_SHORT);
    }

    /**
     * 根据小推车编号从SmallCar表中删除一条数据
     * @param _num
     */
    public void delete(String _num){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("SmallCar", " _num = ? ", new String[]{_num});
        db.close();
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT);
    }

    public void update(){

    }

    /**
     * 根据条码查询小推车信息
     * @param _barcode
     */
    public SmallCar queryByBarcode(String _barcode){
        SmallCar smallCar = new SmallCar();
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        Cursor c = db.query("SmallCar", null, " _barcode = ? ", new String[]{ _barcode }, null, null, null, null);
        while(c.moveToNext()){
            smallCar.setId(c.getInt(c.getColumnIndex("_id")));
            smallCar.setBarcode(c.getString(c.getColumnIndex("_barcode")));
            smallCar.setNum(c.getString(c.getColumnIndex("_num")));
            smallCar.setEgyname(c.getString(c.getColumnIndex("_egyname")));
        }
//        Toast.makeText(mContext, smallCar.toString(), Toast.LENGTH_SHORT).show();
        db.close();
        return smallCar;
    }
}
