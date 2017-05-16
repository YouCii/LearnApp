package com.youcii.mvplearn.presenter.fragment;

import android.content.Context;
import android.content.pm.PackageManager;

import com.youcii.mvplearn.presenter.fragment.interfaces.IFragSocketPresenter;
import com.youcii.mvplearn.view.fragment.interfaces.IFragSocketView;

/**
 * Created by YouCii on 2016/12/3.
 */

public class FragSocketPresenter implements IFragSocketPresenter {
    Context context;
    IFragSocketView iFragSocketView;

    public FragSocketPresenter(Context context, IFragSocketView iFragSocketView) {
        this.iFragSocketView = iFragSocketView;
        this.context = context;
    }

    @Override
    public void socketSend() {

    }

    @Override
    public void socketBreak() {

    }

    @Override
    public void socketConnect() {

    }

    @Override
    public void socketCurrentThread() {
        PackageManager pm = context.getPackageManager();
    }
}
