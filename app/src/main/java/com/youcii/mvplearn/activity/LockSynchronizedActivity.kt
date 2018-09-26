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
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * Created by jdw on 2018/5/28.
 *
附 公平锁和非公平锁的实现:

1. 简化版的步骤：（非公平锁的核心）
基于CAS尝试将state（锁数量）从0设置为1
A、如果设置成功，设置当前线程为独占锁的线程；
B、如果设置失败，还会再
获取一次锁数量，
B1、如果锁数量为0，再
基于CAS尝试将state（锁数量）从0设置为1一次，如果设置成功，设置当前线程为独占锁的线程；
B2、如果锁数量不为0或者上边的尝试又失败了，
查看当前线程是不是已经是独占锁的线程了，如果是，则将当前的锁数量+1；如果不是，则将该线程封装在一个Node内，并加入到等待队列中去。等待被其前一个线程节点唤醒。

2. 简化版的步骤：（公平锁的核心）
获取一次锁数量，
B1、如果锁数量为0，如果当前线程是等待队列中的头节点，
基于CAS尝试将state（锁数量）从0设置为1一次，如果设置成功，设置当前线程为独占锁的线程；
B2、如果锁数量不为0或者当前线程不是等待队列中的头节点或者上边的尝试又失败了，
查看当前线程是不是已经是独占锁的线程了，如果是，则将当前的锁数量+1；如果不是，则将该线程封装在一个Node内，并加入到等待队列中去。等待被其前一个线程节点唤醒。

 */
class LockSynchronizedActivity : BaseActivity() {

    /**
     * 执行完毕后才能再次点击
     */
    private val threadList = ArrayList<Thread>(5)

    private enum class ObservableKind {
        Synchronized,
        WaitNotify,
        ReentrantLock,
        ReadWrite
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
        val readWriteObservable = RxView.clicks(btn_read_write)
                .map { ReadWrite }

        Observable.merge(synchronizedObservable, waitNotifyObservable, reentrantLockObservable, readWriteObservable)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .filter({
                    threadList.isEmpty()
                })
                .subscribe({
                    var thread: Thread
                    when (it) {
                        ObservableKind.Synchronized -> {
                            tv_log.append("\n\nSynchronized")
                            // 不是公平锁, 可能不是先到先得, 但是因为目前逻辑太简单, 所以测试的一般都是123
                            for (i in 0 until THREAD_NUM) {
                                myHandler.postDelayed({
                                    thread = Thread(runnableSynchronized, "Synchronized$i")
                                    threadList.add(thread)
                                    thread.start()
                                }, 20L * i)
                            }
                        }
                        ObservableKind.ReentrantLock -> {
                            tv_log.append("\n\nReentrant")
                            // 公平锁, 永远先到先得
                            for (i in 0 until THREAD_NUM) {
                                myHandler.postDelayed({
                                    thread = Thread(runnableLock, "ReentrantLock$i")
                                    threadList.add(thread)
                                    thread.start()
                                }, 20L * i)
                            }
                            myHandler.postDelayed({
                                thread = Thread(runnableTryLock, "TryLock")
                                threadList.add(thread)
                                thread.start()
                            }, 30L)
                        }
                        ObservableKind.WaitNotify -> {
                            tv_log.append("\n\nWaitNotifySynchronized")
                            for (i in 0 until THREAD_NUM) {
                                thread = Thread(runnableWait, "Wait$i")
                                threadList.add(thread)
                                thread.start()
                            }
                            thread = Thread(runnableNotify, "Notify")
                            threadList.add(thread)
                            thread.start()
                        }
                        ObservableKind.ReadWrite -> {
                            tv_log.append("\n\nReentrantReadWriteLock")
                            thread = Thread(writeRunnable, "Write1")
                            threadList.add(thread)
                            thread.start()
                            for (i in 0 until THREAD_NUM) {
                                thread = Thread(readRunnable, "Read$i")
                                threadList.add(thread)
                                thread.start()
                            }
                            thread = Thread(writeRunnable, "Write2")
                            threadList.add(thread)
                            thread.start()
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
    private val runnableTryLock = Runnable {
        sendMessage(Thread.currentThread().name + "尝试获取")
        if (lock.tryLock(20, TimeUnit.MILLISECONDS)) {
            try {
                sendMessage(Thread.currentThread().name + "获得锁")
                Thread.sleep(THREAD_DELAY)
            } finally {
                sendMessage(Thread.currentThread().name + "释放锁")
                lock.unlock()
            }
        } else {
            sendMessage(Thread.currentThread().name + "未能获取到")
        }
        threadList.remove(Thread.currentThread())
    }

    /**
     * 读写锁 ReadWriteLock
     */
    private val readWriteLock = ReentrantReadWriteLock(false)
    private val readRunnable = Runnable {
        sendMessage(Thread.currentThread().name + "等待读锁")
        readWriteLock.readLock().lock()
        try {
            sendMessage(Thread.currentThread().name + "获得读锁")
            Thread.sleep(THREAD_DELAY)
        } finally {
            sendMessage(Thread.currentThread().name + "释放读锁")
            readWriteLock.readLock().unlock()
        }
        threadList.remove(Thread.currentThread())
    }
    private val writeRunnable = Runnable {
        sendMessage(Thread.currentThread().name + "等待写锁")
        readWriteLock.writeLock().lock()
        try {
            sendMessage(Thread.currentThread().name + "获得写锁")
            Thread.sleep(THREAD_DELAY)
        } finally {
            sendMessage(Thread.currentThread().name + "释放写锁")
            readWriteLock.writeLock().unlock()
        }
        threadList.remove(Thread.currentThread())
    }

    private val myHandler = MyHandler(this)
    private fun sendMessage(obj: String) {
        val message = myHandler.obtainMessage()
        message.obj = obj
        message.sendToTarget()
    }

    override fun onDestroy() {
        super.onDestroy()
        myHandler.removeCallbacksAndMessages(0)
    }

    companion object {
        /**
         * 线程内的模拟耗时延迟, 要保证>1000, 否则标识等待的省略号不会显示了
         */
        private const val THREAD_DELAY = 2000L
        /**
         * 等待的线程数量, 要保证>2, 否则看不出锁是否公平
         */
        private const val THREAD_NUM = 3

        private class MyHandler constructor(lockSynchronizedActivity: LockSynchronizedActivity) : Handler() {
            var weakReference: WeakReference<LockSynchronizedActivity> = WeakReference(lockSynchronizedActivity)
            /**
             * 距离上次打印超过1s时, 添加省略号以便查看
             */
            var lastLogTime: Long = 0

            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
                var logString = ""
                if (lastLogTime != 0L && System.currentTimeMillis() - lastLogTime > 1000) {
                    logString += "..."
                }
                logString += ("\n" + msg?.obj as String)

                System.out.print(logString)
                weakReference.get()?.tv_log?.append(logString)
                lastLogTime = System.currentTimeMillis()

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