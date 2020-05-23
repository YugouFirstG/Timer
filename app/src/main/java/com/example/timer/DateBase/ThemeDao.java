package com.example.timer.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timer.Interfaces.Modify;

import java.util.ArrayList;
import java.util.List;

public class ThemeDao implements Modify {
    private static ThemeDao INSTANCE = null;
    private DatabaseHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private final static String TABLE_NAME = "themes";

    private ThemeDao(Context context){
        mHelper = new DatabaseHelper(context,"timer_db",null,1);
        mDB = mHelper.getWritableDatabase();
        String sql = "create table if not exists " +
                "themes(id integer primary key autoincrement,"+
                "theme text)";
        mDB.execSQL(sql);
    }

    public static  ThemeDao getInstance(Context context){
        if(INSTANCE == null){
            return  new ThemeDao(context);
        }
        return INSTANCE;
    }

    public List<String> select(){
        List<String> list =  new ArrayList<>();
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
                String g;
                g = cursor.getString(cursor.getColumnIndex("theme"));
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
            Log.d("ThemeDao",s.toString());
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
