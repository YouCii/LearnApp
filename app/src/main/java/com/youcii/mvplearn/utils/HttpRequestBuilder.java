package com.youcii.mvplearn.utils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.youcii.mvplearn.adapter.okgo.CallBackAdapter;

/**
 * Created by YouCii on 2016/12/29.
 * <p>
 * 用于承接网络访问框架与逻辑层, 避免更换框架时的大幅改动
 */

public class HttpRequestBuilder<T> {

    /**
     * getRequest 构建
     */
    private GetRequest<T> getRequest = null;

    public HttpRequestBuilder<T> getRequest(String url) {
        getRequest = OkGo.get(url);
        return this;
    }

    /**
     * postRequest 构建
     */
    private PostRequest<T> postRequest = null;

    public HttpRequestBuilder<T> postRequest(String url) {
        postRequest = OkGo.post(url);
        return this;
    }

    /**
     * 添加参数
     */
    public HttpRequestBuilder<T> addParams(String key, String map) {
        if (getRequest != null) {
            getRequest.params(key, map);
        } else {
            postRequest.params(key, map);
        }
        return this;
    }

    /**
     * 发起请求
     */
    public void execute(CallBackAdapter<T> callback) {
        if (getRequest != null) {
            getRequest.execute(callback);
        } else {
            postRequest.execute(callback);
        }
    }
}
