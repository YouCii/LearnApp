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

/**
 * Created by jdw on 2019/1/28.
 */
class DiskLruCacheUtils {
    private val diskLruCache = DiskLruCache.open(getSaveDir(), 1, 1, MAX_SIZE)

    fun save(saveName: String, inputStream: InputStream) {
        var editor: DiskLruCache.Editor? = null
        var bufferIn: BufferedInputStream? = null
        var bufferOut: BufferedOutputStream? = null
        Observable.just(SafeKeyGenerator.getSafeKey(saveName))
                .observeOn(Schedulers.io())
                .map {
                    synchronized(it) {
                        editor = diskLruCache!!.edit(it)
                        bufferIn = BufferedInputStream(inputStream)
                        bufferOut = BufferedOutputStream(editor!!.newOutputStream(0))
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
                        onComplete()
                    }

                    override fun onComplete() {
                        bufferIn?.close()
                        bufferOut?.close()
                    }
                })
    }

    fun get(name: String): InputStream? {
        return diskLruCache.get(SafeKeyGenerator.getSafeKey(name))?.getInputStream(0)
    }

    fun release() {
        // flush方法用于刷新日志文件, 建议在onPause中调用
        diskLruCache?.flush()
        diskLruCache?.close()
    }

    companion object {
        private const val MAX_SIZE = 1024 * 1024 * 100L

        private fun getSaveDir(): File {
            return App.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        }
    }

}