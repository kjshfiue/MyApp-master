package com.example.sks.myapp.test;

import android.os.Bundle;
import android.view.View;

import com.example.sks.myapp.BaseActivity;
import com.example.sks.myapp.R;
import com.example.sks.myapp.util.Util;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DataSelectedActivity extends BaseActivity {

    private CalendarPickerView calendar;
    private int firstDay;
    private int lastDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_selected);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.MONTH, 1);
        nextYear.set(Calendar.DAY_OF_MONTH, 1);
        nextYear.add(Calendar.DAY_OF_MONTH, -1);

        final Calendar lastYear = Calendar.getInstance();
        lastYear.add(Calendar.YEAR, -1);
        lastYear.set(Calendar.DAY_OF_MONTH, 1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);
//        calendar.setTitleVisibility(false);

        calendar.init(lastYear.getTime(), nextYear.getTime()) //
                .inMode(CalendarPickerView.SelectionMode.RANGE) //
                .withSelectedDate(new Date());

        findViewById(R.id.done_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast(getDayOfMonthByDate(calendar.getSelectedDate()) + "-" + getDayOfMonthByDate(calendar.getLastSelectedDate()));
                List<Date> dates = calendar.getSelectedDates();
                StringBuilder sb = new StringBuilder();
                for (Date dd : dates) {
                    Calendar cc = Calendar.getInstance();
                    cc.setTime(dd);
                    sb.append(cc.get(Calendar.DAY_OF_MONTH)).append(",");
                }
                Util.e("yih",sb.toString());
            }
        });
    }

    private int getDayOfMonthByDate(Date date){
        Calendar cc = Calendar.getInstance();
        cc.setTime(date);
        return cc.get(Calendar.DAY_OF_MONTH);
    }
}
