package com.example.timer.Model;


import com.example.timer.Interfaces.IViewType;

public class RecordingBean implements IViewType {
    private String type;
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private int duration;

    public RecordingBean(GoalBean goal) {
        this.type = goal.getType();
        this.title = goal.getTitle();
        this.content = goal.getContent();
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getEndTime() {
        return endTime;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
