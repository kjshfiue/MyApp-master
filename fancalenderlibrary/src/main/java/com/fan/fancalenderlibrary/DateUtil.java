package com.fan.fancalenderlibrary;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * Created by sks on 2016/2/17.
 */
public class DateUtil {
    private static Calendar calendar = Calendar.getInstance();
    public static String formatDate(){


        return "";
    }

    public static int getYearOfDate(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    public static int getMonthOfDate(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }
    public static int getDayOfDate(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public static int getWeekdayOfDate(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

}
