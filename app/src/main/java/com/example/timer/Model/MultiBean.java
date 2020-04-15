package com.example.timer.Model;

import androidx.annotation.NonNull;

import com.example.timer.ListExampleActivity;
import com.example.timer.PlanFragment;

public class MultiBean implements ListExampleActivity.IViewType, PlanFragment.IViewType {
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
