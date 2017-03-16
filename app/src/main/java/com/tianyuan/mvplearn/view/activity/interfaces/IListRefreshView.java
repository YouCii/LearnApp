package com.tianyuan.mvplearn.view.activity.interfaces;

import com.tianyuan.mvplearn.adapter.DeviceListAdapter.Device;

import java.util.List;

/**
 * Created by YouCii on 2017/1/17.
 */

public interface IListRefreshView {

    void initList(List<Device> list);

    void doOnFinish();

}
