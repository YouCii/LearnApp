package com.youcii.mvplearn.network;

import android.support.annotation.Nullable;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.app.App;
import com.youcii.mvplearn.base.BaseCallBack;
import com.youcii.mvplearn.base.BaseNetWork;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class LoginNetWork extends BaseNetWork<String> {
	private String user, password;

	public LoginNetWork(String user, String password) {
		this.user = user;
		this.password = password;
	}

	@Override
	protected void initUrl() {
		setUrl("http://www.baidu.com");
	}

	@Override
	protected void initParams() {
		getParamsMap().put("user", user);
		getParamsMap().put("password", password);
	}

	@Override
	protected void initCallBack() {
		setCallBack(new BaseCallBack<String>(App.getInstance()) {
			@Override
			protected void onSuccess(String string) {
				setChanged();
				notifyObservers(App.getInstance().getString(R.string.success));
			}

			@Override
			public void onError(@Nullable Call call, Response response, Exception e) {
				super.onError(call, response, e);
				setChanged();
				notifyObservers(App.getInstance().getString(R.string.error));
			}
		});
	}
}
