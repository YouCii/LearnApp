package com.youcii.mvplearn.fragment.interfaces;

import android.content.Context;

import com.youcii.mvplearn.base.BaseView;

/**
 * Created by YouCii on 2016/12/3.
 */

public interface IFragSocketView extends BaseView {

    Context getContext();

    void showShortSnack();

    void addMessageText(String s);

    String getIp();

    String getPort();
}
