package com.youcii.mvplearn.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.youcii.mvplearn.listener.BackOnGestureListener;

/**
 * @author Administrator
 */
public abstract class BaseActivity extends AppCompatActivity {

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(isLandscape() ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mGestureDetector = supportBackGesture() ? new GestureDetector(getApplicationContext(), new BackOnGestureListener(this)) : null;
    }

    /**
     * 默认是竖屏，如果某界面希望是横屏，重写此方法，直接return true即可
     *
     * @return true 横屏；false 竖屏
     */
    protected boolean isLandscape() {
        return false;
    }

    /**
     * 默认支持右滑返回, 如果希望某界面屏蔽此手势, 直接重写return false即可
     *
     * @return true 使能；false 失能
     */
    protected boolean supportBackGesture() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mGestureDetector == null ? super.dispatchTouchEvent(ev) : (mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev));
    }

}
