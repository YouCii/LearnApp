package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.widget.DownLoadButton
import kotlinx.android.synthetic.main.activity_diy_view.*
import java.lang.ref.WeakReference

class DiyViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diy_view)

        subStateView.setOnClickListener {
            subStateView.setState((subStateView.currentState + 1) % 4)
        }

        downloadButton.setState(DownLoadButton.STATE_NO_DOWNLOAD)
        downloadButton.setOnDownLoadButtonClickListener(object : DownLoadButton.OnDownLoadButtonClickListener {
            override fun onClick(view: View, currentState: Int) {
                when (currentState) {
                    DownLoadButton.STATE_NO_DOWNLOAD -> {
                        downloadButton.setState(DownLoadButton.STATE_DOWNLOADING)
                        DownLoadHandler(downloadButton).sendEmptyMessage(0)
                    }
                    DownLoadButton.STATE_COMPLETE -> downloadButton.setState(DownLoadButton.STATE_USED)
                    DownLoadButton.STATE_USED -> downloadButton.setState(DownLoadButton.STATE_NO_DOWNLOAD)
                }
            }
        })
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, DiyViewActivity::class.java))
        }

        private class DownLoadHandler(downLoadButton: DownLoadButton) : Handler() {
            private val weak = WeakReference(downLoadButton)

            private var downLoadedPercent = 0
            override fun handleMessage(msg: Message) {
                downLoadedPercent += 2
                if (downLoadedPercent >= 100) {
                    weak.get()?.setState(DownLoadButton.STATE_COMPLETE)
                } else {
                    weak.get()?.setDownLoadProgress(downLoadedPercent)
                    sendEmptyMessageDelayed(0, 50)
                }
            }
        }
    }

}
