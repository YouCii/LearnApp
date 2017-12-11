package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.JsonSyntaxException
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.okgo.JsonConverter
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.model.IpQueryResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_ip_query.*

class IPQueryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ip_query)

        btnQuery.setOnClickListener({ view ->
            if (view.id == R.id.btnQuery) {
                startQuery()
            }
        })

        etIp.setSelection(etIp.text.length)
    }

    private fun startQuery() {
        val observable = OkGo.get<IpQueryResponse>("http://iploc.market.alicloudapi.com/v3/ip")
                .headers("Authorization", "APPCODE e791ada94bd74182aaab249e51128ad3")
                .params("ip", etIp.text.toString())
                .converter(JsonConverter(IpQueryResponse::class.java))
                .adapt(ObservableBody<IpQueryResponse>())
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // ASM means Single Abstract Method
                .subscribe({ ipQueryResponse ->
                    tvResult.text = ipQueryResponse.toString()
                }, { throwable: Throwable? ->
                    tvResult.text = if (throwable is JsonSyntaxException) "无数据" else throwable.toString()
                })
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, IPQueryActivity::class.java))
        }
    }
}
