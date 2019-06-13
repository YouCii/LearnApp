package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.youcii.mvplearn.R
import com.youcii.mvplearn.presenter.NotificationPresenter
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity() {

    private var notificationContent = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        fab.setOnClickListener {
            NotificationPresenter.showGroupNotification(++notificationContent)
        }
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, NotificationActivity::class.java))
        }
    }

}
