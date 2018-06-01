package com.youcii.mvplearn.activity.interfaces;

import com.youcii.mvplearn.base.BaseView;

/**
 * @author YouCii
 * @date 2016/7/14
 */
public interface ILoginView extends BaseView {

    void clearPass();

    void turnProgress(boolean onOff);

    void turnLogin(boolean onOff);

    void showToast(String content);

    void loginSuccess();

    void loginFail(String errorInfo);

    void startActivity();
}
