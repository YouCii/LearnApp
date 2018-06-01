package com.youcii.mvplearn.presenter;

import android.os.CountDownTimer;

import com.youcii.mvplearn.activity.interfaces.IListRefreshView;
import com.youcii.mvplearn.adapter.DeviceListAdapter;
import com.youcii.mvplearn.base.BasePresenter;

import java.util.List;

/**
 * @author YouCii
 * @date 2017/1/17
 */
public class ListRefreshPresenter extends BasePresenter<IListRefreshView> {

    private CountDownTimer countDownTimer;

    public ListRefreshPresenter(IListRefreshView iListRefreshView) {
        super(iListRefreshView);
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
                if (getView() != null) {
                    getView().doOnFinish();
                }
            }
        };
        countDownTimer.start();
    }

    @Override
    public void detach() {
        countDownTimer.cancel();
    }

}
