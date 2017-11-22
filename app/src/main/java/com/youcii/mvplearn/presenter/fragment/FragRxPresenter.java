package com.youcii.mvplearn.presenter.fragment;

import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.utils.RxUtils;
import com.youcii.mvplearn.view.fragment.interfaces.IFragRxView;

/**
 * Created by YouCii on 2016/12/17.
 */

public class FragRxPresenter {

    private IFragRxView iFragRxView;

    public FragRxPresenter(IFragRxView iFragRxView) {
        this.iFragRxView = iFragRxView;
    }

    public void rxTest(EasyEvent[] events) {
        RxUtils.rxTest(events, iFragRxView);
    }

}
