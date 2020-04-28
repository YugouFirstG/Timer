package com.example.timer.Model;

import com.example.timer.Interfaces.IViewType;

import java.util.List;

public class StatisticBean implements IViewType {
    private int totalCost;
    private String startDate;
    public StatisticBean(int totalCost, String startDate) {
        this.totalCost = totalCost;
        this.startDate = startDate;
    }


    public int getTotalCost() {
        return totalCost;
    }

    public String getStartDate() {
        return startDate;
    }



    @Override
    public int getItemType() {
        return 0;
    }
}
