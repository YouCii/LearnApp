package com.youcii.mvplearn.presenter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.youcii.mvplearn.R
import com.youcii.mvplearn.utils.ToastUtils
import java.lang.Exception
import java.lang.reflect.Field
import java.lang.reflect.Proxy

/**
 * Created by jingdongwei on 2019/11/07.
 */
object HookUtils {

    /**
     * 因为LayoutInflater.from(context)获取到的并不是单例, 所以不会全局生效
     */
    fun hook(context: Context) {
        try {
            val layoutInflater = LayoutInflater.from(context)
            val mFactory2: Field = LayoutInflater::class.java.getDeclaredField("mFactory2")
            mFactory2.isAccessible = true

            val oldField = mFactory2.get(layoutInflater)
            val hookFactory2 = Proxy.newProxyInstance(context.javaClass.classLoader, arrayOf<Class<*>>(LayoutInflater.Factory2::class.java)) { _, method, args ->
                val result = method.invoke(oldField, *args)
                if (result is ImageView) {
                    result.setImageResource(R.drawable.immersive)
                }
                return@newProxyInstance result
            }

            mFactory2.set(layoutInflater, hookFactory2)
        } catch (exception: Exception) {
            ToastUtils.showShortToast("hook失败: $exception")
        }
    }

}