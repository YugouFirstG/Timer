package com.example.timer.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timer.Interfaces.Modify;
import com.example.timer.Model.Goal;

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
                "startDate date," +
                "endDate date)";
        mDB.execSQL(sql);
    }

    public static  GoalsDao getInstance(Context context){
        if(INSTANCE == null){
            return  new GoalsDao(context);
        }
        return INSTANCE;
    }

    public List<Goal> select(){
        List<Goal> list = new ArrayList<Goal>();
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
                Goal g = new Goal(
                        cursor.getString(cursor.getColumnIndex("title")),
                        cursor.getString(cursor.getColumnIndex("startDate")),
                        cursor.getString(cursor.getColumnIndex("endDate")),
                        cursor.getString(cursor.getColumnIndex("content")),
                        cursor.getInt(cursor.getColumnIndex("id"))
                        );
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
        mDB.update(TABLE_NAME,values,"title",null);
    }

    @Override
    public void delete(String t) {
        mDB.execSQL("delete from "+TABLE_NAME+" where title "+"="+t);
    }

    public void dropTable(){
        mDB.execSQL("drop table "+TABLE_NAME);
    }
}
