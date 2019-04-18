package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonSyntaxException
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.MovieAdapter
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.encap.retrofit_rx.BaseObserver
import com.youcii.mvplearn.encap.retrofit_rx.RetrofitFactory
import com.youcii.mvplearn.greendao.DaoManager
import com.youcii.mvplearn.model.MovieEntity
import com.youcii.mvplearn.response.IpQueryResponse
import com.youcii.mvplearn.response.TopMovieResponse
import com.youcii.mvplearn.utils.ViewUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_ip_movie.*

/**
 * 比较简单的类, 不用单独抽出方法, 过度设计只是浪费时间, 后续再适时重构就行
 */
class IPMovieActivity : BaseActivity() {

    private val dataList: ArrayList<MovieEntity> = ArrayList()

    /**
     * 这里使用了disposable集合来统一管理disposable, 其他的方式见
     * @see com.youcii.mvplearn.presenter.FragRxPresenter
     */
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_movie)

        btnQuery.isFocusable = true
        btnQuery.isFocusableInTouchMode = true
        btnQuery.requestFocus()

        btnQuery.setOnClickListener {
            ViewUtils.hideInput(this@IPMovieActivity)
            startRxRetrofit2()
        }
        rvMovie.itemAnimator = DefaultItemAnimator() // 设置Item增加、移除动画
        rvMovie.layoutManager = GridLayoutManager(this, 4)
        rvMovie.adapter = MovieAdapter(this, dataList)
    }

    /**
     * 业务场景一:
     *      两个接口同时回调(merge不会互相等待, zip必须合并response)
     */
    private fun startRxRetrofit1() {
        val ipObservable: Observable<IpQueryResponse> = RetrofitFactory.getRxIpInfo(etIp.text.toString())
        val movieObservable: Observable<TopMovieResponse> = RetrofitFactory.getTopMovie(Integer.parseInt(etMovieCount.text.toString()))
        val observer = object : BaseObserver<ArrayList<Any>>(this) {
            override fun onNext(t: ArrayList<Any>) {
                super.onNext(t)
                tvResult.text = t[1].toString()

                dataList.clear()
                dataList.addAll((t[0] as TopMovieResponse).subjects)
                rvMovie.adapter?.notifyDataSetChanged()
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                tvResult.text = if (throwable is JsonSyntaxException) {
                    "数据转化错误"
                } else {
                    throwable.toString()
                }
            }
        }
        compositeDisposable.add(observer)
        Observable.zip(movieObservable, ipObservable,
                BiFunction<TopMovieResponse, IpQueryResponse, ArrayList<Any>> { t1, t2 ->
                    ArrayList<Any>().apply {
                        add(t1)
                        add(t2)
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    /**
     * 业务场景二:
     *      一个接口回调后再次请求另一个
     */
    private fun startRxRetrofit2() {
        val ipObservable: Observable<IpQueryResponse> = RetrofitFactory.getRxIpInfo(etIp.text.toString())
        val movieObservable: Observable<TopMovieResponse> = RetrofitFactory.getTopMovie(Integer.parseInt(etMovieCount.text.toString()))
        val observer = object : BaseObserver<ArrayList<MovieEntity>>(this) {
            override fun onNext(t: ArrayList<MovieEntity>) {
                super.onNext(t)
                dataList.clear()
                dataList.addAll(t)
                rvMovie.adapter?.notifyDataSetChanged()
            }

            override fun onError(throwable: Throwable) {
                super.onError(throwable)
                tvResult.text = if (throwable is JsonSyntaxException) {
                    "数据转化错误"
                } else {
                    throwable.toString()
                }
            }
        }
        compositeDisposable.add(observer)
        ipObservable
                .observeOn(AndroidSchedulers.mainThread())
                .map {
                    tvResult.text = it.toString()
                }
                .observeOn(Schedulers.io())
                .flatMap { movieObservable }
                .observeOn(Schedulers.io())
                .map {
                    DaoManager.getMovieEntityDao().insertOrReplaceInTx(it.subjects)
                    return@map it.subjects
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
        (rvMovie.adapter as MovieAdapter).release()
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, IPMovieActivity::class.java))
        }
    }
}
