package com.youcii.mvplearn.base

import com.youcii.mvplearn.utils.HttpRequestBuilder
import java.util.Observable

/**
 * Created by Administrator on 2017/6/21.
 */
abstract class BaseNetWork<T> : Observable() { // 主构造方法在这写：abstract class BaseNetWork(val paramsMap: HashMap<String, String>)

    protected var url: String = ""
    protected var paramsMap: Map<String, String> = HashMap()
    protected var callBack: BaseCallBack<T>? = null

    init { // 构造时默认调用
    }

    protected abstract fun initUrl()

    protected abstract fun initParams()

    protected abstract fun initCallBack()

    /**
     * 如果让子类可以重写的话需要加 open 关键字
     */
    public fun postNetWork() {
        initUrl()
        initParams()
        initCallBack()
        startPostRequest(callBack)
    }

    public fun getNetWork() {
        initUrl()
        initParams()
        initCallBack()
        startGetRequest(callBack)
    }

    private fun startPostRequest(callBack: BaseCallBack<T>?) {
        val postRequest: HttpRequestBuilder = HttpRequestBuilder.getInstance().postRequest(url)
        for ((key, value) in paramsMap) postRequest.addParams(key, value)
        postRequest.execute(callBack)
    }

    private fun startGetRequest(callBack: BaseCallBack<T>?) {
        val getRequest: HttpRequestBuilder = HttpRequestBuilder.getInstance().getRequest(url)
        for ((key, value) in paramsMap) getRequest.addParams(key, value)
        getRequest.execute(callBack)
    }

}
