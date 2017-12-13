package com.youcii.mvplearn.utils

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by jdw on 2017/12/13.
 *
 * 用于生成各个网络请求
 */
interface RetrofitFactory {

    /**
     * 获取ip的定位信息
     */
    @GET("/v3/ip")
    fun getIpInfo(@Query("id") id: String, @Header("Authorization") header: String): Call<ResponseBody>

}