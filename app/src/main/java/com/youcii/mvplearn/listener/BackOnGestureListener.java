package com.youcii.mvplearn.listener;

import androidx.appcompat.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2017/5/25.
 */

public class BackOnGestureListener extends GestureDetector.SimpleOnGestureListener {

	private AppCompatActivity activity;

	public BackOnGestureListener(AppCompatActivity activity) {
		this.activity = activity;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getX() < 50 && (e2.getX() - e1.getX()) > 200 && Math.abs(e1.getY() - e2.getY()) < 200) {
			activity.onBackPressed();
			return true;
		}
		return super.onFling(e1, e2, velocityX, velocityY);
	}
}
