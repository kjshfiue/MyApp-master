package com.yihealth.calendar.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yih.calendar.lib.R;

import java.util.List;

/**
 *
 * Created by sks on 2016/2/18.
 */
public class CalendarLayoutPager extends LinearLayout {

    private ViewPager mViewPager;
    private int mCurrentIndex = 498;
    private CalendarViewAdapter adapter;

    private View titleLayout;
    private boolean displayMonth = true;

    private OnCalendarListener mListener;
    public interface OnCalendarListener{

        void clickDate(CustomDate date, List<CustomDate> selectedDates);

        void changeDate(CustomDate date, List<CustomDate> selectedDates);
    }

    public void setOnCalendarListener(OnCalendarListener listener){
        this.mListener = listener;
    }



    public CalendarLayoutPager(Context context) {
        super(context);
    }

    public CalendarLayoutPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarLayoutPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CalendarLayoutPager, defStyleAttr, 0);

        int n = array.getIndexCount();

        for (int i = 0; i < n; i++)
        {
            int attr = array.getIndex(i);
            if(attr == R.styleable.CalendarLayoutPager_calendar_selectType){
                switch (array.getInt(attr, 1)){
                    case 1:
                        this.type = CalendarCard.SelectType.MULTIPLE;
                        break;
                    case 2:
                        this.type = CalendarCard.SelectType.RANGE;
                        break;
                    default:
                        this.type = CalendarCard.SelectType.SINGLE;
                }
            }else if(attr == R.styleable.CalendarLayoutPager_calendar_displayMonth){
                displayMonth = array.getBoolean(attr, true);
            }
        }

        init(context);
    }
    public enum SlideDirection {
        RIGHT, LEFT, NO_SLIDE
    }


    private CalendarCard.SelectType type = CalendarCard.SelectType.SINGLE;

    private ImageButton preImgBtn, nextImgBtn;
    private TextView monthText;


    private Context context;
    public void init(Context context) {
        init(context, type);
    }

    public void setTitleVisiable(boolean isShow){
        if(isShow){
            titleLayout.setVisibility(View.VISIBLE);
        }else{
            titleLayout.setVisibility(View.GONE);
        }
    }

    public void setSelectedType(CalendarCard.SelectType type){
        init(this.context,type);
    }


    public void init(Context context, CalendarCard.SelectType type) {
        this.context = context;
        this.type = type;
        setOrientation(VERTICAL);
        View layout = LayoutInflater.from(context).inflate(R.layout.calendar_pager,null, false);
        titleLayout = layout.findViewById(R.id.titleLayout);
        setTitleVisiable(displayMonth);

        mViewPager = (ViewPager) layout.findViewById(R.id.vp_calendar);
        preImgBtn = (ImageButton) layout.findViewById(R.id.btnPreMonth);
        nextImgBtn = (ImageButton) layout.findViewById(R.id.btnNextMonth);
        monthText = (TextView) layout.findViewById(R.id.tvCurrentMonth);
        preImgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
            }
        });
        nextImgBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });

        CalendarCard[] views = new CalendarCard[3];
        MyOnCellClickListener listener = new MyOnCellClickListener();
        for (int i = 0; i < 3; i++) {
            views[i] = new CalendarCard(context, listener, type);
        }
        adapter = new CalendarViewAdapter(views);
        setViewPager();

        removeAllViews();
        addView(layout);
    }

    private void setViewPager() {
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(mCurrentIndex);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                SlideDirection mDirection = measureDirection(position);
                adapter.updateCalendarView(position,mDirection);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private class MyOnCellClickListener implements CalendarCard.OnCellClickListener {

        @Override
        public void clickDate(CustomDate date,List<CustomDate> selectedDates) {
            if (mListener!=null)
                mListener.clickDate(date, selectedDates);
        }

        @Override
        public void changeDate(CustomDate date,List<CustomDate> selectedDates) {
            monthText.setText(date.month + "月");
            if (mListener!=null)
                mListener.changeDate(date, selectedDates);
        }
    }


    /**
     * 计算方向
     *
     * @param arg0
     */
    private SlideDirection measureDirection(int arg0) {
        SlideDirection direction = SlideDirection.NO_SLIDE;
        if (arg0 > mCurrentIndex) {
            direction = SlideDirection.RIGHT;
        } else if (arg0 < mCurrentIndex) {
            direction = SlideDirection.LEFT;
        }
        mCurrentIndex = mViewPager.getCurrentItem();
        return direction;
    }



}
