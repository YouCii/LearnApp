package com.youcii.mvplearn.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.adapter.DeviceListAdapter;
import com.youcii.mvplearn.adapter.DeviceListAdapter.Device;
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.presenter.ListRefreshPresenter;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.activity.interfaces.IListRefreshView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListRefreshActivity extends BaseActivity implements IListRefreshView, Observer {

	@Bind(R.id.listview)
	ListView listview;

	ListRefreshPresenter listRefreshPresenter;
	List<DeviceListAdapter.Device> deviceList = new ArrayList<>();
	DeviceListAdapter deviceListAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_refresh);

		ButterKnife.bind(this);

		initList(deviceList);
		listRefreshPresenter = new ListRefreshPresenter(this);
		listRefreshPresenter.addObserver(this);
		listRefreshPresenter.startRefresh(deviceList);
		deviceListAdapter = new DeviceListAdapter(ListRefreshActivity.this, deviceList);
		listview.setAdapter(deviceListAdapter);
	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof ListRefreshPresenter) {
			Collections.sort(deviceList, new DeviceListAdapter.DeviceComparator());
			deviceListAdapter.notifyDataSetChanged();
		}
	}

	@Override
	public void initList(List<Device> list) {
		deviceList.add(new Device("name1", "11", "11", 100));
		deviceList.add(new Device("name2", "22", "22", 200));
		deviceList.add(new Device("name3", "33", "33", 300));
		deviceList.add(new Device("name4", "44", "44", 400));
		deviceList.add(new Device("name5", "55", "55", 400));
		deviceList.add(new Device("name6", "66", "66", 400));
		deviceList.add(new Device("name7", "77", "77", 400));
		deviceList.add(new Device("name8", "88", "88", 400));
		deviceList.add(new Device("name9", "99", "99", 400));
		deviceList.add(new Device("name0", "00", "00", 400));
	}

	@Override
	public void doOnFinish() {
		ToastUtils.showShortToast(this, "结束");
	}

	public static void startActivity(Context context) {
		Intent intent = new Intent(context, ListRefreshActivity.class);
		context.startActivity(intent);
	}

}
