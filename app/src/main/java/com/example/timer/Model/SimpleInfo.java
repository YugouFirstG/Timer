package com.example.timer.Model;

import com.example.timer.Interfaces.IViewType;

public  class  SimpleInfo implements IViewType {
    public SimpleInfo(String type, int time,int color){
        this.time = time;
        this.type = type;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public int getTime() {
        return time;
    }

    public int getColor() {
        return color;
    }

    private String type;
    private int time;
    private int color;

    @Override
    public int getItemType() {
        return 5;
    }
}
