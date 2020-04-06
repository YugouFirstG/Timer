package com.example.timer.Model;

import androidx.annotation.NonNull;

import com.example.timer.ListExampleActivity;

public class MultiBean implements ListExampleActivity.IViewType {
    public String name;

    @Override
    public int getItemType() {
        return 0;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
