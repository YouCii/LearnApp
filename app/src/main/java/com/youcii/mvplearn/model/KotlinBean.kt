package com.youcii.mvplearn.model

/**
 * Created by Administrator on 2017/6/1.
 *
 * 1. KotlinBean 自动生成 get/set 方法
 * 2. 要想空参构造函数, 需要设置上默认值
 * 3. 目前java尚不能支持..特性，必须要写满所有参数
 */
data class KotlinBean constructor(var articleId: String, var type: String, var editCon: String, var height: String) {

    var responseId: String = "null"
        get() {
            return if (field == "null") "" else field
        }
        set(value) {
            field = if (value == "null") "" else value
        }

}