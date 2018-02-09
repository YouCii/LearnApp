package com.youcii.mvplearn.model

/**
 * Created by jdw on 2017/12/6.
 */
class SingleClass private constructor() {

    fun method() {
    }

    companion object {
        /**
         * 懒汉模式单例
         * @link http://blog.csdn.net/j550341130/article/details/78731480
         */
        @JvmStatic
        val instance: SingleClass by lazy { SingleClass() }
    }

}