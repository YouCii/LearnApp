package com.youcii.mvplearn.network;

import com.youcii.mvplearn.encap.OkGoCallBack.BaseNetWork;
import com.youcii.mvplearn.encap.OkGoCallBack.CallBackAdapter;

import java.util.Map;

/**
 * @author Administrator
 * @date 2017/11/30
 *
 * 此中间层的作用是为了把网络请求单独抽出. 其实这里仅有isPost这一处额外的逻辑, 并不值得单独抽出, 只是为了模拟 单独网络请求相关逻辑较为复杂时的情况.
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
            public void onSuccess(String string) {
                setChanged();
                notifyObservers(string);
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
