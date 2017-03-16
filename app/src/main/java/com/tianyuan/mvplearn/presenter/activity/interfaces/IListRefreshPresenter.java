package com.tianyuan.mvplearn.presenter.activity.interfaces;

import com.tianyuan.mvplearn.adapter.DeviceListAdapter;

import java.util.List;

/**
 * Created by YouCii on 2017/1/17.
 */

public interface IListRefreshPresenter {

    void startRefresh(List<DeviceListAdapter.Device> list);

}
