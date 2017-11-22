package com.youcii.mvplearn.presenter.activity;

import com.lzy.okgo.callback.StringCallback;
import com.youcii.mvplearn.utils.HttpRequestBuilder;
import com.youcii.mvplearn.utils.ThreadPool;
import com.youcii.mvplearn.view.activity.interfaces.IHttpTestView;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author YouCii
 * @date 2017/1/17
 */
public class HttpTestPresenter {

    private IHttpTestView iHttpTestView;
    private HttpRequestBuilder builder;

    public HttpTestPresenter(IHttpTestView iHttpTestView) {
        this.iHttpTestView = iHttpTestView;
    }

    public void startRequest() {
        Map<String, String> paramList = iHttpTestView.getParams();

        builder = HttpRequestBuilder.getInstance();
        if (iHttpTestView.isPostRequest()) {
            builder = builder.postRequest(iHttpTestView.getUrl());
        } else {
            builder = builder.getRequest(iHttpTestView.getUrl());
        }

        for (Map.Entry<String, String> entry : paramList.entrySet()) {
            builder.addParams(entry.getKey(), entry.getValue());
        }

        for (int i = 0; i < iHttpTestView.getRequestTime(); i++){
            builder.execute(10000, 20000, 20000, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
	                ThreadPool.getThreadPool().execute(() -> iHttpTestView.doOnCallBack(s));
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                }
            });
        }
    }

}
