package com.youcii.mvplearn.encap.RetrofitRxJava

import com.youcii.mvplearn.constant.ConnectConfig
import com.youcii.mvplearn.constant.UrlConstant
import com.youcii.mvplearn.model.IpQueryResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by jdw on 2018/3/28.
 *
 * Retrofit网络请求工厂单例, 作用:
 *  1. 添加共同header;
 *  2. 设置超时时间;
 *  3. 隔离了Retrofit, 隔离了 RxJava
 */
object RetrofitFactory {

    private val interceptor = Interceptor {
        val request: Request = it.request()
                .newBuilder()
                .addHeader("Authorization", "APPCODE e791ada94bd74182aaab249e51128ad3")
                .build()
        return@Interceptor it.proceed(request)
    }
    private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(ConnectConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(ConnectConfig.READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(ConnectConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    private val retrofitService: RetrofitService = Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RetrofitService::class.java)

    /**
     * 查询ip的网络请求: RxJava
     */
    fun getRxIpInfo(ip: String): Observable<IpQueryResponse> {
        return retrofitService.getRxIpInfo(ip)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()) // 有些 Observable 会依赖一些资源，当该 Observable 完成后释放这些资源。
                .observeOn(AndroidSchedulers.mainThread())
    }

}