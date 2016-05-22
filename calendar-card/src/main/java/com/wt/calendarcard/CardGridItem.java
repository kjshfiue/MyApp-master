package com.wt.calendarcard;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CardGridItem {

    private Integer dayOfMonth;
    private Object data;
    private boolean enabled = true;
    private Calendar date;

    public enum State {
        NORMAL, SELECTED, TODAY, OTHER_MONTH
    }

    public State getCardState() {

        if (DateUtil.isSameDay(date)) {
            return State.TODAY;
        } else if (DateUtil.isSameDay(date)) {
            return State.TODAY;
        }
        return State.NORMAL;
    }

    public CardGridItem(Integer dom) {
        setDayOfMonth(dom);
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public CardGridItem setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        return this;
    }

    public Object getData() {
        return data;
    }

    public CardGridItem setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public CardGridItem setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public Calendar getDate() {
        return date;
    }

    public CardGridItem setDate(Calendar date) {
        this.date = date;
        return this;
    }

    public String getDateString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date.getTime());
    }

}
