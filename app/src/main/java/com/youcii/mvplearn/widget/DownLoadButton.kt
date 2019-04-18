package com.youcii.mvplearn.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.Button
import com.youcii.mvplearn.R

/**
 * Created by jdw on 2018/11/2.
 *
 * 状态转换按钮, 带有边框填充效果的下载进度
 */
class DownLoadButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : Button(context, attrs, defStyleAttr) {

    private val paint: Paint = Paint()

    /**
     * 当前状态
     */
    private var currentState = 0
    /**
     * 当前下载进度
     * 百分比
     */
    private var currentPercent = 0

    private var onDownLoadButtonClickListener: OnDownLoadButtonClickListener? = null

    init {
        paint.isAntiAlias = true // 抗锯齿
        paint.isDither = true // 抗抖动

        gravity = Gravity.CENTER
        currentState = STATE_NO_DOWNLOAD

        setOnClickListener { v -> onDownLoadButtonClickListener?.onClick(v, currentState) }
    }

    /**
     * 设置当前状态
     */
    fun setState(state: Int) {
        this.currentState = state
        postInvalidate()
    }

    /**
     * 设置下载进度
     */
    fun setDownLoadProgress(percent: Int) {
        this.currentPercent = percent
        postInvalidate()
    }

    private var solidDrawable: Drawable? = null

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (currentState) {
            STATE_NO_DOWNLOAD -> {
                currentPercent = 0

                text = "下载"
                setTextColor(Color.parseColor("#D8413A"))
                setBackgroundResource(R.drawable.bg_download_button_download)
            }
            STATE_DOWNLOADING -> {
                if (text != "下载中") {
                    text = "下载中"
                    setTextColor(Color.parseColor("#000000"))

                    // 动态填充的Drawable
                    solidDrawable = resources.getDrawable(R.drawable.bg_download_button_clip)
                    // 原background是显示的边框, 合并后显示
                    background = LayerDrawable(arrayOf(background, solidDrawable))
                }
                // 利用ClipDrawable的裁剪效果实现进度展示
                solidDrawable?.level = currentPercent * 100
            }
            STATE_COMPLETE -> {
                text = "立即使用"
                setTextColor(Color.parseColor("#FFFFFF"))
                setBackgroundResource(R.drawable.bg_download_button_to_use)
            }
            STATE_USED -> {
                text = "使用中"
                setTextColor(Color.parseColor("#000000"))
                setBackgroundResource(R.drawable.bg_download_button_using)
            }
        }
    }


    fun setOnDownLoadButtonClickListener(onDownLoadButtonClickListener: OnDownLoadButtonClickListener) {
        this.onDownLoadButtonClickListener = onDownLoadButtonClickListener
    }

    interface OnDownLoadButtonClickListener {
        fun onClick(view: View, currentState: Int)
    }

    companion object {
        /**
         * 未下载
         */
        const val STATE_NO_DOWNLOAD = 0
        /**
         * 下载中
         */
        const val STATE_DOWNLOADING = 1
        /**
         * 下载完成
         */
        const val STATE_COMPLETE = 2
        /**
         * 已使用
         */
        const val STATE_USED = 3
    }

}