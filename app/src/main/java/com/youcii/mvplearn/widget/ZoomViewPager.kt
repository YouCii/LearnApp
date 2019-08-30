package com.youcii.mvplearn.widget

import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.View
import kotlin.math.abs

/**
 * Created by jdw on 2018/11/2.
 *
 * 左右显示缩小page的ViewPager
 *
 * 使用时请注意:
 * 1. 请给父布局设置 android:clipChildren="false"
 * 2. viewpager宽度留边
 */
class ZoomViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

    init {
        setPageTransformer(true, CustomPagerTransformer())

        offscreenPageLimit = 3
        currentItem = 0
    }

    /**
     * 用于处理ViewPager的缩放效果
     */
    private class CustomPagerTransformer : PageTransformer {
        private var viewPager: ViewPager? = null

        override fun transformPage(view: View, position: Float) {
            if (viewPager == null) {
                viewPager = view.parent as ViewPager
            }

            // 当前page的左坐标
            val leftInScreen = view.left - viewPager!!.scrollX
            // 当前page的中间位置
            val centerXInViewPager = leftInScreen + view.measuredWidth / 2
            // 当前page与viewPager的相对距离(可能是正的, 也可能是负的)
            val offsetX = centerXInViewPager - viewPager!!.measuredWidth / 2

            // 缩放比例
            val offsetRate = offsetX.toFloat() * 0.15f / viewPager!!.measuredWidth
            val scaleFactor = 1 - abs(offsetRate)
            if (scaleFactor > 0) {
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                // 间距
                view.translationX = 60 * offsetRate
            }
        }
    }
}

