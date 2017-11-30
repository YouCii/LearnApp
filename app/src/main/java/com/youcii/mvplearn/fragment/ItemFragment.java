package com.youcii.mvplearn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.adapter.MyItemAdapter;
import com.youcii.mvplearn.base.BaseFragment;
import com.youcii.mvplearn.diyview.RecyclerItemDecoration;
import com.youcii.mvplearn.model.RecyclerBean;
import com.youcii.mvplearn.fragment.interfaces.IFragItemView;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends BaseFragment implements IFragItemView {

    private OnListFragmentInteractionListener mListener;
    private int mColumnCount = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_item, container, false);

        setRecyclerView(recyclerView, mColumnCount);

        recyclerView.setAdapter(new MyItemAdapter(getShowDate(), mListener));

        return recyclerView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = context instanceof OnListFragmentInteractionListener ? (OnListFragmentInteractionListener) context : null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setRecyclerView(RecyclerView recyclerView, int mColumnCount) {
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumnCount, StaggeredGridLayoutManager.VERTICAL));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator()); // 设置Item增加、移除动画
        recyclerView.setHasFixedSize(true);// 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.addItemDecoration(new RecyclerItemDecoration(getContext(), RecyclerItemDecoration.VERTICAL_LIST));// 添加分割线
    }

    @Override
    public List<RecyclerBean> getShowDate() {
        List<RecyclerBean> map = new ArrayList<>();
        map.add(new RecyclerBean("1", "一"));
        map.add(new RecyclerBean("2", "二"));
        map.add(new RecyclerBean("3", "三"));
        map.add(new RecyclerBean("4", "四"));
        return map;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(RecyclerBean recyclerBean);
    }

}
