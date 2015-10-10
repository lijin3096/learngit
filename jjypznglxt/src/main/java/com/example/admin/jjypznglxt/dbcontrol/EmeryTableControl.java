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
     * ���ݼ�����Ŀ������������Ŀ�������һ������
     * @param title  �������������Ŀ����
     */
    public void add( int _id, String title){
//        SQLiteDatabase db = mContext.openOrCreateDatabase("jjxtc_gkzx.db", Context.MODE_PRIVATE, null);
//        db.execSQL(" create table emergency (_id integer primary key autoincrement, _title varchar(20)) ");
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_id", _id);
        values.put("_title", title);
        db.insert("Emergency", null, values);
//        Toast.makeText(mContext, "����ɹ�", Toast.LENGTH_SHORT).show();
        db.close();

    }

    /**
     * ���ݼ�����Ŀ����ɾ��������Ŀ���е�ĳһ������
     * @param title  �������������Ŀ����
     */
    public void delete(String title){
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("Emergency", " _title = ? ", new String[]{title});
        db.close();
//        Toast.makeText(mContext, "ɾ���ɹ�", Toast.LENGTH_SHORT).show();
    }

    /**
     * ���ݼ�����Ŀ�����޸ĵ�ǰ������Ŀ����
     * @param title  ��ǰ���ݿ����Ѵ��ڵļ�����Ŀ����
     * @param newTitle  �µļ�����Ŀ����
     */
    public void update(String title, String newTitle){
//        SQLiteDatabase db = mContext.openOrCreateDatabase("jjxtc_gkzx.db", Context.MODE_PRIVATE, null);
        SQLiteDatabase db = SQLiteDatabase.openDatabase("/data/data/com.example.admin.jjypznglxt/files/jjxtc_gkzx.db", null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("_title", newTitle);
        db.update("Emergency", values, " _title = ? ", new String[]{title});
        db.close();
//        Toast.makeText(mContext, "���³ɹ�", Toast.LENGTH_SHORT).show();
    }

    /**
     * ��ѯ���м�����Ŀ
     * @return  �������м�����Ŀ�����Ƽ���
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
