package com.youcii.mvplearn.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.TextView

import com.jakewharton.rxbinding2.view.RxView
import com.orhanobut.logger.Logger
import com.youcii.mvplearn.R
import com.youcii.mvplearn.activity.interfaces.ILoginView
import com.youcii.mvplearn.base.BasePresenterActivity
import com.youcii.mvplearn.presenter.LoginPresenter
import com.youcii.mvplearn.utils.PermissionUtils
import com.youcii.mvplearn.utils.PhoneUtils
import com.youcii.mvplearn.utils.ToastUtils
import com.youcii.mvplearn.utils.ViewUtils

import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author Administrator
 */
class LoginActivity : BasePresenterActivity<ILoginView, LoginPresenter>(), ILoginView, TextView.OnEditorActionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Logger.i("Login--onCreate")

        // 设置透明状态栏
        // if (Build.VERSION.SDK_INT >= 21) {
        //     // SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN 会保留状态栏, SYSTEM_UI_FLAG_FULLSCREEN 会默认不保留然后下拉显示
        //     // 暂未搞懂 View.SYSTEM_UI_FLAG_LAYOUT_STABLE 的作用
        //     getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        //     getWindow().setStatusBarColor(Color.TRANSPARENT);
        //
        //     ActionBar actionBar = getSupportActionBar();
        //     if (actionBar != null) {
        //         actionBar.hide();
        //     }
        // }

        et_user!!.setOnEditorActionListener(this)
        et_password!!.setOnEditorActionListener(this)

        showGifDrawable()

        subscribeLoginObserver(RxView.clicks(bt_login!!))
    }

    private var animatedDrawable: AnimatedImageDrawable? = null

    @SuppressLint("CheckResult")
    private fun showGifDrawable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .map {
                        val resource = ImageDecoder.createSource(resources, R.drawable.chicken)
                        ImageDecoder.decodeDrawable(resource)
                    }.observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ drawable ->
                        tv_gif!!.setImageDrawable(drawable)
                        if (drawable is AnimatedImageDrawable) {
                            animatedDrawable = drawable
                            animatedDrawable!!.repeatCount = 5
                            animatedDrawable!!.start()
                        }
                    }, { error -> ToastUtils.showShortToast(error.toString()) })
            tv_gif.setOnClickListener {
                if (animatedDrawable?.isRunning == false) {
                    animatedDrawable?.start()
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun subscribeLoginObserver(observable: Observable<Any>) {
        observable.throttleFirst(1, TimeUnit.SECONDS)
                .subscribe {
                    ViewUtils.hideInput(this)
                    presenter.login(et_user!!.text.toString(), et_password!!.text.toString())
                }
    }

    override fun initPresenter(): LoginPresenter {
        return LoginPresenter(this)
    }

    override fun onResume() {
        super.onResume()
        Logger.i("Login--onResume")

        // 取消焦点
        qxjd!!.isFocusable = true
        qxjd.isFocusableInTouchMode = true
        qxjd.requestFocus()

        turnLogin(PhoneUtils.isOnLine())

        PermissionUtils.examinePermission(this)
    }

    override fun loginSuccess() {
        turnProgress(false)
        clearPass()
        startActivity()
    }

    override fun loginFail(errorInfo: String) {
        turnProgress(false)
        showToast("登录失败: $errorInfo")
    }

    override fun clearPass() {
        et_password!!.setText("")
    }

    override fun turnProgress(onOff: Boolean) {
        if (onOff) {
            login_progress!!.visibility = View.VISIBLE
        } else {
            login_progress!!.visibility = View.INVISIBLE
        }
    }

    override fun turnLogin(onOff: Boolean) {
        bt_login!!.isEnabled = onOff
    }

    override fun showToast(content: String) {
        ToastUtils.showShortSnack(window.decorView, content)
    }

    override fun startActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("userName", et_user!!.text.toString())
        startActivity(intent)
    }

    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        when (v.id) {
            R.id.et_user -> et_password!!.requestFocus()
            R.id.et_password -> subscribeLoginObserver(Observable.fromArray(1))
            else -> {
            }
        }
        return true
    }

    override fun onStart() {
        super.onStart()
        Logger.i("Login--onStart")
    }

    override fun onPause() {
        super.onPause()
        Logger.i("Login--onPause")
    }

    override fun onStop() {
        super.onStop()
        Logger.i("Login--onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.i("Login--onDestroy")

        // A > B
        // Login--onPause
        // Main--onCreate
        // Main--onStart
        // Main--onResume
        // Login--onStop

        // B > A
        // Main--onPause
        // Login--onStart
        // Login--onResume
        // Main--onStop
        // Main--onDestroy

        // Activity会在onResume后显示
    }
}

