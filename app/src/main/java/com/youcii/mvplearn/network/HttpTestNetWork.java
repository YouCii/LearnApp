package com.youcii.mvplearn.network;

import com.youcii.mvplearn.adapter.okgo.CallBackAdapter;
import com.youcii.mvplearn.adapter.okgo.ResponseAdapter;
import com.youcii.mvplearn.base.BaseNetWork;
import com.youcii.mvplearn.utils.ThreadPool;

import java.util.Map;

/**
 * @author Administrator
 * @date 2017/11/30
 */
public class HttpTestNetWork extends BaseNetWork<String> {
    private boolean isPost;

    public HttpTestNetWork(String url, Map<String, String> paramList, boolean isPost) {
        setUrl(url);
        setParamsMap(paramList);
        this.isPost = isPost;
    }

    @Override
    protected void initUrl() {

    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void initCallBack() {
        setCallBack(new CallBackAdapter<String>() {
            @Override
            public void onSuccess(ResponseAdapter response) {
                ThreadPool.getThreadPool().execute(() -> {
                    try {
                        setChanged();
                        notifyObservers(convertResponse(response.getRawResponse()));

                    } catch (Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
            }
        });
    }

    /**
     * 发起请求
     */
    public void doExecute() {
        if (isPost) {
            postNetWork();
        } else {
            getNetWork();
        }
    }

}
