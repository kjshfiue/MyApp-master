package com.wt.calendarcard;

import android.view.View;

import java.util.Calendar;
import java.util.Date;

public interface OnCellItemClick {
	
	public void onCellClick(View v, CardGridItem item);
    void changeDate(Date calendar); // 回调滑动ViewPager改变的日期

}
