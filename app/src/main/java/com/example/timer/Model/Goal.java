package com.example.timer.Model;

import androidx.annotation.Nullable;

public class Goal {
    private String gTitle;
    private String startDate;
    private String endDate;
    private String content;
    private int id;

    public Goal(String gTitle, String startDate, String endDate,String content,int id) {
        this.gTitle = gTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.id = id;
    }

    public String getGTitle() {
        return gTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getContent() {
        return content;
    }

    public void updateData(String gTitle, String startDate, String endDate,String content){
        this.gTitle = gTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
    }


}
