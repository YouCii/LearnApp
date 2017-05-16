package com.youcii.mvplearn.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.diyview.SubStateView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiyViewActivity extends BaseActivity {

	@Bind(R.id.sv_sub_state)
	SubStateView subStateView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diy_view);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.bt_change)
	void onClick() {
		if (subStateView.getCurrentState() < 3)
			subStateView.setState(subStateView.getCurrentState() + 1);
		else
			subStateView.setState(0);
	}

	public static void openActivity(Context context) {
		context.startActivity(new Intent(context, DiyViewActivity.class));
	}

}
