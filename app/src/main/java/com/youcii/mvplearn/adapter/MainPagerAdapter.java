package com.youcii.mvplearn.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 1.FragmentStatePagerAdapter:
 * a. 适合大量页面，不断重建和销毁, viewpager切换时执行到onDestroy;
 * b. 会保存内部fragments
 * 2.FragmentPagerAdapter:
 * a. 适合少量页面，常驻内存, viewpager切换时仅执行到onDestroyView;
 * b. 不会保存内部fragments
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> titleList;

    public MainPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    @NonNull
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < fragmentList.size()) {
            return titleList.get(position);
        } else {
            return null;
        }
    }

}