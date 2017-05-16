package com.youcii.mvplearn.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.youcii.mvplearn.R;

public class MyItemViewHolder extends RecyclerView.ViewHolder {
    public View mView;
    public TextView mIdView;
    public TextView mContentView;

    public MyItemViewHolder(View view) {
        super(view);
        mView = view;
        mIdView = (TextView) view.findViewById(R.id.id);
        mContentView = (TextView) view.findViewById(R.id.content);
    }
}