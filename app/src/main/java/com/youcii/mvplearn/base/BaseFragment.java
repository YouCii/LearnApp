package com.youcii.mvplearn.base;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.youcii.mvplearn.app.App;

/**
 * Created by Administrator on 2017/6/1.
 */

public class BaseFragment extends Fragment {

    // 首先顺序加载static
    // 然后顺序加载非静态变量
    protected static int j = 0;
    protected int i = j;

    protected Activity activity;

    @Override
    public Context getContext() {
        if (activity == null) {
            return App.getContext();
        }
        return activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}
