package com.youcii.mvplearn.adapter.okgo;

import com.lzy.okgo.model.Response;

/**
 * Response 适配器
 *
 * @author Administrator
 * @date 2017/11/30
 * <p>
 * 用于承接网络访问框架与逻辑层, 避免更换框架时的大幅改动
 */
public class ResponseAdapter<T> {

    /**
     * 禁止外部调用, 否则隔离功能失效, 需要com.lzy.okgo.model.Response内数据时可以写某数据的get方法
     */
    private com.lzy.okgo.model.Response response;

    public ResponseAdapter(Response<T> response) {
        this.response = response;
    }

    /**
     * 禁止外部调用, 否则隔离功能失效, 需要com.lzy.okgo.model.Response内数据时可以写某数据的get方法
     */
    private Response getResponse() {
        return response;
    }

    public okhttp3.Response getRawResponse() {
        return response.getRawResponse();
    }
}
