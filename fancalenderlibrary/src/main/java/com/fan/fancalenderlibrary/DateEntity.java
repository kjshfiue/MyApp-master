package com.fan.fancalenderlibrary;

import java.util.Calendar;

/**
 * Created by sks on 2016/2/17.
 */
public class DateEntity {
    private long milliseconds;
    private int year;
    private int month;
    private int day;
    private Calendar calendar;

    public DateEntity(){
        this.milliseconds = System.currentTimeMillis();
        this.calendar = Calendar.getInstance();
    }
    public DateEntity(long milliseconds){
        this.milliseconds = milliseconds;
        this.calendar = Calendar.getInstance();
        this.calendar.setTimeInMillis(milliseconds);
    }

    public int getDay(){

        return calendar.get(Calendar.DAY_OF_MONTH);
    }




}
