package com.example.admin.jjypznglxt.dbcontrol;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/29.
 */
public class EmeryTableControl {

    private static Context mContext;

    private EmeryTableControl(Context context){
        this.mContext = context;
    }

    private static EmeryTableControl emeryTableControl = null;

    public static EmeryTableControl getInstance(Context context){
        if(emeryTableControl == null) {
            synchronized (EmeryTableControl.class){
                if(emeryTableControl == null){
                    emeryTableControl = new EmeryTableControl(context);
                }
            }
        }
        return emeryTableControl;
    }

    /**
     * 根据急救项目名称往急救项目表中添加一条数据
     * @param title  传入参数急救项目名称
     */
    public void add( int _id, String title){
//        SQLiteDatabase db = mContext.openOrCreateDatabase("jjxtc_gkzx.db", Context.MODE_PRIVATE, null);
//        db.execSQL(" create table emergency (_id integer primary key autoincrement, _title varchar(20)) ");
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("_title", title);
        db.insert("Emergency", null, values);
//        Toast.makeText(mContext, "插入成功", Toast.LENGTH_SHORT).show();
        db.close();

    }

    /**
     * 根据急救项目名字删除急救项目表中的某一条数据
     * @param title  传入参数急救项目名称
     */
    public void delete(String title){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("Emergency", " _title = ? ", new String[]{title});
        db.close();
//        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据急救项目名称修改当前急救项目名称
     * @param title  当前数据库中已存在的急救项目名字
     * @param newTitle  新的急救项目名字
     */
    public void update(String title, String newTitle){
//        SQLiteDatabase db = mContext.openOrCreateDatabase("jjxtc_gkzx.db", Context.MODE_PRIVATE, null);
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_title", newTitle);
        db.update("Emergency", values, " _title = ? ", new String[]{title});
        db.close();
//        Toast.makeText(mContext, "更新成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 查询所有急救项目
     * @return  返回所有急救项目的名称集合
     */
    public List<String> queryAll(){
        List<String> result = new ArrayList<String>();
//        SQLiteDatabase db = mContext.openOrCreateDatabase("jjxtc_gkzx.db", Context.MODE_PRIVATE, null);
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        Cursor cursor = db.query("Emergency", new String[]{"_title"}, null, null, null, null, null);
        while (cursor.moveToNext()){
            result.add(cursor.getString(cursor.getColumnIndex("_title")));
        }
        String test = "";
        for(String s : result){
            test += s;
        }
        Toast.makeText(mContext, test, Toast.LENGTH_SHORT).show();

        db.close();
        return result;
    }
}
