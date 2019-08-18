package com.youcii.mvplearn.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.youcii.mvplearn.R
import com.youcii.mvplearn.base.BaseActivity
import com.youcii.mvplearn.flutter.SingleFlutterView

class FlutterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter)
        addContentView(SingleFlutterView.createView(this), FrameLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FlutterActivity::class.java))
        }
    }
}