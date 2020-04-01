package com.example.timer.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TestDao {
    private  static TestDao INSTANCE = null;
    private  DatabaseHelper mHelper = null;
    private SQLiteDatabase mDB = null;
    private TestDao(Context context){
        mHelper = new DatabaseHelper(context,"timer_db",null,1);
        mDB = mHelper.getWritableDatabase();
    }

    public static  TestDao getInstance(Context context){
        if(INSTANCE == null){
            return  new TestDao(context);
        }
        return INSTANCE;
    }

    public void insert(){
        ContentValues values = new ContentValues();
        values.put("id",321);
        mDB.insert("test",null,values);
    }

    public void update(){

    }

    public void delete(){
        mDB.execSQL("delete from test where id = 321");
    }

    public List<Integer> select(){
        List<Integer> list = new ArrayList<Integer>();
        Cursor cursor = mDB.query(
                "test",
                new String[]{"id"},
                null,
                null,
                null,
                null,
                null);

        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                int x = cursor.getColumnIndex("id");
                Log.d("MainActivity","clindex"+x);
                int s = cursor.getInt(x);
                list.add(s);
            }
            cursor.close();
        }
        return list;
    }

    public void deleteAll(){
        mDB.execSQL("drop table test");
        mDB.execSQL("create table test(id Integer)");
    }
}
