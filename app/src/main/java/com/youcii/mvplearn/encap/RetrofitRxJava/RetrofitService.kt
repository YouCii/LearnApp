package com.youcii.mvplearn.encap.RetrofitRxJava

import com.youcii.mvplearn.response.IpQueryResponse
import com.youcii.mvplearn.response.TopMovieResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by jdw on 2017/12/13.
 *
 * 用于生成各个网络请求
 */
interface RetrofitService {

    /**
     * 获取ip的定位信息
     */
    @GET("/v3/ip")
    fun getCallIpInfo(@Query("id") ip: String): Call<ResponseBody>

    /**
     * 获取ip的定位信息
     */
    @GET("/v3/ip")
    fun getRxIpInfo(@Query("id") ip: String): Observable<IpQueryResponse>

    /**
     * 获取豆瓣Top250电影条目
     * 用headers作为拦截器内切换base_url的判断依据
     */
    @HTTP(method = "GET", path = "/v2/movie/top250?start=0", hasBody = false)
    @Headers("BASE_URL: DOU_BAN")
    fun getTOP250(@Query("count") count: Int): Observable<TopMovieResponse>

}