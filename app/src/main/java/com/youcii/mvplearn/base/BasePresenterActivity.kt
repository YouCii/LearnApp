package com.youcii.mvplearn.base

import android.os.Bundle

/**
 * Created by jdw on 2018/6/1.
 */
abstract class BasePresenterActivity<V : BaseView, T : BasePresenter<V>> : BaseActivity() {

    protected lateinit var presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = initPresenter()
    }

    abstract fun initPresenter(): T

    override fun onStop() {
        super.onStop()
        if (isFinishing) {
            presenter.onDetach()
        }
    }

}