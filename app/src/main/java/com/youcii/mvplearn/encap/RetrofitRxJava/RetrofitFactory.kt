package com.youcii.mvplearn.encap.RetrofitRxJava

import android.text.TextUtils
import com.youcii.mvplearn.constant.ConnectConfig
import com.youcii.mvplearn.constant.UrlConstant
import com.youcii.mvplearn.response.IpQueryResponse
import com.youcii.mvplearn.response.TopMovieResponse
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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

    private const val authorHeader = "Authorization"

    private val headerInterceptor = Interceptor {
        val request: Request = it.request()
                .newBuilder()
                .addHeader(authorHeader, "APPCODE e791ada94bd74182aaab249e51128ad3")
                .build()
        return@Interceptor it.proceed(request)
    }
    private val baseUrlInterceptor = Interceptor {
        val request: Request = it.request()
        val baseUrlKey = request.header(UrlConstant.BASE_URL_KEY)
        if (!TextUtils.isEmpty(baseUrlKey)) {
            val builder: Request.Builder = request.newBuilder()
            builder.removeHeader(UrlConstant.BASE_URL_KEY)

            if (baseUrlKey == "DOU_BAN") {
                builder.removeHeader(authorHeader)
                val oldFullUrl: HttpUrl = request.url()
                val newFullUrl: String = oldFullUrl.toString().replace(UrlConstant.BASE_URL_ALI, UrlConstant.BASE_URL_DOU_BAN)
                return@Interceptor it.proceed(builder.url(newFullUrl).build())
            }
        }
        return@Interceptor it.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(ConnectConfig.CONNECT_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(ConnectConfig.READ_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(ConnectConfig.WRITE_TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(headerInterceptor)
            .addInterceptor(baseUrlInterceptor)
            .build()
    private val retrofitService: RetrofitService = Retrofit.Builder()
            .baseUrl(UrlConstant.BASE_URL_ALI)
            .client(okHttpClient)
            .addConverterFactory(CustomResponseConverterFactory.create())
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
    }

    /**
     * 豆瓣Top250
     */
    fun getTopMovie(count: Int): Observable<TopMovieResponse> {
        return retrofitService.getTOP250(count)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io()) // 有些 Observable 会依赖一些资源，当该 Observable 完成后释放这些资源。
    }

}