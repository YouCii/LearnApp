package com.youcii.mvplearn.adapter.okgo

import com.lzy.okgo.convert.Converter
import com.youcii.mvplearn.utils.GsonUtils
import okhttp3.Response

/**
 * Created by jdw on 2017/12/4.
 *
 * 类似于CallBackAdapter, 实现对json的处理
 * 用一个T, 还得传一个T, 有点恶心, 但是类似于CallBackAdapter的反射在这里报错
 */
class JsonConverter<T>(private var cls: Class<T>) : Converter<T> {

    override fun convertResponse(p0: Response?): T {
        return GsonUtils.json2Bean(p0?.body()?.string(), cls)
    }

}