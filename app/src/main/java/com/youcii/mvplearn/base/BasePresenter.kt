package com.youcii.mvplearn.base

import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by jdw on 2018/5/30.
 *
 * 用于处理presenter的内存泄漏问题
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