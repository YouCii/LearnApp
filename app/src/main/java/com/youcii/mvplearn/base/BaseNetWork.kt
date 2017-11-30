package com.youcii.mvplearn.base

import com.youcii.mvplearn.adapter.okgo.CallBackAdapter
import com.youcii.mvplearn.utils.HttpRequestBuilder
import java.util.Observable
import kotlin.collections.HashMap
import kotlin.collections.component1
import kotlin.collections.component2

/**
 * Created by Administrator on 2017/6/21.
 */
abstract class BaseNetWork<T> : Observable() { // 主构造方法在这写：abstract class BaseNetWork(val paramsMap: HashMap<String, String>)

    private var request: HttpRequestBuilder<T>? = null

    protected var url: String = ""
    protected var paramsMap: Map<String, String> = HashMap()
    protected var callBack: CallBackAdapter<T>? = null

    init { // 构造时默认调用
    }

    protected abstract fun initUrl()

    protected abstract fun initParams()

    protected abstract fun initCallBack()

    /**
     * 初始化, 并发起请求
     */
    fun postNetWork() {
        if (request == null) {
            initUrl()
            initParams()
            initCallBack()

            request = HttpRequestBuilder<T>().postRequest(url)
            for ((key, value) in paramsMap) {
                request?.addParams(key, value)
            }
        }
        request?.execute(callBack)
    }

    /**
     * 如果让子类可以重写的话需要加 open 关键字
     */
    fun getNetWork() {
        if (request == null) {
            initUrl()
            initParams()
            initCallBack()

            request = HttpRequestBuilder<T>().getRequest(url)
            for ((key, value) in paramsMap) {
                request?.addParams(key, value)
            }
        }
        request?.execute(callBack)
    }

}
