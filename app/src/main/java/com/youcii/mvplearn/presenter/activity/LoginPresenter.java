package com.youcii.mvplearn.presenter.activity;


import com.youcii.mvplearn.network.LoginNetWork;
import com.youcii.mvplearn.presenter.activity.interfaces.ILoginPresenter;
import com.youcii.mvplearn.view.activity.interfaces.ILoginView;

/**
 * Created by YouCii on 2016/7/15.
 */
public class LoginPresenter implements ILoginPresenter {

    private ILoginView iLoginView;

    public LoginPresenter(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void login(String user, String password) {
        iLoginView.turnProgress(true);
        new LoginNetWork(user, password).requestNetWork();
    }

    @Override
    public void saveUser(String user, String password) {
        iLoginView.showToast("getEvent");
    }

}
