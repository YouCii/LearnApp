package com.youcii.mvplearn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseFragment;
import com.youcii.mvplearn.presenter.FragSocketPresenter;
import com.youcii.mvplearn.utils.ThreadPool;
import com.youcii.mvplearn.fragment.interfaces.IFragSocketView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YouCii on 2016/12/3.
 */

public class SocketFragment extends BaseFragment implements IFragSocketView {

	@Bind(R.id.sv_message)
	ScrollView svMessage;
	@Bind(R.id.socket_message_window)
	TextView socketMessageWindow;
	@Bind(R.id.etServerIp)
	EditText etServerIp;
	@Bind(R.id.etServerPort)
	EditText etServerPort;

	private FragSocketPresenter fragSocketPresenter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_socket, container, false);
		ButterKnife.bind(this, view);

		/* 取消下方EditText焦点 */
		socketMessageWindow.setFocusableInTouchMode(true);
		socketMessageWindow.requestFocus();

		fragSocketPresenter = new FragSocketPresenter(activity, this);

		return view;
	}

	@Override
	public void onStop() {
		super.onStop();

		fragSocketPresenter.socketBreak();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

		ButterKnife.unbind(this);
	}

	@OnClick({R.id.socket_send, R.id.socket_break, R.id.socket_connect, R.id.socket_current_thread})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.socket_send:
				fragSocketPresenter.socketSend();
				break;
			case R.id.socket_break:
				fragSocketPresenter.socketBreak();
				break;
			case R.id.socket_connect:
				fragSocketPresenter.socketConnect();
				break;
			case R.id.socket_current_thread:
				fragSocketPresenter.socketCurrentThread();
				break;
			default:
				break;
		}
	}

	@Override
	public void addMessageText(String s) {
		socketMessageWindow.append("\n" + s);

		ThreadPool.getThreadPool().execute(() -> {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 滚到底部
			activity.runOnUiThread(() -> svMessage.fullScroll(ScrollView.FOCUS_DOWN));
		});
	}

	@Override
	public String getIp() {
		return etServerIp.getText().toString();
	}

	@Override
	public String getPort() {
		return etServerPort.getText().toString();
	}

}
