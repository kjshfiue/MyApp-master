package com.example.sks.myapp.test;

import android.os.Bundle;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;
import com.example.sks.myapp.BaseActivity;
import com.example.sks.myapp.R;
import com.example.sks.myapp.util.Util;
import com.squareup.timessquare.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        DayPickerView dayPickerView = (DayPickerView) findViewById(R.id.pickerView);
        dayPickerView.setController(new DatePickerController() {
            @Override
            public int getMaxYear() {
                return 2017;
            }

            @Override
            public void onDayOfMonthSelected(int year, int month, int day) {
                Util.e("Day Selected", day + " / " + month + " / " + year);
            }

            @Override
            public void onDateRangeSelected(SimpleMonthAdapter.SelectedDays<SimpleMonthAdapter.CalendarDay> selectedDays) {

            }
        });
    }


}
