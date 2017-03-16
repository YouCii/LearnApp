package com.tianyuan.mvplearn.view.fragment.interfaces;

import android.support.v7.widget.RecyclerView;

import com.tianyuan.mvplearn.model.RecyclerBean;

import java.util.List;

/**
 * Created by YouCii on 2016/8/17.
 */

public interface IFragItemView {

    void setRecyclerView(RecyclerView recyclerView, int mColumnCount);

    List<RecyclerBean> getShowDate();

}
