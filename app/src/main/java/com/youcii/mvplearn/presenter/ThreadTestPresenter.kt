package com.youcii.mvplearn.presenter

import com.youcii.mvplearn.activity.interfaces.IThreadTestView
import com.youcii.mvplearn.base.BasePresenter

class ThreadTestPresenter(private val iThreadTestView: IThreadTestView) : BasePresenter<IThreadTestView>(iThreadTestView) {

    fun startTestThread() {
        val thread1 = Thread { run() }
        val thread2 = Thread { run() }
        val thread3 = Thread { run() }
        val thread4 = Thread { run() }
        val thread5 = Thread {
            synchronized(this) {
                iThreadTestView.showText("Hello")
                iThreadTestView.showText("World")
                (this as Object).notifyAll()
            }
        }
        thread1.start()
        thread2.start()
        thread3.start()
        thread4.start()
        thread5.start()
    }

    @Synchronized
    fun run() {
        iThreadTestView.showText("Hello")
        (this as Object).wait()
        iThreadTestView.showText("World")
    }

    override fun detach() {
    }
}