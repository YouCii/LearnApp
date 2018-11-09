package com.youcii.mvplearn.utils

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes

/**
 * Created by jdw on 2018/11/8.
 *
 * 简单封装color/drawable等资源的获取
 */
class ResourcesUtils {
    companion object {

        @Throws(NotFoundException::class)
        fun getColor(context: Context, @ColorRes color: Int): Int {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                context.resources.getColor(color, null)
            } else {
                context.resources.getColor(color)
            }
        }

        @Throws(NotFoundException::class)
        fun getDrawable(context: Context, @DrawableRes drawable: Int): Drawable {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.resources.getDrawable(drawable, null)
            } else {
                context.resources.getDrawable(drawable)
            }
        }

    }
}