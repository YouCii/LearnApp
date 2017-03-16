package com.tianyuan.mvplearn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianyuan.mvplearn.R;
import com.tianyuan.mvplearn.model.RecyclerBean;
import com.tianyuan.mvplearn.view.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.tianyuan.mvplearn.viewholder.MyItemViewHolder;

import java.util.List;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemViewHolder> {

    private final List<RecyclerBean> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemAdapter(List<RecyclerBean> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public MyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyItemViewHolder holder, int position) {
        holder.mIdView.setText(mValues.get(position).getId());
        holder.mContentView.setText(mValues.get(position).getContent());

        holder.mIdView.setSelected(true);
        holder.mContentView.setSelected(true);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(mValues.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
}
