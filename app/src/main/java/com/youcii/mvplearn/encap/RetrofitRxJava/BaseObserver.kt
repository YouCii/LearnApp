package com.youcii.mvplearn.encap.RetrofitRxJava

import android.annotation.SuppressLint
import android.widget.Toast
import com.orhanobut.logger.Logger
import com.youcii.mvplearn.R
import com.youcii.mvplearn.app.App
import com.youcii.mvplearn.utils.PhoneUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by jdw on 2018/3/29.
 *
 * 用于封装公共回调, 并且隔离了第三方库
 */
@SuppressLint("ShowToast")
open class BaseObserver<T> : Observer<T> {

    private var disposable: Disposable? = null

    override fun onSubscribe(d: Disposable) {
        Logger.i("onSubscribe: " + Thread.currentThread().name)
        disposable = d
        if (PhoneUtils.isOnLine()) {
            toast.setText("开始请求")
            toast.show()
        } else {
            onError(Throwable(App.getContext().getString(R.string.network_error)))
        }
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
        disposable?.dispose()
        toast.setText("请求完成")
        toast.show()
    }

    override fun onError(throwable: Throwable) {
        disposable?.dispose()

        when (throwable) {
            is SocketTimeoutException ->
                toast.setText(App.getContext().getString(R.string.network_error))
            is ConnectException ->
                toast.setText(App.getContext().getString(R.string.network_error))
            else ->
                toast.setText("请求失败: " + throwable.toString())
        }
        toast.show()
    }

    companion object {
        /**
         * 静态变量可以保证所有的网络请求只能同时弹一个toast, 懒加载可以保证App已经初始化完成
         */
        val toast: Toast by lazy { Toast.makeText(App.getContext(), "", Toast.LENGTH_SHORT) }
    }

}