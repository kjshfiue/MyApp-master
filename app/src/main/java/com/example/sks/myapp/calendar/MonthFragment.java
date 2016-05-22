package com.example.sks.myapp.calendar;

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.sks.myapp.R;
import com.example.sks.myapp.util.Util;
import com.fan.fancalenderlibrary.DateEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MonthFragment extends Fragment {

    private MyAdapter myAdapter;


    public static MonthFragment newInstance(Calendar calendar) {
        MonthFragment fragment = new MonthFragment();
        Bundle args = new Bundle();
        args.putSerializable("calendar", calendar);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar;
        if (getArguments() != null) {
            calendar = (Calendar) getArguments().getSerializable("calendar");
        } else {
            calendar = Calendar.getInstance();
        }
        GridView grid_view = (GridView) getActivity().findViewById(R.id.grid_view);
        myAdapter = new MyAdapter(getActivity(), initDates(calendar));
        grid_view.setAdapter(myAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        Util.e("yih", "onResume"  );
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_month, container, false);
    }

    private List<DateEntity> initDates(Calendar calendar) {
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
                Util.e("yih", "month" + calendar.get(Calendar.MONTH));
                break;
            }
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        return dates;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    private OnDayClickListener dayClickListener;

    public void setDayClickListener(OnDayClickListener dayClickListener){
        this.dayClickListener = dayClickListener;
    }

    public interface OnDayClickListener {
        void onClick(View view, int position);
    }

    private class MyAdapter extends BaseAdapter {
        private Context context;

        private List<DateEntity> dates;

        public MyAdapter(Context context, List<DateEntity> dates) {
            this.context = context;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_day_2, parent, false);
                holder.dayText = (TextView) convertView.findViewById(R.id.day_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DateEntity item = getItem(position);
            int day = item.getDay();
            holder.dayText.setText(day < 10 ? "0" + day : "" + day);
            holder.dayText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dayClickListener != null)
                        dayClickListener.onClick(v, position);
                }
            });
            return convertView;
        }

        private class ViewHolder {
            TextView dayText;
        }
    }


    public void slideToTheNextMonth() {

    }

    public void slideToTheLastMonth() {

    }


}
