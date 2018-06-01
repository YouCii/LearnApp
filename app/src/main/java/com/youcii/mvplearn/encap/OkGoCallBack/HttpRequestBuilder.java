package com.youcii.mvplearn.encap.OkGoCallBack;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;

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
     * 添加请求头
     */
    public HttpRequestBuilder<T> addHeader(String key, String value) {
        if (getRequest != null) {
            getRequest.headers(key, value);
        } else {
            postRequest.headers(key, value);
        }
        return this;
    }


    /**
     * 添加参数
     */
    public HttpRequestBuilder<T> addParams(String key, String value) {
        if (getRequest != null) {
            getRequest.params(key, value);
        } else {
            postRequest.params(key, value);
        }
        return this;
    }

    /**
     * 发起请求
     */
    public void execute(CallBackAdapter<T> callback, int tag) {
        if (getRequest != null) {
            getRequest.tag(tag).execute(callback);
        } else {
            postRequest.tag(tag).execute(callback);
        }
    }
}
