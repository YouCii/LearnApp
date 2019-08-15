package com.youcii.mvplearn.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.youcii.mvplearn.app.App;

/**
 * Created by Administrator on 2017/6/1.
 */
public class BaseFragment extends Fragment {

    // 首先顺序加载static
    // 然后顺序加载非静态变量
    // 然后再子类
    protected static int j = 0;
    protected int i = j;

    protected Activity activity;

    /**
     * 用于缓存View, 避免重复创建
     * 目前看来只有在 FragmentPagerAdapter 中才会有用一些
     */
    private View contentView;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = super.onCreateView(inflater, container, savedInstanceState);
        }
        return contentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 只在show/hide发生改变时回调, ViewPager切换时此方法不执行
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * 1. 在fragment创建时执行一遍返回false,
     * 2. 在fragment显示与不显示切换时回调
     *
     * 废弃原因:
     * 此方法只有在ViewPager中需要用来判断是否可见, 这是因为在以往版本的ViewPager中, setOffScreenPageLimit范围内的Fragment会出现多个onResume, 只有借助此方法才可以判断真正显示的Fragment
     * 目前ViewPager可以通过BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT来制定只有当前Fragment会执行到onResume, 此方法就没有用了.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /**
     * FragmentPagerAdapter切换时:
     * 没有在setOffScreenPageLimit范围内的fragment会执行到这onDestroyView
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * FragmentStatePagerAdapter切换时:
     * 没有在setOffScreenPageLimit范围内的fragment会执行到onDestroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}
