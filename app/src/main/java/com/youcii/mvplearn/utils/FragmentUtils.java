package com.youcii.mvplearn.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YouCii on 2016/8/30.
 */
public class FragmentUtils {

    private ArrayList<Fragment> fragmentArray = new ArrayList<>();
    private FragmentManager fragmentManager;

    public FragmentUtils(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void replaceContent(Fragment to, int containerViewId) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        List<Fragment> fragmentList = fragmentManager.getFragments();
        for (Fragment from : fragmentList) {
            fragmentTransaction.hide(from);
        }

        if (!to.isAdded() && !fragmentArray.contains(to)) { // 切换太快的话，isAdded函数返回会出错，自己再次判断一下
            fragmentTransaction.add(containerViewId, to);
            fragmentArray.add(to);
        }

        if (fragmentTransaction.commit() != 0) {
            fragmentTransaction.show(to);
        }
    }
}
