package com.example.admin.jjypznglxt.dbcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.admin.jjypznglxt.bean.MedicineUsage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/9/30.
 */
public class UsageTableControl {
    private Context mcontext;

    public UsageTableControl(Context mcontext) {
        this.mcontext = mcontext;
    }

    private static UsageTableControl usageTableControl = null;

    public static UsageTableControl getInstance(Context context){
        if(usageTableControl == null) {
            synchronized (MedicineConntrol.class){
                if(usageTableControl == null){
                    usageTableControl = new UsageTableControl(context);
                }
            }
        }
        return usageTableControl;
    }

    /**
     * 往Usage表中添加一条数据
     * @param _id
     * @param _mecId
     * @param _drawerId
     * @param _position
     * @param _usage
     */
    public void add(int _id,String _mecId,int _drawerId,
                    int _position,String _usage){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_id",_id);
        values.put("_mecId",_mecId);
        values.put("_drawerId",_drawerId);
        values.put("_position",_position);
        values.put("_usage",_usage);
        db.insert("Usage", null, values);
        db.close();
    }

    /**
     * 根据药品id删除Usage表中的一条数据
     * @param _mecId
     */
    public void delete(String _mecId){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("Usage", "_mecId = ?", new String[]{_mecId});
        db.close();
    }

    /**
     *更改用法用量
     */
    public void update(String _mecId,String newUsage){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_usage",newUsage);
        db.update("Usage", values, "_mecId = ?", new String[]{_mecId});
        db.close();
    }

    /**
     * 根据药品id到Usage表中查询当前药品用法用量及其相关信息
     * @param _mecId
     * @return
     */
    public MedicineUsage query(String _mecId){
        MedicineUsage usage = new MedicineUsage();
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("Usage",null," _mecId = ? ",new String[]{_mecId},null,null,null);
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            usage.set_id(_id);

            String mecId = cursor.getString(cursor.getColumnIndex("_mecId"));
            usage.set_mecId(mecId);

            int _drawerId = cursor.getInt(cursor.getColumnIndex("_drawerId"));
            usage.set_drawerId(_drawerId);

            int _position = cursor.getInt(cursor.getColumnIndex("_posiotion"));
            usage.set_position(_position);

            String _usage = cursor.getString(cursor.getColumnIndex("_usage"));
            usage.set_usage(_usage);
        }
        db.close();
        return usage;
    }
}
