package com.youcii.mvplearn.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.youcii.mvplearn.R
import com.youcii.mvplearn.adapter.TransformerPager2Adapter
import com.youcii.mvplearn.base.BaseActivity
import kotlinx.android.synthetic.main.activity_transformer_pager2.*
import kotlinx.android.synthetic.main.item_transformer_pager2.view.*
import java.util.ArrayList
import kotlin.math.abs

/**
 * Created by jdw on 2019/09/09.
 */
class TransformerPager2Activity : BaseActivity() {

    private val imageList = ArrayList<String>().apply {
        repeat(3) {
            add("http://5b0988e595225.cdn.sohucs.com/images/20180203/bb9208badf9e4fdd9a46e7a1243f9c46.jpeg")
            add("http://img5.imgtn.bdimg.com/it/u=2198448186,2593700097&fm=26&gp=0.jpg")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformer_pager2)

        viewPager.offscreenPageLimit = 2
        viewPager.adapter = TransformerPager2Adapter(this, imageList)

        viewPager.setPageTransformer { view, _ ->
            // 当前page的上坐标
            val topInScreen = view.top - viewPager!!.scrollY
            // 当前page的中间位置
            val centerYInViewPager = topInScreen + view.measuredHeight / 2
            // 当前page与viewPager的相对距离(可能是正的, 也可能是负的)
            val offsetY = centerYInViewPager - viewPager!!.measuredHeight / 2

            val offsetRate = offsetY.toFloat() * 0.15f / viewPager!!.measuredHeight
            val scaleFactor = 1 - abs(offsetRate)
            if (scaleFactor > 0) {
                // 缩放比例
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                // 平移距离
                view.translationY = (-0.5 * offsetY).toFloat()
                view.translationZ = (1 - abs(offsetY)).toFloat()
            }
            // 蒙版
            view.ivMask.visibility = if (scaleFactor < 0.9) View.VISIBLE else View.GONE
        }
        viewPager.currentItem = 2
    }

    companion object {
        @JvmStatic
        fun startActivity(context: Activity) {
            context.startActivity(Intent(context, TransformerPager2Activity::class.java))
        }
    }
}