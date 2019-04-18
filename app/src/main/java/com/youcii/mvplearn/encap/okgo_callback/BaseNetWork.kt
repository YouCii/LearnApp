package com.youcii.mvplearn.encap.okgo_callback

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.component1
import kotlin.collections.component2

/**
 * Created by Administrator on 2017/6/21.
 *
 *  用于封装各网络请求共同的部分, 如: 继承Observable, add共同的header/params等
 *
 *  注: 如果http相关逻辑不是很多的话, 也可以把这一层放在HttpRequestBuilder里面
 */
abstract class BaseNetWork<T> : Observable() { // 主构造方法在这写：abstract class BaseNetWork(val paramsMap: HashMap<String, String>)

    private var request: HttpRequestBuilder<T>? = null

    protected var url: String = ""
    protected var paramsMap: Map<String, String> = HashMap()
    protected var callBack: CallBackAdapter<T>? = null

    init { // 构造时默认调用
    }

    /**
     * 意义在于:
     *  1. 接收到传入的url后进行请求前的修改;
     *  2. 未传入url, 自行设置url;
     */
    protected abstract fun initUrl()

    /**
     * 意义在于:
     *  1. 接收到传入的params后进行请求前的判断处理;
     *  2. 添加可全局获取的参数等;
     */
    protected abstract fun initParams()

    /**
     * 意义在于:
     *  1. 初始化callback
     */
    protected abstract fun initCallBack()

    /**
     * 初始化, 并发起请求
     */
    fun postNetWork(tag: Int) {
        if (request == null) {
            initUrl()
            initParams()
            initCallBack()

            request = HttpRequestBuilder<T>().postRequest(url)
            // 此处可以添加共同的header/params等
            for ((key, value) in paramsMap) {
                request!!.addParams(key, value)
            }
        }
        request!!.execute(callBack, tag)
    }

    /**
     * 如果让子类可以重写的话需要加 open 关键字
     */
    fun getNetWork(tag: Int) {
        if (request == null) {
            initUrl()
            initParams()
            initCallBack()

            request = HttpRequestBuilder<T>().getRequest(url)
            // 此处可以添加共同的header/params等
            for ((key, value) in paramsMap) {
                request!!.addParams(key, value)
            }
        }
        request!!.execute(callBack, tag)
    }

}
