package com.youcii.mvplearn.presenter.activity;

import android.os.Handler;
import android.os.Message;

import com.lzy.okgo.callback.StringCallback;
import com.youcii.mvplearn.utils.HttpRequestBuilder;
import com.youcii.mvplearn.view.activity.interfaces.IHttpTestView;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by YouCii on 2017/1/17.
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
            builder = builder.PostRequest(iHttpTestView.getUrl());
        } else {
            builder = builder.GetRequest(iHttpTestView.getUrl());
        }

        for (Map.Entry<String, String> entry : paramList.entrySet()) {
            builder.addParams(entry.getKey(), entry.getValue());
        }

        for (int i = 0; i < iHttpTestView.getRequestTime(); i++){

            builder.execute(10000, 20000, 20000, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    new Thread() {
                        @Override
                        public void run() {
                            iHttpTestView.doOnCallBack(s);
                        }
                    }.start();
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                }
            });

        }
//            handler.sendEmptyMessageDelayed(0, iHttpTestView.getRequestFrequency() * 1000);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            builder.execute(10000, 20000, 20000, new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    new Thread() {
                        @Override
                        public void run() {
                            iHttpTestView.doOnCallBack(s);
                        }
                    }.start();
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                }
            });
        }
    };

}
