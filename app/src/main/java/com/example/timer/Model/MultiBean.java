package com.example.timer.Model;

import androidx.annotation.NonNull;

import com.example.timer.Interfaces.IViewType;

public class MultiBean implements IViewType {
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
