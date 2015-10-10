package com.example.admin.jjypznglxt.dbcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.admin.jjypznglxt.bean.MedicineInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzn on 2015/9/30.
 */
public class MedicineConntrol {

    private Context mContext;

    private MedicineConntrol(Context context){
        this.mContext = context;
    }

    private static MedicineConntrol medicineConntrol = null;

    public static MedicineConntrol getInstance(Context context){
        if(medicineConntrol == null) {
            synchronized (MedicineConntrol.class){
                if(medicineConntrol == null){
                    medicineConntrol = new MedicineConntrol(context);
                }
            }
        }
        return medicineConntrol;
    }

    /**
     * 往medicine表中插入一条数据
     * @param _id
     * @param _name
     * @param _count
     * @param _carId
     * @param _drawerId
     * @param _mBarcode
     * @param _proDate
     */
    public void add(String _id, String _name, String _count, int _carId, int _drawerId, String _mBarcode, String _proDate){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("_name", _name);
        values.put("_count", _count);
        values.put("_carId", _carId);
        values.put("_drawerId", _drawerId);
        values.put("_mBarcode", _mBarcode);
        values.put("_proDate", _proDate);
        db.insert("medicine", null, values);
        db.close();
        Toast.makeText(mContext, "插入成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据药品id删除medicine表中一条数据
     * @param _id
     */
    public void deleteById(String _id){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("medicine", " _id = ? ", new String[]{_id});
        db.close();
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据药品名称删除medicine表中一条数据
     * @param _name
     */
    public void deleteByName(String _name){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("medicine", " _name = ? ", new String[]{_name});
        db.close();
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据药品id修改药品数量
     * @param _id
     * @param _count
     */
    public void updateById(String _id, String _count){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_count", _count);
        db.update("medicine", values, " _id = ? ", new String[]{_id});
        db.close();
        Toast.makeText(mContext, "修改成功,当前药品数量更正为" + _count, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据药品名称修改药品数量
     * @param _name
     * @param _count
     */
    public void updateByName(String _name, String _count){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_count", _count);
        db.update("medicine", values, " _name = ? ", new String[]{_name});
        db.close();
        Toast.makeText(mContext, "修改成功,当前药品数量更正为" + _count, Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据条码查询medicine对应药品信息
     * @param _mBarcode  传入参数药品条码字符串
     * @return  返回当前条码对应的药品信息对象
     */
    public MedicineInfo queryByBarcode(String _mBarcode){
        MedicineInfo info = new MedicineInfo();
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("medicine", null, " _mbarcode = ? ", new String[]{_mBarcode}, null, null, null);
        while (cursor.moveToNext()){
            String _id = cursor.getString(cursor.getColumnIndex("_id"));
            info.set_id(_id);

            String _name = cursor.getString(cursor.getColumnIndex("_name"));
            info.set_name(_name);

            int _count = cursor.getInt(cursor.getColumnIndex("_count"));
            info.set_count(_count);

            int _carId = cursor.getInt(cursor.getColumnIndex("_carId"));
            info.set_carId(_carId);

            int _drawerId = cursor.getInt(cursor.getColumnIndex("_drawerId"));
            info.set_drawerId(_drawerId);

            String _mbarcode = cursor.getString(cursor.getColumnIndex("_mbarcode"));
            info.set_mbarcode(_mbarcode);

            String _proDate = cursor.getString(cursor.getColumnIndex("_proDate"));
            info.set_proDate(_proDate);

            int minCount = cursor.getInt(cursor.getColumnIndex("minCount"));
            info.setMinCount(minCount);
        }
        db.close();
        Toast.makeText(mContext, info.toString(), Toast.LENGTH_SHORT).show();
        return info;
    }

    /**
     * 根据小推车id查询属于该小推车的药品信息
     * @param _carId
     */
    public List<MedicineInfo> queryByCarId(String _carId){
        List<MedicineInfo> medicineInfos = new ArrayList<MedicineInfo>();
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("medicine", new String[]{"_proDate"}, " _carId = ? ", new String[]{_carId}, null, null, null);
        while(cursor.moveToNext()){
            MedicineInfo info = new MedicineInfo();

            String _id = cursor.getString(cursor.getColumnIndex("_id"));
            info.set_id(_id);

            String _name = cursor.getString(cursor.getColumnIndex("_name"));
            info.set_name(_name);

            int _count = cursor.getInt(cursor.getColumnIndex("_count"));
            info.set_count(_count);

            int carId = cursor.getInt(cursor.getColumnIndex("_carId"));
            info.set_carId(carId);

            int _drawerId = cursor.getInt(cursor.getColumnIndex("_drawerId"));
            info.set_drawerId(_drawerId);

            String _mbarcode = cursor.getString(cursor.getColumnIndex("_mbarcode"));
            info.set_mbarcode(_mbarcode);

            String _proDate = cursor.getString(cursor.getColumnIndex("_proDate"));
            info.set_proDate(_proDate);

            int minCount = cursor.getInt(cursor.getColumnIndex("minCount"));
            info.setMinCount(minCount);

            medicineInfos.add(info);
        }
//        Toast.makeText(mContext, medicineInfos.get(0).toString(), Toast.LENGTH_SHORT).show();
        db.close();
        return medicineInfos;
    }
}
