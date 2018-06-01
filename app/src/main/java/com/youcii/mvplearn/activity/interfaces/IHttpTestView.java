package com.youcii.mvplearn.activity.interfaces;

import com.youcii.mvplearn.base.BaseView;

import java.util.Map;

/**
 * Created by YouCii on 2017/1/17.
 */

public interface IHttpTestView extends BaseView {

    void doOnCallBack(String content);

    void addParam();

    String getUrl();

    Map<String, String> getParams();

    boolean isPostRequest();

    int getRequestTime();

    int getRequestFrequency();

}
