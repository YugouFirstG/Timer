package com.example.timer.Model;

import com.example.timer.MainActivity;

public class GoalBean implements MainActivity.IViewType {
    private String type;
    private String title;
    private String content;
    private String startTime;
    private int estimate_duration;
    private String date;
    private int id;

    public GoalBean(String type, String title, String content, String startTime, int estimate_duration,String date) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.estimate_duration = estimate_duration;
        this.date = date;
    }

    public void updateData(String type, String title, String content, String startTime, int estimate_duration,String date) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.estimate_duration = estimate_duration;
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

    public int getEstimateDuration() {
        return estimate_duration;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
