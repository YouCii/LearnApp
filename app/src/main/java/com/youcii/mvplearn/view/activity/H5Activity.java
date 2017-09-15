package com.youcii.mvplearn.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class H5Activity extends BaseActivity {

	@Bind(R.id.imageView)
	ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_h5);
		ButterKnife.bind(this);
	}



}
