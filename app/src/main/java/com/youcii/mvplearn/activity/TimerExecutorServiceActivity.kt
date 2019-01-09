package com.youcii.mvplearn.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.utils.ThreadPool
import kotlinx.android.synthetic.main.activity_timer_executor_service.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by jdw on 2018/11/26.
 */
class TimerExecutorServiceActivity : BaseActivity() {

    private val dateFormat = SimpleDateFormat("mm:ss", Locale.CHINA)

    private val timer = Timer()
    private val scheduledExecutorService = ScheduledThreadPoolExecutor(1, ThreadPool.threadFactory)

    private lateinit var handler: MyHandler

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer_executor_service)

        tvTimer.movementMethod = ScrollingMovementMethod.getInstance()
        tvExecutor.movementMethod = ScrollingMovementMethod.getInstance()

        RxView.clicks(btnStartTimer)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe { startTimer() }
        RxView.clicks(btnStartExecutor)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe { startExecutor() }

        handler = MyHandler(tvTimer, tvExecutor)
    }

    private fun startTimer() {
        // TimerTask 不能复用
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.sendMessage(handler.obtainMessage(0, 0, 0, "\n" + dateFormat.format(Date())))
            }
        }, 1000, 1000)
    }

    private fun startExecutor() {
        // runnable 可以复用
        scheduledExecutorService.scheduleAtFixedRate(executorTask, 1000, 1000, TimeUnit.MILLISECONDS)
    }

    private val executorTask = Runnable {
        handler.sendMessage(handler.obtainMessage(1, 0, 0, "\n" + dateFormat.format(Date())))
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
        timer.purge()

        scheduledExecutorService.shutdown()
        scheduledExecutorService.purge()
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, TimerExecutorServiceActivity::class.java))
        }

        /**
         * 使用Handler后没有出现TextView.append时的越界错误, 怀疑是事件并发处理导致
         */
        private class MyHandler(tvTimer: TextView, tvExecutor: TextView) : Handler() {
            private val timerReference = WeakReference<TextView>(tvTimer)
            private val executorReference = WeakReference<TextView>(tvExecutor)

            override fun handleMessage(msg: Message?) {
                if (msg?.what == 0) {
                    timerReference.get()!!.append((msg.obj ?: "") as String)
                } else if (msg?.what == 1) {
                    executorReference.get()!!.append((msg.obj ?: "") as String)
                }
            }
        }
    }
}