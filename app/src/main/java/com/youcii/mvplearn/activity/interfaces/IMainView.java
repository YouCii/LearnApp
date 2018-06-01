package com.youcii.mvplearn.activity.interfaces;

import com.youcii.mvplearn.base.BaseView;

/**
 * Created by YouCii on 2016/8/11.
 */
public interface IMainView extends BaseView {

    void showToast(String content);

    void initFragmentList();

    void initToolBar(String title);

}
