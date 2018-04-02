package com.youcii.mvplearn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.utils.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class CollapsingActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsing);
        ButterKnife.bind(this);

        initToolBar("Collapsing");
        fab.setOnClickListener(this);
    }

    private void initToolBar(String title) {
        collapsingToolbarLayout.setTitle(title);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.toolbar_back);
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            ToastUtils.showShortToast("click fab");
        } else {
            onBackPressed();
        }
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CollapsingActivity.class));
    }

}
