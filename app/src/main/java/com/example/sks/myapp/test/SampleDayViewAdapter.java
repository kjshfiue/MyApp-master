package com.example.sks.myapp.test;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.sks.myapp.R;
import com.squareup.timessquare.CalendarCellView;
import com.squareup.timessquare.DayViewAdapter;

public class SampleDayViewAdapter implements DayViewAdapter {
  @Override
  public void makeCellView(CalendarCellView parent) {
      View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view_custom, null);
      parent.addView(layout);
      parent.setDayOfMonthTextView((TextView) layout.findViewById(R.id.day_view));
  }
}
