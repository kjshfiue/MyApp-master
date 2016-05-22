package com.example.sks.myapp.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sks.myapp.BaseActivity;
import com.example.sks.myapp.R;
import com.example.sks.myapp.adapter.MyRecyclerAdapter;
import com.example.sks.myapp.entity.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * 看图片
 * Created by sks on 2016/2/15.
 */
public class ShowPhotoActivity extends BaseActivity {
    private List<Name> datas;
    private RecyclerView recycler_view;
    private MyAdapter mAdapter;
    private ImageView m_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        initView();
    }

    private void initView() {

        m_img = (ImageView) findViewById(R.id.m_img);
        datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            datas.add(new Name( i, "String - " + i));
        }
        mAdapter = new MyAdapter(datas);
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        recycler_view.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position, long id) {
                toast("图片-"+position);

            }
        });

    }

    private class MyAdapter extends MyRecyclerAdapter {

        private List<Name> datas;

        public MyAdapter(List<Name> datas) {
            this.datas = datas;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_string_list, parent, false));
            Log.e("yih", "onCreateViewHolder");
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
            return 0;
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
