package com.youcii.mvplearn.presenter.activity;

import com.youcii.mvplearn.activity.interfaces.IHttpTestView;
import com.youcii.mvplearn.network.HttpTestNetWork;

import java.util.Observable;
import java.util.Observer;

/**
 * @author YouCii
 * @date 2017/1/17
 */
public class HttpTestPresenter implements Observer {

    private IHttpTestView iHttpTestView;
    private HttpTestNetWork httpTestNetWork;

    public HttpTestPresenter(IHttpTestView iHttpTestView) {
        this.iHttpTestView = iHttpTestView;
    }

    public void startRequest() {
        httpTestNetWork = new HttpTestNetWork(iHttpTestView.getUrl(), iHttpTestView.getParams(), iHttpTestView.isPostRequest());
        httpTestNetWork.addObserver(this);
        for (int i = 0; i < iHttpTestView.getRequestTime(); i++) {
            httpTestNetWork.doExecute();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof HttpTestNetWork) {
            iHttpTestView.doOnCallBack((String) data);
            httpTestNetWork.deleteObserver(this);
        }
    }

}
