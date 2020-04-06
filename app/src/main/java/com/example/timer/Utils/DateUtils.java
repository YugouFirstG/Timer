package com.example.timer.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.Duration;

public class DateUtils {

    ///yyyy-MM-dd'T'HH:mm:ss
     public static String getForMatedDate(int year, int month, int day){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(new Date(year-1900,month,day));
    }

    public static String getCurrentDate(){
         return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(new Date());
    }

    public static String getCurrentTime(){
        return new SimpleDateFormat("HH:mm:ss", Locale.CHINESE).format(new Date());
    }

    public static String getForMatedTime(int hour,int minute,int sec){
         Date d = new Date();
         d.setHours(hour);
         d.setMinutes(minute);
         d.setSeconds(sec);

        return new SimpleDateFormat("HH:mm:ss",Locale.CHINESE).format(d);
    }

    public static Duration getDuration(String startDate,String endDate){
//        Date sDate =;
//        Date eDate =;



        return null;
     }
}
