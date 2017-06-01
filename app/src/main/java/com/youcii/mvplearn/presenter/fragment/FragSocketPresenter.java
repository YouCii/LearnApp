package com.youcii.mvplearn.presenter.fragment;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.youcii.mvplearn.presenter.fragment.interfaces.IFragSocketPresenter;
import com.youcii.mvplearn.service.PitPatService;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.view.fragment.interfaces.IFragSocketView;

/**
 * Created by YouCii on 2016/12/3.
 */

public class FragSocketPresenter implements IFragSocketPresenter {
	private Activity activity;

	private IFragSocketView iFragSocketView;
	private PitPatService pitPatService;

	private ServiceConnection connection = new ServiceConnection() {
		public void onServiceConnected(ComponentName name, IBinder service) {
			pitPatService = ((PitPatService.ServiceBinder) service).getService();
			pitPatService.setSocketStateListener(socketStateListener);
		}

		public void onServiceDisconnected(ComponentName name) { // 此方法只有在service异常时才调用
			pitPatService = null;
		}
	};

	public FragSocketPresenter(Activity activity, IFragSocketView iFragSocketView) {
		this.iFragSocketView = iFragSocketView;
		this.activity = activity;
	}

	@Override
	public void socketSend() {
		if (pitPatService != null) pitPatService.sentMessage("message from client");
	}

	@Override
	public void socketBreak() {
		if (pitPatService != null) {
			activity.unbindService(connection); // 会触发unBind()和onDestroy()
			pitPatService = null;
		}
	}

	@Override
	public void socketConnect() {
		Intent intent = new Intent(activity, PitPatService.class);
		intent.putExtra("IP", iFragSocketView.getIp());
		intent.putExtra("PORT", iFragSocketView.getPort());
		activity.bindService(intent, connection, Context.BIND_AUTO_CREATE); // 会触发onCreate()和onBind()，不触发onStartCommand； 多次点击不会多次触发
	}

	@Override
	public void socketCurrentThread() {
		ToastUtils.showShortSnack(activity.getWindow().getDecorView(), "待写");
	}

	private PitPatService.SocketStateListener socketStateListener = new PitPatService.SocketStateListener() {

		@Override
		public void onBreak() {
			activity.runOnUiThread(() -> iFragSocketView.addMessageText("连接已断开"));
		}

		@Override
		public void onConnect() {
			activity.runOnUiThread(() -> iFragSocketView.addMessageText("已连接"));
		}

		@Override
		public void onReceive(String string) {
			activity.runOnUiThread(() -> iFragSocketView.addMessageText("接收到消息：" + string));
		}

		@Override
		public void onSend(String string) {
			activity.runOnUiThread(() -> iFragSocketView.addMessageText("发送消息：" + string));
		}
	};

}
