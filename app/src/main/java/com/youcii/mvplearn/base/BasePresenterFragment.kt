package com.youcii.mvplearn.base

import android.os.Bundle

/**
 * Created by jdw on 2018/6/1.
 */
abstract class BasePresenterFragment<V : BaseView, T : BasePresenter<V>> : BaseFragment() {

    lateinit var presenter: T

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter = initPresenter()
    }

    abstract fun initPresenter(): T

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }
}