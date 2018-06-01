package com.youcii.mvplearn.presenter;

import com.lzy.okgo.OkGo;
import com.youcii.mvplearn.activity.interfaces.IHttpTestView;
import com.youcii.mvplearn.base.BasePresenter;
import com.youcii.mvplearn.network.HttpTestNetWork;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * @author YouCii
 * @date 2017/1/17
 */
public class HttpTestPresenter extends BasePresenter<IHttpTestView> implements Observer {

    private int requestTag;

    public HttpTestPresenter(IHttpTestView iHttpTestView) {
        super(iHttpTestView);
    }

    public void startRequest() {
        if (getView() != null) {
            requestTag = new Random().nextInt();
            HttpTestNetWork httpTestNetWork = new HttpTestNetWork(getView().getUrl(), getView().getParams(), getView().isPostRequest());
            httpTestNetWork.addObserver(this);
            for (int i = 0; i < getView().getRequestTime(); i++) {
                httpTestNetWork.doExecute(requestTag);
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof HttpTestNetWork) {
            if (getView() != null) {
                getView().doOnCallBack((String) data);
            }
        }
    }

    @Override
    public void detach() {
        OkGo.getInstance().cancelTag(requestTag);
    }
}
