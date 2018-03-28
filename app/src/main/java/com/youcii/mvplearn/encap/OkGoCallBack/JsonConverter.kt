package com.youcii.mvplearn.encap.OkGoCallBack

import com.google.gson.stream.JsonReader
import com.lzy.okgo.convert.Converter
import com.youcii.mvplearn.utils.GsonUtils
import okhttp3.Response

/**
 * Created by jdw on 2017/12/4.
 *
 * 用于承接网络访问框架与逻辑层, 避免更换框架时的大幅改动
 * 类似于CallBackAdapter, 实现对json的处理
 * ps.. 用一个T, 还得传一个T, 有点恶心, 但是类似于CallBackAdapter的反射在这里报错
 */
class JsonConverter<T>(private var cls: Class<T>) : Converter<T> {

    override fun convertResponse(p0: Response?): T {
        val jsonReader = JsonReader(p0?.body()?.charStream())
        return GsonUtils.json2Bean(jsonReader, cls)
    }

}