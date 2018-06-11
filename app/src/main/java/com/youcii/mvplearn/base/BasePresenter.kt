package com.youcii.mvplearn.base

import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by jdw on 2018/5/30.
 *
 * 用于处理presenter的内存泄漏问题
 *
 * 这里的 T:BaseView 是泛型边界, 类似于java里的 T extends BaseView
 * 泛型边界与逆变协变没有关系, 使用泛型通配符的时候才会有逆变协变, 声明类的时候不能也无需使用通配符
 */
abstract class BasePresenter<T : BaseView>(baseView: T) : Observable() {

    private val viewWeak = WeakReference(baseView)

    protected fun getView(): T? {
        return viewWeak.get()
    }

    internal fun onDetach() {
        viewWeak.clear()
        detach()
    }

    protected abstract fun detach()

}