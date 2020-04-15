package com.example.timer.DateBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.timer.Interfaces.Modify;

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
                "startDate date," +
                "type text)";
        mDB.execSQL(sql);
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
