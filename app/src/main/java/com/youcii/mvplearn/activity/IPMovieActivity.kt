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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_ip_movie.*

/**
 * 比较简单的类, 不用单独抽出方法, 过度设计只是浪费时间, 后续再适时重构就行
 */
class IPMovieActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_movie)

        btnQuery.setOnClickListener({ view ->
            if (view.id == R.id.btnQuery) {
                startRxRetrofit2()
            }
        })
        btnQuery.isFocusable = true
        btnQuery.isFocusableInTouchMode = true
        btnQuery.requestFocus()
    }

    /**
     * 业务场景一:
     *      两个接口同时回调(merge不会互相等待, zip必须合并response)
     */
    private fun startRxRetrofit1() {
        val ipObservable: Observable<IpQueryResponse> = RetrofitFactory.getRxIpInfo(etIp.text.toString())
        val movieObservable: Observable<TopMovieResponse> = RetrofitFactory.getTopMovie(Integer.parseInt(etMovieCount.text.toString()))
        Observable.zip(movieObservable, ipObservable,
                BiFunction<TopMovieResponse, IpQueryResponse, ArrayList<Any>> { t1, t2 ->
                    ArrayList<Any>().apply {
                        add(t1)
                        add(t2)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : BaseObserver<ArrayList<Any>>() {
                            override fun onNext(t: ArrayList<Any>) {
                                super.onNext(t)
                                tvResult.text = t[1].toString()
                                ToastUtils.showShortToast("刷新Movie UI") // TODO Glide
                            }

                            override fun onError(throwable: Throwable) {
                                super.onError(throwable)
                                tvResult.text = if (throwable is JsonSyntaxException) "数据转化错误" else throwable.toString()
                            }
                        }
                )
    }

    /**
     * 业务场景二:
     *      一个接口回调后再次请求另一个
     */
    private fun startRxRetrofit2() {
        val ipObservable: Observable<IpQueryResponse> = RetrofitFactory.getRxIpInfo(etIp.text.toString())
        val movieObservable: Observable<TopMovieResponse> = RetrofitFactory.getTopMovie(Integer.parseInt(etMovieCount.text.toString()))
        ipObservable
                .observeOn(AndroidSchedulers.mainThread())
                .map { tvResult.text = it.toString() }
                .observeOn(Schedulers.io())
                .flatMap({ movieObservable })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : BaseObserver<TopMovieResponse>() {
                    override fun onNext(t: TopMovieResponse) {
                        super.onNext(t)
                        ToastUtils.showShortToast("刷新Movie UI") // TODO Glide
                    }

                    override fun onError(throwable: Throwable) {
                        super.onError(throwable)
                        tvResult.text = if (throwable is JsonSyntaxException) "数据转化错误" else throwable.toString()
                    }
                })
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, IPMovieActivity::class.java))
        }
    }
}
