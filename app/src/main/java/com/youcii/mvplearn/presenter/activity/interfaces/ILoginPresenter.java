package com.youcii.mvplearn.presenter.activity.interfaces;

/**
 * Created by YouCii on 2016/7/15.
 */
public interface ILoginPresenter {

    void login(String user, String password);

    void saveUser(String user, String password);

}
