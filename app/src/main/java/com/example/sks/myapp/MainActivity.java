package com.example.sks.myapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sks.myapp.adapter.MyRecyclerAdapter;
import com.example.sks.myapp.calendar.CalendarCardActivity;
import com.example.sks.myapp.calendar.Testctivity1;
import com.example.sks.myapp.entity.Name;
import com.example.sks.myapp.test.CalendarActivity;
import com.example.sks.myapp.test.DataSelectedActivity;
import com.example.sks.myapp.test.SampleTimesSquareActivity;
import com.example.sks.myapp.test.TestEventBusActivity;
import com.example.sks.myapp.test.TestGuideActivity;
import com.example.sks.myapp.test.TestOkHttpActivity;
import com.example.sks.myapp.test.daoexample.NoteActivity;
import com.example.sks.myapp.test.testrili.TestRiLiActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private List<Name> datas;
    private RecyclerView recycler_view;
    private MyAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private class MyListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_1:
                    mAdapter.notifyItemChanged(15);
                    break;


            }
        }
    }


    private void initView() {
        datas = new ArrayList<Name>() {{
            add(new Name(101, "CalendarActivity"));
            add(new Name(102, "SampleTimesSquareActivity"));
            add(new Name(103, "DataSelectedActivity"));
            add(new Name(104, "TestRiLiActivity"));
            add(new Name(105, "Testctivity1"));
            add(new Name(106, "CalendarCardActivity"));
            add(new Name(107, "NoteActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(109, "TestGuideActivity"));
            add(new Name(110, "TestOkHttpActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));
            add(new Name(108, "TestEventBusActivity"));

        }};

        mAdapter = new MyAdapter(datas);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, long id) {
                toActivityById(id);
            }
        });

        MyListener listener = new MyListener();

        findViewById(R.id.btn_1).setOnClickListener(listener);


    }

    private void toActivityById(long id) {
        switch ((int) id) {
            case 101:
                startActivity(new Intent(this, CalendarActivity.class));
                break;
            case 102:
                startActivity(new Intent(this, SampleTimesSquareActivity.class));
                break;
            case 103:
                startActivity(new Intent(this, DataSelectedActivity.class));
                break;
            case 104:
                startActivity(new Intent(this, TestRiLiActivity.class));
                break;
            case 105:
                startActivity(new Intent(this, Testctivity1.class));
                break;
            case 106:
                startActivity(new Intent(this, CalendarCardActivity.class));
                break;
            case 107:
                startActivity(new Intent(this, NoteActivity.class));
                break;
            case 108:
                startActivity(new Intent(this, TestEventBusActivity.class));
                break;
            case 109:
                startActivity(new Intent(this, TestGuideActivity.class));
                break;
            case 110:
                startActivity(new Intent(this, TestOkHttpActivity.class));
                break;

        }
    }

    public void showListVertical(View view) {
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    public void showListHorizontal(View view) {
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }


    public void showGrid(View view) {
        recycler_view.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public void itemAdd(View view) {
        mAdapter.addItem(new Name(1, "新添加"), 2);
    }

    public void itemDel(View view) {
        mAdapter.removeItem(2);
    }


    /**
     *
     */
    private class MyAdapter extends MyRecyclerAdapter {
        private List<Name> datas;

        public MyAdapter(List<Name> datas) {
            this.datas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string_list, parent, false));
//            Log.e("yih", "onCreateViewHolder");
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder sholder, int position) {
            super.onBindViewHolder(sholder, position);
            ViewHolder holder = (ViewHolder) sholder;
            holder.text.setText(datas.get(position).name);
        }

        @Override
        public int getItemCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public Object getItem() {
            return null;
        }


        @Override
        public long getItemId(int position) {
            return datas == null ? 0 : datas.get(position).id;
        }

        public void refresh(List<Name> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        public void addItem(Name name) {
            datas.add(name);
            notifyItemInserted(datas.size() - 1);
        }

        public void addItem(Name name, int position) {
            datas.add(position, name);
            notifyItemInserted(position);
            notifyDataSetChanged();
        }


        public void removeItem(int position) {
            datas.remove(position);
            notifyItemRemoved(position);
            notifyDataSetChanged();
        }


        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView text;
            View itemView;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                this.text = (TextView) itemView.findViewById(R.id.text_name);
            }


        }
    }


}
