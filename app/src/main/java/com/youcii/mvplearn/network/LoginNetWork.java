package com.youcii.mvplearn.network;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.adapter.okgo.CallBackAdapter;
import com.youcii.mvplearn.adapter.okgo.ResponseAdapter;
import com.youcii.mvplearn.app.App;
import com.youcii.mvplearn.base.BaseNetWork;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class LoginNetWork extends BaseNetWork<String> {
    private String user, password;

    public LoginNetWork(String user, String password) {
        this.user = user;
        this.password = password;
    }

    @Override
    protected void initUrl() {
        setUrl("http://www.baidu.com");
    }

    @Override
    protected void initParams() {
        getParamsMap().put("user", user);
        getParamsMap().put("password", password);
    }

    @Override
    protected void initCallBack() {
        setCallBack(new CallBackAdapter<String>() {
            @Override
            public void onSuccess(ResponseAdapter<String> response) {
                setChanged();
                notifyObservers(App.getInstance().getString(R.string.success));
            }

            @Override
            public void onError(ResponseAdapter<String> response) {
                super.onError(response);
                setChanged();
                notifyObservers(App.getInstance().getString(R.string.error));
            }
        });
    }
}
