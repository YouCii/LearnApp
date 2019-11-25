package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.presenter.HookUtils

/**
 * Created by jingdongwei on 2019/11/07.
 *
 * hook:
 * 替换系统成员变量为自定义变量或者一个代理变量
 */
class HookActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HookUtils.hook(this)
        setContentView(R.layout.activity_hook)
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, HookActivity::class.java))
        }
    }

}