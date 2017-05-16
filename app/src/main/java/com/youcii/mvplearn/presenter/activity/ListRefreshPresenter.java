package com.youcii.mvplearn.presenter.activity;

import android.os.CountDownTimer;

import com.youcii.mvplearn.adapter.DeviceListAdapter;
import com.youcii.mvplearn.presenter.activity.interfaces.IListRefreshPresenter;
import com.youcii.mvplearn.view.activity.interfaces.IListRefreshView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by YouCii on 2017/1/17.
 */

public class ListRefreshPresenter implements IListRefreshPresenter {

    IListRefreshView iListRefreshView;

    public ListRefreshPresenter(IListRefreshView iListRefreshView) {
        this.iListRefreshView = iListRefreshView;
    }

    @Override
    public void startRefresh(List<DeviceListAdapter.Device> list) {
        new CountDownTimer(1000000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                list.get(0).distance = Math.random() * 100;
                list.get(1).distance = Math.random() * 100;
                list.get(2).distance = Math.random() * 100;
                list.get(3).distance = Math.random() * 100;
                list.get(4).distance = Math.random() * 100;
                list.get(5).distance = Math.random() * 100;
                list.get(6).distance = Math.random() * 100;
                list.get(7).distance = Math.random() * 100;
                list.get(8).distance = Math.random() * 100;
                list.get(9).distance = Math.random() * 100;

                EventBus.getDefault().post(list.get(0));
            }

            @Override
            public void onFinish() {
                iListRefreshView.doOnFinish();
            }
        }.start();

    }


}
