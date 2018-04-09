package com.youcii.mvplearn.encap.RetrofitRxJava

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.youcii.mvplearn.R
import com.youcii.mvplearn.app.App
import com.youcii.mvplearn.utils.PhoneUtils
import com.youcii.mvplearn.utils.ToastUtils
import io.reactivex.observers.DisposableObserver
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Created by jdw on 2018/3/29.
 *
 * 用于封装公共回调, 并且隔离了第三方库
 * open使其可以被继承, 目前仅用在了匿名内部类里
 */
open class BaseObserver<T>(context: Context) : DisposableObserver<T>() {

    private val loadingDialog: Dialog = Dialog(context)

    init {
        loadingDialog.setContentView(R.layout.dialog_loading)
        loadingDialog.setCanceledOnTouchOutside(false)
        loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.argb(0, 0, 0, 0)))
    }

    override fun onStart() {
        super.onStart()
        if (PhoneUtils.isOnLine()) {
            loadingDialog.show()
        } else {
            throw ConnectException(App.getContext().getString(R.string.network_error))
        }
    }

    override fun onNext(t: T) {
    }

    override fun onComplete() {
        loadingDialog.dismiss()
    }

    override fun onError(throwable: Throwable) {
        loadingDialog.dismiss()

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