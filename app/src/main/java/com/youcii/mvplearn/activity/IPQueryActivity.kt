package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lzy.okgo.OkGo
import com.lzy.okrx2.adapter.ObservableBody
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.okgo.JsonConvert
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
    }

    private fun startQuery() {
        val observable = OkGo.post<IpQueryResponse>("http://iploc.market.alicloudapi.com/v3/ip")
                .headers("Authorization", "e791ada94bd74182aaab249e51128ad3")
                .params("ip", etIp.text.toString())
                // 强制把params拼在url后面, 哪怕是post
                .isSpliceUrl(true)
                .converter(JsonConvert<IpQueryResponse>())
                .adapt(ObservableBody<IpQueryResponse>())
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                // ASM means Single Abstract Method
                .subscribe({ ipQueryResponse ->
                    tvResult.text = ipQueryResponse.toString()
                }, { throwable: Throwable? ->
                    tvResult.text = throwable.toString()
                })
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, IPQueryActivity::class.java))
        }
    }
}
