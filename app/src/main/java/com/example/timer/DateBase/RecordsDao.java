package com.example.timer.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timer.Interfaces.IViewType;
import com.example.timer.Interfaces.Modify;

import com.example.timer.Model.RecordBean;
import com.example.timer.Model.StatisticBean;

import java.util.ArrayList;
import java.util.List;

public class RecordsDao implements Modify {
    private static RecordsDao INSTANCE = null;
    private DatabaseHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    final static String TABLE_NAME = "record";

    private RecordsDao(Context context) {
        mHelper = new DatabaseHelper(context, "timer_db", null, 1);
        mDB = mHelper.getWritableDatabase();
        String sql = "create table if not exists " +
                "record(id integer primary key autoincrement," +
                "title text," +
                "content text," +
                "startTime time," +
                "costTime integer," +
                "endTime time,"+
                "startDate date," +
                "type text)";
        mDB.execSQL(sql);
    }

    public static  RecordsDao getInstance(Context context){
        if(INSTANCE == null){
            return  new RecordsDao(context);
        }
        return INSTANCE;
    }

    public StatisticBean getStatisticData(int dur,String date){
        int total = 0;
        String[] col = {"sum(costTime)",date},dt={date};
        Cursor cursor = mDB.query(
                TABLE_NAME,
                col,
                "startDate=?",
                dt,
                null,
                null,
                null,
                null
        );
        if(cursor.getCount()>0){
            String[] s = cursor.getColumnNames();
            cursor.moveToFirst();
            total = cursor.getInt(0);;
        }
        cursor.close();
        return new StatisticBean(total,date);
    }

    public List<RecordBean> select(String selection,String[] selectionArgs,String group){
        List<RecordBean> list = new ArrayList<>();
        Cursor cursor = mDB.query(
                TABLE_NAME,
                null,
                selection,
                selectionArgs,
                group,
                null,
                null
        );
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                RecordBean r = new RecordBean(
                  cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("startTime")),
                        cursor.getInt(cursor.getColumnIndex("costTime")),
                        cursor.getString(cursor.getColumnIndex("endTime")),
                        cursor.getString(cursor.getColumnIndex("startDate"))
                );
                r.setId(cursor.getInt(cursor.getColumnIndex("id")));
                list.add(r);
            }
            cursor.close();
        }

        return list;
    }

    @Override
    public void insert(ContentValues values) {
        try {
            mDB.insert(TABLE_NAME, null, values);
        } catch (SQLiteConstraintException s) {
            Log.d("RecordDao", s.toString());
        }
    }

    @Override
    public void update(ContentValues values) {
        mDB.update(TABLE_NAME, values, "id", null);
    }

    @Override
    public void delete(int t) {
        mDB.execSQL("delete from " + TABLE_NAME + " where id " + "=" + t);
    }

    @Override
    public void dropTable() {
        mDB.execSQL("drop table " + TABLE_NAME);
    }

}
