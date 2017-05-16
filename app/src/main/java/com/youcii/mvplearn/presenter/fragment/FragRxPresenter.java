package com.youcii.mvplearn.presenter.fragment;

import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.presenter.fragment.interfaces.IFragRxPresenter;
import com.youcii.mvplearn.utils.RxUtils;
import com.youcii.mvplearn.view.fragment.interfaces.IFragRxView;

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
