package com.youcii.mvplearn.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.adapter.MainPagerAdapter;
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.model.MainMenuID;
import com.youcii.mvplearn.model.RecyclerBean;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.activity.interfaces.IMainView;
import com.youcii.mvplearn.fragment.BlockFragment;
import com.youcii.mvplearn.fragment.ChangeFragment;
import com.youcii.mvplearn.fragment.ItemFragment;
import com.youcii.mvplearn.fragment.RxFragment;
import com.youcii.mvplearn.fragment.SocketFragment;
import com.youcii.mvplearn.fragment.WebFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements IMainView, View.OnClickListener, ViewPager.OnPageChangeListener, ItemFragment.OnListFragmentInteractionListener, BlockFragment.OnFragmentInteractionListener {
	private List<Fragment> fragmentList = new ArrayList<>();
	private List<String> titleList = new ArrayList<>();

	@Bind(R.id.toolbar)
	Toolbar toolbar;
	@Bind(R.id.main_viewpager)
	ViewPager mViewPager;
	@Bind(R.id.fab)
	FloatingActionButton fab;
	@Bind(R.id.main_tab_layout)
	TabLayout mainTabLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		initToolBar("主页");

		initFragmentList();
		fab.setOnClickListener(this);

		MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
		mViewPager.setAdapter(mainPagerAdapter);
		mViewPager.addOnPageChangeListener(this);

		mainTabLayout.setTabMode(TabLayout.MODE_FIXED);
		mainTabLayout.setupWithViewPager(mViewPager);

		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.selector_main_tap_call);
		mainTabLayout.getTabAt(0).setCustomView(imageView);
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.selector_main_tap_conversation);
		mainTabLayout.getTabAt(1).setCustomView(imageView);
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.selector_main_tap_contact);
		mainTabLayout.getTabAt(2).setCustomView(imageView);
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.selector_main_tap_plugin);
		mainTabLayout.getTabAt(3).setCustomView(imageView);

		mainTabLayout.getTabAt(0).getCustomView().setSelected(true);
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
		centerButtonInit(menu); // 方式2：getMenuInflater().inflate(R.menu.menu_main, menu); // 通过布局定义
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * 初始化各个按键参数
	 */
	private void centerButtonInit(Menu menu) {
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
			TestListActivity.Companion.startActivity(this);
		} else {
			onBackPressed();
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
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {
		toolbar.setTitle(titleList.get(position));
	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

}
