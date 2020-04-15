package com.example.timer.Model;

import com.example.timer.MainActivity;

public class RecordBean /*implements MainActivity.IViewType*/ {
    private String type;
    private String title;
    private String content;
    private String startTime;
    private int costTime;
    private String date;
    private int id;

    public RecordBean(String type, String title, String content, String startTime, int costTime, String date) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.costTime = costTime;
        this.date = date;
    }

    public void updateData(String type, String title, String content, String startTime, int costTime, String date) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.costTime = costTime;
        this.date = date;
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

//    @Override
//    public int getItemType() {
//        return 0;
//    }
}
