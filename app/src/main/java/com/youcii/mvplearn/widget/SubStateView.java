package com.youcii.mvplearn.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.youcii.mvplearn.R;

/**
 * Created by YouCii on 2017/5/16.
 */

public class SubStateView extends LinearLayout {
	public static final int STATE_SUBSCRIBED = 0; // 已订阅
	public static final int STATE_NOT_SUBSCRIBED = 1; // 未订阅
	public static final int STATE_DISABLE = 2; // 未开通
	public static final int STATE_CHANGING = 3; // 接口请求中

	private int currentState;

	private ProgressBar progressBar;
	private ImageView imageView;

	public SubStateView(Context context) {
		super(context);
	}

	public SubStateView(Context context, AttributeSet attrs) {
		super(context, attrs);

		progressBar = new ProgressBar(context);
		imageView = new ImageView(context);

		progressBar.setVisibility(GONE);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		imageView.setVisibility(GONE);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);

		addView(progressBar);
		addView(imageView);
	}

	public void setState(int state) {
		currentState = state;

		switch (state) {
			case STATE_SUBSCRIBED:
				if (progressBar.isShown()) {
                    progressBar.setVisibility(GONE);
                }
				if (!imageView.isShown()) {
                    imageView.setVisibility(VISIBLE);
                }
				imageView.setBackgroundResource(R.drawable.substate_yes);
				break;
			case STATE_NOT_SUBSCRIBED:
				if (progressBar.isShown()) {
                    progressBar.setVisibility(GONE);
                }
				if (!imageView.isShown()) {
                    imageView.setVisibility(VISIBLE);
                }
				imageView.setBackgroundResource(R.drawable.substate_not);
				break;
			case STATE_DISABLE:
				if (progressBar.isShown()) {
                    progressBar.setVisibility(GONE);
                }
				if (!imageView.isShown()) {
                    imageView.setVisibility(VISIBLE);
                }
				imageView.setBackgroundResource(R.drawable.substate_cannot);
				break;
			case STATE_CHANGING:
				if (!progressBar.isShown()) {
                    progressBar.setVisibility(VISIBLE);
                }
				if (imageView.isShown()) {
                    imageView.setVisibility(GONE);
                }
				break;
			default:
				break;
		}
	}

	public int getCurrentState() {
		return currentState;
	}

}
