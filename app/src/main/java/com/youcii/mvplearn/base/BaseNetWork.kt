package com.youcii.mvplearn.base

import com.lzy.okgo.OkGo
import com.lzy.okgo.request.PostRequest

/**
 * Created by Administrator on 2017/6/21.
 */

abstract class BaseNetWork { // 主构造方法在这写：abstract class BaseNetWork(val map: HashMap<String, String>)

    var url: String = ""
    var map: Map<String, String> = HashMap()

    init { // 构造时默认调用
    }

    protected abstract fun initUrl()

    protected open fun initParams() { // 如果让子类可以重写的话需要加 open 关键字
    }

    protected open fun requestNetWork() {
        initUrl()
        initParams()
    }

    protected fun <T> startPostRequest(callBack: BaseCallBack<T>?) {
        val postRequest: PostRequest = OkGo.post(url)
        for ((key, value) in map) postRequest.params(key, value)
        postRequest.execute(callBack)
    }


}
