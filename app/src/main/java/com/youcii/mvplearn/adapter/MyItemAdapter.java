package com.youcii.mvplearn.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.fragment.ItemFragment.OnListFragmentInteractionListener;
import com.youcii.mvplearn.model.RecyclerBean;

import java.util.List;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.MyItemViewHolder> {

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

        holder.itemView.setOnClickListener((view) -> {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(mValues.get(holder.getAdapterPosition()));
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {
        TextView mIdView;
        TextView mContentView;

        MyItemViewHolder(View view) {
            super(view);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }
    }
}
