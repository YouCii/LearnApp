package com.youcii.mvplearn.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.youcii.mvplearn.app.App;

/**
 * Created by Administrator on 2017/6/1.
 */

public class BaseFragment extends Fragment {

	protected Activity activity;

	@Override
	public Context getContext() {
		if (activity == null) {
            return App.getContext();
        }
		return activity;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		activity = (Activity) context;
	}

	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}
}
