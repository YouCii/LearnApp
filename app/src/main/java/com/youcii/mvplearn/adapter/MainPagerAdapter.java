package com.youcii.mvplearn.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
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

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);

        currentFragment = (Fragment) object;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    @Override
    @NonNull
    public Fragment instantiateItem(@NonNull ViewGroup container, int position) { /* 用于保存原fragment状态 */
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) { /* 用于保存原fragment状态 */
        Fragment fragment = fragmentList.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }
}