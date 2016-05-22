package com.wt.calendarcard;

import java.util.Calendar;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CardPagerAdapter extends PagerAdapter {

    private Context mContext;
    private OnCellItemClick defaultOnCellItemClick;
    private CalendarCard[] cards;

    private final int mCurrentIndex = 498;

    public CardPagerAdapter(Context ctx, CalendarCard[] cards) {
        mContext = ctx;
        this.cards = cards;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.MONTH, position - mCurrentIndex);
//        CalendarCard card = new CalendarCard(mContext,cal);
//        card.setHeaderVisibility(false);
//
////        card.setDateDisplay(cal);
////        card.notifyChanges();
//
//        if (card.getOnCellItemClick() == null)
//            card.setOnCellItemClick(defaultOnCellItemClick);
//
//        ((ViewPager) container).addView(card, 0);
//
//        return card;

        CalendarCard card = cards[position % cards.length];

        if (((ViewPager) container).getChildCount() == cards.length || container == card.getParent()) {
            ((ViewPager) container).removeView(card);
        }


        if (card.getOnCellItemClick() == null)
            card.setOnCellItemClick(defaultOnCellItemClick);

        container.addView(card, 0);
        return card;


    }

    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        // TODO almoast ifinite ;-)
        return Integer.MAX_VALUE;
    }

    public OnCellItemClick getDefaultOnCellItemClick() {
        return defaultOnCellItemClick;
    }

    public void setDefaultOnCellItemClick(OnCellItemClick defaultOnCellItemClick) {
        this.defaultOnCellItemClick = defaultOnCellItemClick;
    }

}
