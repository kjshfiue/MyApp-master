package com.example.sks.myapp.calendar;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.sks.myapp.BaseActivity;
import com.example.sks.myapp.R;
import com.example.sks.myapp.util.Util;
import com.wt.calendarcard.CalendarCardPager;
import com.wt.calendarcard.CardGridItem;
import com.wt.calendarcard.OnCellItemClick;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarCardActivity extends BaseActivity {
    private TextView tvCurrentMonth;
    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_card);
        tvCurrentMonth = (TextView) findViewById(R.id.tvCurrentMonth);
        sdf = new SimpleDateFormat("yyyy-MM", Locale.getDefault());

        tvCurrentMonth.setText(sdf.format(new Date()));
        CalendarCardPager calendarCard = (CalendarCardPager)findViewById(R.id.calendarCard);
        calendarCard.setOnCellItemClick(new OnCellItemClick() {
            @Override
            public void onCellClick(View v, CardGridItem item) {
                Util.e("yih", "日期：" + item.getDateString());
            }

            @Override
            public void changeDate(Date date) {
                Log.e("yih", "onPageSelected 日期：" + sdf.format(date.getTime()));
                tvCurrentMonth.setText(sdf.format(date.getTime()));
            }
        });



    }
}
