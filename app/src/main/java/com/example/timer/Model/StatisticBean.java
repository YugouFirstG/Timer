package com.example.timer.Model;

import com.example.timer.Interfaces.IViewType;

import java.util.ArrayList;
import java.util.List;

import static com.example.timer.Utils.SheetColors.colors;


public class StatisticBean implements IViewType {
    private int totalCost;
    private String startDate;
    private int dur;
    List<SimpleInfo> list = new ArrayList<>();



    public StatisticBean( String startDate,List<SimpleInfo> ls,int dur,int totalCost) {
        this.startDate = startDate;
        this.dur = dur;
        list.add(new SimpleInfo("未记录",dur*24*3600-totalCost,0xffebedf2));
        list.addAll(ls) ;
        this.totalCost = totalCost;
    }


    public int getTotalCost() {
        return totalCost;
    }

    public String getStartDate() {
        return startDate;
    }

    public int getTypes() {

        return list.size();
    }

    public List<SimpleInfo> getList() {
        return list;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}

