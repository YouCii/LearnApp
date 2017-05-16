package com.youcii.mvplearn.view.activity.interfaces;

import java.util.Map;

/**
 * Created by YouCii on 2017/1/17.
 */

public interface IHttpTestView {

    void doOnCallBack(String content);

    void addParam();

    String getUrl();

    Map<String, String> getParams();

    boolean isPostRequest();

    int getRequestTime();

    int getRequestFrequency();

}
