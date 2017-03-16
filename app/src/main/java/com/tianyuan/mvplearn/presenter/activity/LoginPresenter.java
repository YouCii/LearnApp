package com.tianyuan.mvplearn.presenter.activity;


import com.tianyuan.mvplearn.network.LoginNetWork;
import com.tianyuan.mvplearn.presenter.activity.interfaces.ILoginPresenter;
import com.tianyuan.mvplearn.view.activity.interfaces.ILoginView;

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
