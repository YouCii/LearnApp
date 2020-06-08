package com.youcii.mvplearn.flutter

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StringCodec
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterNativeView
import io.flutter.view.FlutterRunArguments
import io.flutter.view.FlutterView

object SingleFlutterView {

    fun init(context: Context) {
        FlutterMain.startInitialization(context)
        FlutterMain.ensureInitializationComplete(context, null)
    }

    fun createView(activity: AppCompatActivity, route: String = "./"): FlutterView {
        val flutterView = object : FlutterView(activity, null, FlutterNativeView(activity)) {
            private val lifecycleMessages = BasicMessageChannel(this, "flutter/lifecycle", StringCodec.INSTANCE)
            override fun onFirstFrame() {
                super.onFirstFrame()
                alpha = 1.0f
            }

            override fun onPostResume() {
                lifecycleMessages.send("AppLifecycleState.resumed")
            }
        }
        flutterView.setInitialRoute(route)
        activity.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun onCreate() {
                val arguments = FlutterRunArguments()
                arguments.bundlePath = FlutterMain.findAppBundlePath(activity.applicationContext)
                arguments.entrypoint = "main"
                flutterView.runFromBundle(arguments)
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                flutterView.onStart()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                flutterView.onPostResume()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onPause() {
                flutterView.onPause()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onStop() {
                flutterView.onStop()
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                flutterView.destroy()
            }
        })
        flutterView.alpha = 0.0f
        return flutterView
    }
}
