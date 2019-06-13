package com.youcii.mvplearn.presenter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import com.youcii.mvplearn.R
import com.youcii.mvplearn.app.App

/**
 * Created by jdw on 2019/06/12.
 *
 * 用于测试分组通知, 坑:
 * 1. 分组必须单独发送setGroupSummary的一条通知
 * 2. 最大显示9条, 超过9条的被干掉; 如果是同一分组, 只能显示8条, 删掉某条后中最多只会再显示出一条, 也就是仍然9条
 */
object NotificationPresenter {

    private const val CHANNEL_ID = "channel_id"
    private const val GROUP_ID = "group_id"

    private val context = App.getInstance()
    private val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "channel名称", importance).apply { description = "channel描述" }
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showGroupNotification(content: Int) {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("title")
                .setContentText("$content")
                .setGroup(GROUP_ID)
        notificationManager.notify(content, notificationBuilder.build())

        sendSummery()
    }

    private fun sendSummery() {
        val notificationBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setGroup(GROUP_ID)
                .setGroupSummary(true)
        notificationManager.notify(notificationManager.hashCode(), notificationBuilder.build())
    }

}