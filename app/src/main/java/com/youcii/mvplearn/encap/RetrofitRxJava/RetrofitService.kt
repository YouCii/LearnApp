package com.youcii.mvplearn.encap.RetrofitRxJava

import com.youcii.mvplearn.model.IpQueryResponse
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
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

}