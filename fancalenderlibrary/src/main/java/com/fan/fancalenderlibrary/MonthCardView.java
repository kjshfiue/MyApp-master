package com.fan.fancalenderlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * Created by sks on 2016/2/17.
 */
public class MonthCardView extends GridView {
    private Context context;
    private Date today;
    private Date setDate;
    private MyAdapter myAadpter;

    public MonthCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context);
        this.context = context;

    }
    public MonthCardView(Context context, Date setDate) {
        super(context);
        this.context = context;
        this.setDate = setDate;
        init(setDate);
    }


    public void init(Date setDate) {
        setNumColumns(7);
        setGravity(Gravity.CENTER);
        setVerticalSpacing(14);
        myAadpter = new MyAdapter(initDates(setDate));
        setAdapter(myAadpter);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private List<DateEntity> initDates(Date setDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(setDate);

        List<DateEntity> dates = new ArrayList<>();

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, (dayOfMonth - 1) * -1);

        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, (weekDay - 1) * -1);

        while (true) {
            dates.add(new DateEntity(calendar.getTimeInMillis()));
            if ((month + 1) % 12 == calendar.get(Calendar.MONTH) && calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                dates.remove(dates.size() - 1);
                break;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dates;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    private class MyAdapter extends BaseAdapter {

        private List<DateEntity> dates;

        public MyAdapter(List<DateEntity> dates) {
            this.dates = dates;
        }

        @Override
        public int getCount() {
            return dates.size();
        }

        @Override
        public DateEntity getItem(int position) {
            return dates.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new  ViewHolder();
                LinearLayout linearLayout = new LinearLayout(context);

                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);
                holder.dayText = new TextView(context);
//                holder.dayText.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                holder.dayText.setTextSize(12);
                linearLayout.addView(holder.dayText);

                convertView = linearLayout;
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            DateEntity item = getItem(position);
            int day = item.getDay();
            holder.dayText.setText(day < 10 ? "0" + day : "" + day);
            return convertView;
        }

        private class ViewHolder{
            TextView dayText;
        }
    }
}
