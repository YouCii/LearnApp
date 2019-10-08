package com.youcii.mvplearn.presenter

import com.youcii.mvplearn.activity.interfaces.IThreadTestView
import com.youcii.mvplearn.base.BasePresenter

class ThreadTestPresenter(iThreadTestView: IThreadTestView) : BasePresenter<IThreadTestView>(iThreadTestView) {

    fun startTestThread() {
        val thread1 = Thread { run() }
        val thread2 = Thread { run() }
        val thread3 = Thread { run() }
        val thread4 = Thread { run() }
        val thread5 = Thread {
            synchronized(this) {
                getView()?.showText("Hello")
                getView()?.showText("World")
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
    private fun run() {
        getView()?.showText("Hello")
        (this as Object).wait()
        getView()?.showText("World")
    }

    override fun detach() {
    }
}