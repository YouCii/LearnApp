package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.JsonSyntaxException
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.constant.UrlConstant
import com.youcii.mvplearn.encap.RetrofitRxJava.BaseObserver
import com.youcii.mvplearn.encap.RetrofitRxJava.RetrofitFactory
import com.youcii.mvplearn.encap.RetrofitRxJava.RetrofitService
import com.youcii.mvplearn.model.IpQueryResponse
import com.youcii.mvplearn.utils.GsonUtils
import kotlinx.android.synthetic.main.activity_ip_query.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class IPQueryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_query)

        btnQuery.setOnClickListener({ view ->
            if (view.id == R.id.btnQuery) {
                startRxRetrofit()
            }
        })

        etIp.setSelection(etIp.text.length)
    }

    /**
     * 比较简单的类, 不用单独抽出, 过度设计只是浪费时间, 后续再适时重构就行
     */
    private fun startCallBackRetrofitQuery() {
        val retrofit = Retrofit.Builder()
                .baseUrl(UrlConstant.BASE_URL)
                .build()
        val call = retrofit.create(RetrofitService::class.java)
                .getCallIpInfo(etIp.text.toString())
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                tvResult.text = GsonUtils.json2Bean(response.body()?.string(), IpQueryResponse::class.java).toString()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                tvResult.text = if (t is JsonSyntaxException) "无数据" else t.toString()
            }
        })
    }

    private fun startRxRetrofit() {
        RetrofitFactory.getRxIpInfo(etIp.text.toString())
                .subscribe(
                        object : BaseObserver<IpQueryResponse>() {
                            override fun onNext(t: IpQueryResponse) {
                                super.onNext(t)
                                tvResult.text = t.toString()
                            }

                            override fun onError(e: Throwable) {
                                super.onError(e)
                                tvResult.text = if (e is JsonSyntaxException) "无数据" else e.toString()
                            }
                        }
                )
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, IPQueryActivity::class.java))
        }
    }
}
