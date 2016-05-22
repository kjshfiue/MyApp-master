package com.yihealth.calendar.lib;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


public class CalendarViewAdapter extends PagerAdapter {
    public static final String TAG = "CalendarViewAdapter";  
    private CalendarCard[] views;
  
    public CalendarViewAdapter(CalendarCard[] views) {
        super();  
        this.views = views;  
    }  
  
      
    @Override  
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views[position % views.length];
        if (((ViewPager) container).getChildCount() == views.length) {
            ((ViewPager) container).removeView(view);
        }
        container.addView(view, 0);
        return view;
    }  
  
    @Override  
    public int getCount() {  
        return Integer.MAX_VALUE;  
    }  
  
    @Override  
    public boolean isViewFromObject(View view, Object object) {  
        return view == ((View) object);  
    }  
  
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) container);
    }

    // 更新日历视图
    public void updateCalendarView(int arg0,CalendarLayoutPager.SlideDirection direction) {
        CalendarCard card = views[arg0 % views.length];
        if (direction == CalendarLayoutPager.SlideDirection.RIGHT) {
            card.rightSlide();
        } else if (direction == CalendarLayoutPager.SlideDirection.LEFT) {
            card.leftSlide();
        }
    }

  
}  
