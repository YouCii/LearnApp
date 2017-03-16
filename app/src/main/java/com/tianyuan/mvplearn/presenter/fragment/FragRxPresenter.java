package com.tianyuan.mvplearn.presenter.fragment;

import com.tianyuan.mvplearn.model.EasyEvent;
import com.tianyuan.mvplearn.presenter.fragment.interfaces.IFragRxPresenter;
import com.tianyuan.mvplearn.utils.RxUtils;
import com.tianyuan.mvplearn.view.fragment.interfaces.IFragRxView;

/**
 * Created by YouCii on 2016/12/17.
 */

public class FragRxPresenter implements IFragRxPresenter {

    private IFragRxView iFragRxView;

    public FragRxPresenter(IFragRxView iFragRxView) {
        this.iFragRxView = iFragRxView;
    }

    @Override
    public void rxTest(EasyEvent[] events) {
        RxUtils.rxTest(events, iFragRxView);
    }

}
