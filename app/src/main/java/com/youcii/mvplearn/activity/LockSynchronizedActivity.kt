package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.method.ScrollingMovementMethod
import com.jakewharton.rxbinding2.view.RxView
import com.youcii.mvplearn.R
import com.youcii.mvplearn.activity.LockSynchronizedActivity.ObservableKind.*
import com.youcii.mvplearn.base.BaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_lock_synchronized.*
import java.lang.ref.WeakReference
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

/**
 * Created by jdw on 2018/5/28.
 */
class LockSynchronizedActivity : BaseActivity() {

    /**
     * 执行完毕后才能再次点击
     */
    private val threadList = ArrayList<Thread>(5)

    private enum class ObservableKind {
        Synchronized,
        WaitNotify,
        ReentrantLock
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock_synchronized)

        tv_log.movementMethod = ScrollingMovementMethod.getInstance()

        val synchronizedObservable = RxView.clicks(btn_synchronized)
                .map { Synchronized }
        val reentrantLockObservable = RxView.clicks(btn_reentrant)
                .map { ReentrantLock }
        val waitNotifyObservable = RxView.clicks(btn_wait_notify)
                .map { WaitNotify }

        Observable.merge(synchronizedObservable, waitNotifyObservable, reentrantLockObservable)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter({
                    threadList.isEmpty()
                })
                .subscribe({
                    var thread: Thread
                    when (it) {
                        ObservableKind.Synchronized -> {
                            tv_log.append("\nSynchronized\n")
                            // 不是公平锁, 可能不是先到先得, 但是因为目前逻辑太简单, 所以测试的一般都是123
                            for (i in 1..THREAD_NUM) {
                                myHandler.postDelayed({
                                    thread = Thread(runnableSynchronized, "Synchronized$i")
                                    thread.start()
                                    threadList.add(thread)
                                }, 20L * i)
                            }
                        }
                        ObservableKind.ReentrantLock -> {
                            tv_log.append("\nReentrant\n")
                            // 公平锁, 永远先到先得
                            for (i in 1..THREAD_NUM) {
                                myHandler.postDelayed({
                                    thread = Thread(runnableLock, "ReentrantLock$i")
                                    thread.start()
                                    threadList.add(thread)
                                }, 20L * i)
                            }
                        }
                        ObservableKind.WaitNotify -> {
                            tv_log.append("\nWaitNotifySynchronized\n")
                            for (i in 1..THREAD_NUM) {
                                myHandler.postDelayed({
                                    thread = Thread(runnableWait, "Wait$i")
                                    threadList.add(thread)
                                    thread.start()
                                }, 20L * i)
                            }
                            myHandler.postDelayed({
                                thread = Thread(runnableNotify, "Notify")
                                thread.start()
                                threadList.add(thread)
                            }, 20L * THREAD_NUM + 20)
                        }
                        null -> {
                        }
                    }
                })
    }

    /**
     * 普通的Synchronized, 非公平重入锁
     */
    private val runnableSynchronized = Runnable {
        sendMessage(Thread.currentThread().name + "等待锁")
        synchronized(this@LockSynchronizedActivity) {
            sendMessage(Thread.currentThread().name + "获得锁")
            Thread.sleep(THREAD_DELAY)
            sendMessage(Thread.currentThread().name + "释放锁")
        }
        threadList.remove(Thread.currentThread())
    }

    /**
     * 使用wait/notify的Synchronized, 主要是用于生产者/消费者模式
     */
    private val runnableWait = Runnable {
        sendMessage(Thread.currentThread().name + "等待锁")
        synchronized(this@LockSynchronizedActivity) {
            sendMessage(Thread.currentThread().name + "获得锁,并wait")
            (this@LockSynchronizedActivity as Object).wait()
            sendMessage(Thread.currentThread().name + "释放锁,wait结束")
        }
        threadList.remove(Thread.currentThread())
    }
    private val runnableNotify = Runnable {
        Thread.sleep(THREAD_NUM * THREAD_DELAY)
        sendMessage(Thread.currentThread().name + "等待锁")
        synchronized(this@LockSynchronizedActivity) {
            sendMessage(Thread.currentThread().name + "获得锁,并notifyAll")
            (this@LockSynchronizedActivity as Object).notifyAll()
        }
        threadList.remove(Thread.currentThread())
    }

    /**
     * 公平ReentrantLock
     */
    private val lock: Lock = ReentrantLock(true)
    private val runnableLock = Runnable {
        sendMessage(Thread.currentThread().name + "等待锁")
        lock.lock()
        try {
            sendMessage(Thread.currentThread().name + "获得锁")
            Thread.sleep(THREAD_DELAY)
        } finally {
            sendMessage(Thread.currentThread().name + "释放锁")
            lock.unlock()
        }
        threadList.remove(Thread.currentThread())
    }

    private val myHandler = MyHandler(this)
    private fun sendMessage(obj: String) {
        System.out.print(obj + "\n")
        val message = myHandler.obtainMessage()
        message.obj = obj
        message.sendToTarget()
    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler.removeCallbacksAndMessages(0)
    }

    companion object {
        private const val THREAD_DELAY = 2000L
        private const val THREAD_NUM = 3

        private class MyHandler constructor(lockSynchronizedActivity: LockSynchronizedActivity) : Handler() {
            var weakReference: WeakReference<LockSynchronizedActivity> = WeakReference(lockSynchronizedActivity)

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                weakReference.get()?.tv_log?.append(msg?.obj as String + "\n")

                // TextView自动滚动到底部
                val offset = (weakReference.get()?.tv_log?.lineCount ?: 0) * (weakReference.get()?.tv_log?.lineHeight ?: 0)
                if (offset > (weakReference.get()?.tv_log?.height ?: 0)) {
                    weakReference.get()?.tv_log?.scrollTo(0, offset - (weakReference.get()?.tv_log?.height ?: 0))
                }
            }
        }

        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LockSynchronizedActivity::class.java))
        }
    }
}