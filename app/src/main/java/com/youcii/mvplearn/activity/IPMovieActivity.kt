package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.JsonSyntaxException
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.encap.RetrofitRxJava.BaseObserver
import com.youcii.mvplearn.encap.RetrofitRxJava.RetrofitFactory
import com.youcii.mvplearn.response.IpQueryResponse
import com.youcii.mvplearn.response.TopMovieResponse
import com.youcii.mvplearn.utils.ToastUtils
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_ip_movie.*

class IPMovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_movie)

        btnQuery.setOnClickListener({ view ->
            if (view.id == R.id.btnQuery) {
                startRxRetrofit()
            }
        })
        btnQuery.isFocusable = true
        btnQuery.isFocusableInTouchMode = true
        btnQuery.requestFocus()
    }

    /**
     * 比较简单的类, 不用单独抽出方法, 过度设计只是浪费时间, 后续再适时重构就行
     */
    private fun startRxRetrofit() {
        val ipObservable: Observable<IpQueryResponse> = RetrofitFactory.getRxIpInfo(etIp.text.toString())
        val movieObservable: Observable<TopMovieResponse> = RetrofitFactory.getTopMovie(Integer.parseInt(etMovieCount.text.toString()))
        Observable.merge(movieObservable, ipObservable).subscribe(
                object : BaseObserver<Any>() {
                    override fun onNext(t: Any) {
                        super.onNext(t)
                        when (t) {
                            is IpQueryResponse -> {
                                tvResult.text = t.toString()
                            }
                            is TopMovieResponse -> {
                                ToastUtils.showShortToast("刷新Movie UI") // TODO Glide
                            }
                            else -> {
                                onError(Throwable("类型错误"))
                            }
                        }
                    }

                    override fun onError(throwable: Throwable) {
                        super.onError(throwable)
                        tvResult.text = if (throwable is JsonSyntaxException) "无数据" else throwable.toString()
                    }
                }
        )
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, IPMovieActivity::class.java))
        }
    }
}
