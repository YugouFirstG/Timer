package com.example.timer.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timer.Interfaces.Modify;

import com.example.timer.Model.GoalBean;

import java.util.ArrayList;
import java.util.List;

public class GoalsDao implements Modify {
    private static GoalsDao INSTANCE = null;
    private DatabaseHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    final static String TABLE_NAME = "test2";

    private GoalsDao(Context context){
        mHelper = new DatabaseHelper(context,"timer_db",null,1);
        mDB = mHelper.getWritableDatabase();
        String sql = "create table if not exists " +
                "test2(id integer primary key autoincrement,"+
                "title text," +
                "content text," +
                "startTime time," +
                "estimateTime integer," +
                "startDate date," +
                "type text)";
        mDB.execSQL(sql);
    }

    public static  GoalsDao getInstance(Context context){
        if(INSTANCE == null){
            return  new GoalsDao(context);
        }
        return INSTANCE;
    }

    public List<GoalBean> select(){
        List<GoalBean> list = new ArrayList<GoalBean>();
        Cursor cursor = mDB.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                GoalBean g = new GoalBean(
                        cursor.getString(cursor.getColumnIndex("type")),
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getString(cursor.getColumnIndex("startTime")),
                        cursor.getInt(cursor.getColumnIndex("estimateTime")),
                        cursor.getString(cursor.getColumnIndex("startDate"))
                );
                g.setId(cursor.getInt(cursor.getColumnIndex("id")));
                list.add(g);
            }
            cursor.close();
        }
        return list;
    }



    @Override
    public void insert(ContentValues values) {
        try {
            mDB.insert(TABLE_NAME,null,values);
        }catch (SQLiteConstraintException s){
            Log.d("GoalDao",s.toString());
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
        mDB.execSQL("drop table "+TABLE_NAME);
    }


}
