package com.youcii.mvplearn.base

import com.youcii.mvplearn.utils.HttpRequestBuilder

/**
 * Created by Administrator on 2017/6/21.
 */

abstract class BaseNetWork { // 主构造方法在这写：abstract class BaseNetWork(val paramsMap: HashMap<String, String>)

    var url: String = ""
    var paramsMap: Map<String, String> = HashMap()

    init { // 构造时默认调用
    }

    protected abstract fun initUrl()

    protected abstract fun initParams()

    /**
     * 如果让子类可以重写的话需要加 open 关键字
     */
    protected open fun requestNetWork() {
        initUrl()
        initParams()
    }

    protected fun <T> startPostRequest(callBack: BaseCallBack<T>?) {
        val postRequest: HttpRequestBuilder = HttpRequestBuilder.getInstance().postRequest(url)
        for ((key, value) in paramsMap) postRequest.addParams(key, value)
        postRequest.execute(callBack)
    }

    protected fun <T> startGetRequest(callBack: BaseCallBack<T>?) {
        val getRequest: HttpRequestBuilder = HttpRequestBuilder.getInstance().getRequest(url)
        for ((key, value) in paramsMap) getRequest.addParams(key, value)
        getRequest.execute(callBack)
    }

}
