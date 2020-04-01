package com.example.timer.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    ///
     public static String getForMatedDate(int year, int month, int day){
        return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(new Date(year-1900,month,day));
    }

    public static String getCurrentDate(){
         return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINESE).format(new Date());
    }

}
