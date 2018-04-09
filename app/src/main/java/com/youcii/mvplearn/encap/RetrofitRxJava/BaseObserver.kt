package com.youcii.mvplearn.encap.RetrofitRxJava

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.youcii.mvplearn.R
import com.youcii.mvplearn.app.App
import com.youcii.mvplearn.utils.PhoneUtils
import com.youcii.mvplearn.utils.ToastUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by jdw on 2018/3/29.
 *
 * 用于封装公共回调, 并且隔离了第三方库
 */
open class BaseObserver<T>(context: Context) : Observer<T> {

    private var disposable: Disposable? = null
    private var loadingDialog: Dialog? = null

    init {
        loadingDialog = Dialog(context)
        loadingDialog!!.setContentView(R.layout.dialog_loading)
        loadingDialog!!.setCanceledOnTouchOutside(false)
        loadingDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.argb(0, 0, 0, 0)))
    }

    override fun onSubscribe(d: Disposable) {
        disposable = d
        if (PhoneUtils.isOnLine()) {
            loadingDialog!!.show()
        } else {
            throw ConnectException(App.getContext().getString(R.string.network_error))
        }
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
        disposable?.dispose()
        loadingDialog!!.dismiss()
    }

    override fun onError(throwable: Throwable) {
        disposable?.dispose()
        loadingDialog!!.dismiss()

        when (throwable) {
            is SocketTimeoutException ->
                ToastUtils.showShortToast(App.getContext().getString(R.string.network_error))
            is ConnectException ->
                ToastUtils.showShortToast(App.getContext().getString(R.string.network_error))
            else ->
                ToastUtils.showShortToast("请求失败: " + throwable.toString())
        }
    }

}