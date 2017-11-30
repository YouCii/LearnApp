package com.youcii.mvplearn.activity.interfaces;

/**
 * @author YouCii
 * @date 2016/7/14
 */
public interface ILoginView {

    void clearPass();

    void turnProgress(boolean onOff);

    void turnLogin(boolean onOff);

    void showToast(String content);

    void loginSuccess();

    void loginFail();

    void startActivity();
}
