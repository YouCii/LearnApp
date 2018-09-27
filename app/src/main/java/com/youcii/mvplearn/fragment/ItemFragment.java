package com.youcii.mvplearn.fragment;

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
import com.youcii.mvplearn.fragment.interfaces.IFragItemView;
import com.youcii.mvplearn.model.RecyclerBean;
import com.youcii.mvplearn.widget.RecyclerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends BaseFragment implements IFragItemView {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_item, container, false);

        setRecyclerView(recyclerView, 1);

        recyclerView.setAdapter(new MyItemAdapter(getShowDate()));

        return recyclerView;
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
        map.add(new RecyclerBean("1", "一", RecyclerBean.BeanType.TEXT));
        map.add(new RecyclerBean("2", "https://avatars0.githubusercontent.com/u/17899073?s=40&v=4", RecyclerBean.BeanType.IMAGE));
        map.add(new RecyclerBean("3", "三", RecyclerBean.BeanType.TEXT));
        map.add(new RecyclerBean("4", "https://upload.jianshu.io/users/upload_avatars/3103396/b7dee070-e665-487f-ac91-42faf20d2147.png?imageMogr2/auto-orient/strip|imageView2/1/w/120/h/120", RecyclerBean.BeanType.IMAGE));
        return map;
    }

}
