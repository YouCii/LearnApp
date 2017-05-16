package com.youcii.mvplearn.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.adapter.MainPagerAdapter;
import com.youcii.mvplearn.model.MainMenuID;
import com.youcii.mvplearn.model.RecyclerBean;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.view.activity.interfaces.IMainView;
import com.youcii.mvplearn.view.fragment.BlockFragment;
import com.youcii.mvplearn.view.fragment.ChangeFragment;
import com.youcii.mvplearn.view.fragment.ItemFragment;
import com.youcii.mvplearn.view.fragment.RxFragment;
import com.youcii.mvplearn.view.fragment.SocketFragment;
import com.youcii.mvplearn.view.fragment.WebFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView, View.OnClickListener, RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener, ItemFragment.OnListFragmentInteractionListener, BlockFragment.OnFragmentInteractionListener {
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.main_viewpager)
    ViewPager mViewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.bottom_tap)
    RadioGroup bottom_tap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initToolBar("主页");

        initFragmentList();
        fab.setOnClickListener(this);
        bottom_tap.setOnCheckedChangeListener(this);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.addOnPageChangeListener(this);

        bottom_tap.check(bottom_tap.getChildAt(0).getId());
    }

    @Override
    public void initFragmentList() {
        fragmentList.add(new SocketFragment());
        fragmentList.add(new RxFragment());
        fragmentList.add(new ChangeFragment());
        fragmentList.add(new WebFragment());

        titleList.add("Socket通信");
        titleList.add("RxJava示例");
        titleList.add("切换示例");
        titleList.add("原生与html互相调用");
    }

    @Override
    public void initToolBar(String title) {
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.toolbar_back);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        CenterButtonInit(menu); // 方式2：getMenuInflater().inflate(R.menu.menu_main, menu); // 通过布局定义
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化各个按键参数
     */
    private void CenterButtonInit(Menu menu) {
        menu.clear();

        menu.add(0, MainMenuID.设置, 0, "设置");
        menu.add(0, MainMenuID.关于, 0, "关于");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == MainMenuID.设置 || item.getItemId() == R.id.action_settings) {
            showToast("设置");
            return true;
        }
        if (item.getItemId() == MainMenuID.关于 || item.getItemId() == R.id.action_about) {
            showToast("关于");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showToast(String content) {
        ToastUtils.showShortToast(this, content);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab) {
            TestListActivity.openActivity(this);
        } else {
            finish();
        }
    }

    @Override
    public void onListFragmentInteraction(RecyclerBean recyclerBean) {
        showToast("点击了 " + recyclerBean.getId() + "：" + recyclerBean.getContent());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        View checkView = findViewById(checkedId);
        int position = bottom_tap.indexOfChild(checkView);
        toolbar.setTitle(titleList.get(position));
        if (checkView != null && checkView.isPressed())
            mViewPager.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // git 上传测试
    }

    @Override
    public void onPageSelected(int position) {
        bottom_tap.check(bottom_tap.getChildAt(position).getId());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
