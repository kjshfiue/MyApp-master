package com.example.sks.myapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义 MyRecyclerAdapter ，item 点击事件
 * Created by sks on 2015/12/15.
 */
public abstract class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public OnItemClickListener listener;
    public OnItemLongClickListener listenerLong;
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listenerLong) {
        this.listenerLong = listenerLong;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position, long id);
    }
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position, long id);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View parent) {
                int pos = holder.getLayoutPosition();
                if (listener != null)
                    listener.onItemClick(parent, pos, getItemId(pos));
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View parent) {
                int pos = holder.getLayoutPosition();
                if (listenerLong != null)
                    listenerLong.onItemLongClick(parent, pos, getItemId(pos));
                return true;
            }
        });
    }

    public abstract int getItemCount();

    public abstract Object getItem();

    public abstract long getItemId(int position);

}
