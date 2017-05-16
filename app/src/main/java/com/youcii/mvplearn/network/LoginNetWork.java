package com.youcii.mvplearn.network;

import android.os.CountDownTimer;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by YouCii on 2016/7/15.
 */
public class LoginNetWork {

    String user, password;

    public LoginNetWork(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public void requestNetWork() {

        // 模拟访问网络服务
        new CountDownTimer(2000, 2000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                EventBus.getDefault().post("登陆成功");
            }
        }.start();

    }
}
