package com.youcii.mvplearn.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.TransformerPagerAdapter
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.utils.ResourcesUtils
import kotlinx.android.synthetic.main.activity_transformer_viewpager.*

/**
 * Created by jdw on 2018/11/1.
 *
 * 带有露边缩放效果的ViewPager
 */
class TransformerViewPagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformer_viewpager)

        val imgList = arrayListOf(
                ResourcesUtils.getDrawable(this, R.drawable.pager_1),
                ResourcesUtils.getDrawable(this, R.drawable.pager_2),
                ResourcesUtils.getDrawable(this, R.drawable.pager_3)
        )
        vp_zoom.adapter = TransformerPagerAdapter(this, imgList)
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Activity) {
            context.startActivity(Intent(context, TransformerViewPagerActivity::class.java))
        }
    }
}