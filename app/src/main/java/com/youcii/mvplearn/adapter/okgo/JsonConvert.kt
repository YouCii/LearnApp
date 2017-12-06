package com.youcii.mvplearn.adapter.okgo

import com.lzy.okgo.convert.Converter
import com.youcii.mvplearn.utils.GsonUtils
import okhttp3.Response
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by jdw on 2017/12/4.
 *
 * 类似于CallBackAdapter, 实现对json的处理
 */
class JsonConvert<T> : Converter<T> {

    override fun convertResponse(p0: Response?): T {
        return GsonUtils.json2Bean(p0?.body().toString(), getTClass())
    }

    /**
     * 反射获取T.class TODO 有问题
     */
    private fun getTClass(): Class<T> {
        var type: Type = javaClass.genericSuperclass
        var parameterizeType: ParameterizedType? = null
        while (parameterizeType == null) {
            if (type is ParameterizedType) {
                parameterizeType = type
            } else {
                type = (type as Class<T>).genericSuperclass
            }
        }
        return parameterizeType.actualTypeArguments[0] as Class<T>
    }

}