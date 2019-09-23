package com.youcii.mvplearn.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.TransformerPager1Adapter
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_transformer_pager1.*

/**
 * Created by jdw on 2018/11/1.
 *
 * 带有露边缩放效果的ViewPager
 */
class TransformerPager1Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformer_pager1)

        val imgList = arrayListOf(
                ResourcesUtils.getDrawable(this, R.drawable.pager_1),
                ResourcesUtils.getDrawable(this, R.drawable.pager_2),
                ResourcesUtils.getDrawable(this, R.drawable.pager_3)
        )
        vp_zoom.adapter = TransformerPager1Adapter(this, imgList)
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Activity) {
            context.startActivity(Intent(context, TransformerPager1Activity::class.java))
        }
    }
}