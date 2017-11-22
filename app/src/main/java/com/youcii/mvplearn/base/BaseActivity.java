package com.youcii.mvplearn.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
		setBackGestureAvailable(true);
	}

	/**
	 * 默认是竖屏，如果某界面希望是横屏，重写此方法，直接return true即可
	 *
	 * @return true 横屏；false 竖屏
	 */
	protected boolean isLandscape() {
		return false;
	}

	protected void setBackGestureAvailable(boolean supportGesture) {
		mGestureDetector = supportGesture ? new GestureDetector(getApplicationContext(), new BackOnGestureListener(this)) : null;
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		return mGestureDetector == null ? super.dispatchTouchEvent(ev) : (mGestureDetector.onTouchEvent(ev) || super.dispatchTouchEvent(ev));
	}

}
