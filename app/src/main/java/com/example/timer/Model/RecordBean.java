package com.example.timer.Model;

import com.example.timer.Interfaces.IViewType;


public class RecordBean implements IViewType {
    private String type;
    private String title;
    private String content;
    private String startTime;
    private int costTime;
    private String endTime;
    private String date;
    private int id;
    boolean isComplete;

    public RecordBean(String type, String title, String content, String startTime, int costTime,String endTime, String date,boolean isComplete) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.costTime = costTime;
        this.date = date;
        this.endTime = endTime;
        this.isComplete = isComplete;
    }

    public void updateData(String type, String title, String content, String startTime, int costTime,String endTime, String date,boolean isComplete) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.costTime = costTime;
        this.date = date;
        this.endTime = endTime;
        this.isComplete = isComplete;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getId() {
        return id;
    }

    public int getCostTime() {
        return costTime;
    }

    public String getDate() {
        return date;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public int getItemType() {
        return 1;
    }


    //    @Override
//    public int getItemType() {
//        return 0;
//    }
}
