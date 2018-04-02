package com.youcii.mvplearn.presenter;

import android.os.CountDownTimer;

import com.youcii.mvplearn.adapter.DeviceListAdapter;
import com.youcii.mvplearn.activity.interfaces.IListRefreshView;

import java.util.List;
import java.util.Observable;

/**
 * @author YouCii
 * @date 2017/1/17
 */
public class ListRefreshPresenter extends Observable {

    private IListRefreshView iListRefreshView;
    private CountDownTimer countDownTimer;

    public ListRefreshPresenter(IListRefreshView iListRefreshView) {
        this.iListRefreshView = iListRefreshView;
    }

    public void startRefresh(List<DeviceListAdapter.Device> list) {
        countDownTimer = new CountDownTimer(1000000, 200) {
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

                setChanged();
                notifyObservers();
            }

            @Override
            public void onFinish() {
                iListRefreshView.doOnFinish();
            }
        };
        countDownTimer.start();
    }

    public void stopRefresh() {
        countDownTimer.cancel();
    }

}
