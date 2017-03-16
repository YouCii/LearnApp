package com.tianyuan.mvplearn.utils;


import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;


/**
 * Created by YouCii on 2016/12/29.
 * <p>
 * 用于承接网络访问框架与逻辑层, 避免更换框架时的大幅改动
 */

public class HttpRequestBuilder {

    public static HttpRequestBuilder getInstance() {
        return new HttpRequestBuilder();
    }

    public void execute(AbsCallback callback) {
        if (getRequest != null)
            getRequest.execute(callback);
        else
            postRequest.execute(callback);
    }

    /**
     * 设置超时时间的请求
     *
     * @param connTimeOut  连接超时时间
     * @param readTimeOut  读超时
     * @param writeTimeOut 写超时
     * @param callback     访问回调
     */
    public void execute(long connTimeOut, long readTimeOut, long writeTimeOut, AbsCallback callback) {
        if (getRequest != null)
            getRequest.connTimeOut(connTimeOut)
                    .readTimeOut(readTimeOut)
                    .writeTimeOut(writeTimeOut)
                    .execute(callback);
        else
            postRequest.connTimeOut(connTimeOut)
                    .readTimeOut(readTimeOut)
                    .writeTimeOut(writeTimeOut)
                    .execute(callback);
    }

    public HttpRequestBuilder addParams(String key, String map) {
        if (getRequest != null)
            getRequest.params(key, map);
        else
            postRequest.params(key, map);
        return this;
    }

    /**
     * getRequest 构建
     */
    private GetRequest getRequest = null;

    public HttpRequestBuilder GetRequest(String url) {
        getRequest = OkGo.get(url);
        return this;
    }

    /**
     * PostRequest 构建
     */
    private PostRequest postRequest = null;

    public HttpRequestBuilder PostRequest(String url) {
        postRequest = OkGo.post(url);
        return this;
    }

}
