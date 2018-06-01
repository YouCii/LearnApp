package com.youcii.mvplearn.activity.interfaces;

import com.youcii.mvplearn.adapter.DeviceListAdapter.Device;
import com.youcii.mvplearn.base.BaseView;

import java.util.List;

/**
 * Created by YouCii on 2017/1/17.
 */

public interface IListRefreshView extends BaseView {

    void initList(List<Device> list);

    void doOnFinish();

}
