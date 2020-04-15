package com.example.timer.Interfaces;

import android.content.ContentValues;

public interface Modify {
    void insert(ContentValues values);
    void update(ContentValues values);

    void delete(int t);

    void dropTable();
}
