package com.example.timer.Utils;

import android.provider.ContactsContract;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.datatype.Duration;

import static android.icu.text.DateTimePatternGenerator.DAY;

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

     public static int  getOffsetDays(int y1,int m1,int day1,int y2,int m2, int day2){
         Date d =new  Date(y2-1900,m2,day2);
         int offset = Integer.parseInt((new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(d)).substring(8));

         return  offset;
     }
     public static String getFormatTimeFromSeconds(int sec){
         int hour,min,second;
         hour = (sec-sec %3600)/3600;
         sec = sec - hour *3600;
         min  = (sec -sec % 60)/60;
         second = sec % 60;

         String s,h=""+hour,m=""+min,se=""+second;
         if(hour<10)
             h = "0"+hour;
         if(min<10)
             m = "0"+min;
         if(second<10)
             se = "0"+second;

         s = h+"h "+m+"m "+se+"s";
         return s;
     }

     public static String FormatMiss(int time){
        String hh=time/3600>9?time/3600+"":"0"+time/3600;
        String mm=(time% 3600)/60>9?(time% 3600)/60+"":"0"+(time% 3600)/60;
        String ss=(time% 3600) % 60>9?(time% 3600) % 60+"":"0"+(time% 3600) % 60;
        return hh+":"+mm+":"+ss;
    }
}
