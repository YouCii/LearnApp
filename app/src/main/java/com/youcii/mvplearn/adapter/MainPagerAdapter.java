package com.youcii.mvplearn.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> titleList;

    private FragmentManager fragmentManager;
    private Fragment currentFragment;

    public MainPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragmentList, List<String> titleList) {
        super(fragmentManager);

        this.fragmentManager = fragmentManager;
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
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

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);

        currentFragment = (Fragment) object;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    public Fragment instantiateItem(ViewGroup container, int position) { /* 用于保存原fragment状态 */
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) { /* 用于保存原fragment状态 */
        Fragment fragment = fragmentList.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }
}