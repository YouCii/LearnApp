package com.youcii.mvplearn.utils

import android.os.Environment
import com.jakewharton.disklrucache.DiskLruCache
import com.youcii.mvplearn.app.App
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.InputStream
import java.security.MessageDigest

/**
 * Created by jdw on 2019/1/28.
 */
object DiskLruCacheUtils {
    private const val MAX_SIZE = 1024 * 1024 * 100L
    private val diskLruCache = DiskLruCache.open(getSaveDir(), 1, 1, MAX_SIZE)

    fun save(saveName: String, inputStream: InputStream) {
        var editor: DiskLruCache.Editor? = null
        var bufferIn: BufferedInputStream? = null
        var bufferOut: BufferedOutputStream? = null
        Observable.just(getMD5(saveName))
                .observeOn(Schedulers.io())
                .map {
                    synchronized(it) {
                        editor = diskLruCache.edit(it)
                        bufferIn = BufferedInputStream(inputStream)
                        bufferOut = BufferedOutputStream(editor?.newOutputStream(0))
                        var b = 0
                        while (b != -1) {
                            b = bufferIn?.read() ?: -1
                            if (b != -1) {
                                bufferOut?.write(b)
                            } else {
                                break
                            }
                        }
                        editor?.commit()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : DisposableObserver<Unit>() {
                    override fun onNext(t: Unit) {
                    }

                    override fun onError(e: Throwable) {
                        editor?.abort()
                        ToastUtils.showShortToast(e.toString())
                    }

                    override fun onComplete() {
                        bufferIn?.close()
                        bufferOut?.close()
                        diskLruCache.flush()
                    }
                })
    }

    private fun getSaveDir(): File {
        return App.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    }

    private fun getMD5(oldString: String): String {
        val messageDigest = MessageDigest.getInstance("MD5")
        messageDigest.update(oldString.toByteArray())
        return messageDigest.digest().toString()
    }

}