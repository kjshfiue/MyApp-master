package com.wt.calendarcard;

import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * Created by sks on 2016/2/17.
 */
public class DateUtil {

    public static boolean isSameMonth(Date date){
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        return (calendar.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH));
    }
    public static boolean isSameDay(Date date){
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        return (calendar.get(Calendar.YEAR)==calendar2.get(Calendar.YEAR)
                && calendar.get(Calendar.MONTH)==calendar2.get(Calendar.MONTH)
                && calendar.get(Calendar.DAY_OF_MONTH)==calendar2.get(Calendar.DAY_OF_MONTH));
    }
    public static boolean isSameDay(Calendar calendar){
        return calendar!=null && isSameDay(calendar.getTime());
    }
}
