package com.youcii.mvplearn.utils

import java.security.MessageDigest

/**
 * 根据Glide内部源码重建的编码类, 返回某String的唯一校验码
 *
 * Created by jdw on 2019/2/19.
 */
object SafeKeyGenerator {
    private val SHA_256_CHARS = CharArray(64)
    private val HEX_CHAR_ARRAY = "0123456789abcdef".toCharArray()

    /**
     * 用于缓存
     */
    private val cacheMap = LinkedHashMap<String, String>()

    fun getSafeKey(oldString: String): String {
        synchronized(cacheMap) {
            if (cacheMap[oldString] != null) {
                return cacheMap[oldString]!!
            }
        }

        val messageDigest = MessageDigest.getInstance("SHA-256")
        messageDigest.update(oldString.toByteArray())
        val newString = sha256BytesToHex(messageDigest.digest())

        synchronized(cacheMap) {
            cacheMap[oldString] = newString
        }
        return newString
    }

    private fun sha256BytesToHex(bytes: ByteArray): String {
        synchronized(SHA_256_CHARS) {
            return bytesToHex(bytes, SHA_256_CHARS)
        }
    }

    private fun bytesToHex(bytes: ByteArray, hexChars: CharArray): String {
        var v: Int
        for (j in bytes.indices) {
            v = bytes[j].toInt() and 0xFF
            hexChars[j * 2] = HEX_CHAR_ARRAY[v.ushr(4)]
            hexChars[j * 2 + 1] = HEX_CHAR_ARRAY[v and 0x0F]
        }
        return String(hexChars)
    }
}