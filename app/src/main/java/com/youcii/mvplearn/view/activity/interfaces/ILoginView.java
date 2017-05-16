package com.youcii.mvplearn.view.activity.interfaces;

/**
 * Created by YouCii on 2016/7/14.
 */
public interface ILoginView {

    void clearPass();

    void turnProgress(boolean onOff);

    void turnLogin(boolean onOff);

    void showToast(String content);

    void startActivity();
}
