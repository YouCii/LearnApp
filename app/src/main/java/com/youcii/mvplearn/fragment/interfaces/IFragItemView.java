package com.youcii.mvplearn.fragment.interfaces;

import android.support.v7.widget.RecyclerView;

import com.youcii.mvplearn.model.RecyclerBean;

import java.util.List;

/**
 * Created by YouCii on 2016/8/17.
 */

public interface IFragItemView {

    void setRecyclerView(RecyclerView recyclerView, int mColumnCount);

    List<RecyclerBean> getShowDate();

}
