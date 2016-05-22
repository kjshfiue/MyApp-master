package com.wt.calendarcard;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarCardPager extends ViewPager {

	private CardPagerAdapter mCardPagerAdapter;
	private OnCellItemClick mOnCellItemClick;
    private  int mCurrentIndex = 498;
    private CalendarCard[] cards;

    private SlideDirection mDirection = SlideDirection.NO_SLIDE;
    enum SlideDirection {
        RIGHT, LEFT, NO_SLIDE
    }

    public CalendarCardPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		init(context);
	}

	public CalendarCardPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public CalendarCardPager(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
        cards = new CalendarCard[3];
        for(int i=0;i<3;i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, i);
            CalendarCard card = new CalendarCard(context,cal);
            card.setHeaderVisibility(false);
            cards[i] = card;
        }
		 mCardPagerAdapter = new CardPagerAdapter(context, cards);
		 setAdapter(mCardPagerAdapter);
        setCurrentItem(498);

        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                measureDirection(position);
                updateCalendarView(position);

                Date date = cards[mCurrentIndex % cards.length].getDateDisplay().getTime();

                mOnCellItemClick.changeDate(date);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
	}
    /**
     * 计算方向
     *
     * @param arg0
     */
    private void measureDirection(int arg0) {

        if (arg0 > mCurrentIndex) {
            mDirection = SlideDirection.RIGHT;
        } else if (arg0 < mCurrentIndex) {
            mDirection = SlideDirection.LEFT;
        }
        mCurrentIndex = arg0;
    }

    // 更新日历视图
    private void updateCalendarView(int arg0) {
        Calendar cal = Calendar.getInstance();
        if (mDirection == SlideDirection.RIGHT) {
            cal.add(Calendar.MONTH, arg0 - 498 +1);
            CalendarCard card1 = new CalendarCard(getContext(),cal);
            card1.setHeaderVisibility(false);
            cards[(arg0 + 1) % cards.length] = card1;
        } else if (mDirection == SlideDirection.LEFT) {

            cal.add(Calendar.MONTH, arg0 - 498 -1);
            CalendarCard card2 = new CalendarCard(getContext(),cal);
            card2.setHeaderVisibility(false);
            cards[(arg0 - 1) % cards.length] = card2;
        }
        mDirection = SlideDirection.NO_SLIDE;
    }
	public CardPagerAdapter getCardPagerAdapter() {
		return mCardPagerAdapter;
	}

	public OnCellItemClick getOnCellItemClick() {
		return mOnCellItemClick;
	}

	public void setOnCellItemClick(OnCellItemClick mOnCellItemClick) {
		this.mOnCellItemClick = mOnCellItemClick;
		mCardPagerAdapter.setDefaultOnCellItemClick(this.mOnCellItemClick);
		if (getChildCount() > 0) {
			for(int i=0; i<getChildCount(); i++) {
				View v = getChildAt(i);
				if (v instanceof CalendarCard) {
					((CalendarCard) v).setOnCellItemClick(this.mOnCellItemClick);
				}
			}
		}
	}




}
