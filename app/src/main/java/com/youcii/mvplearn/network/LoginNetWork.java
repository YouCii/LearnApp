package com.youcii.mvplearn.network;

import com.youcii.mvplearn.app.App;
import com.youcii.mvplearn.base.BaseNetWork;
import com.youcii.mvplearn.callback.LoginCallBack;

/**
 * Created by YouCii on 2016/7/15.
 */
public class LoginNetWork extends BaseNetWork {
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
	public void requestNetWork() {
		super.requestNetWork();
		startPostRequest(new LoginCallBack(App.getContext()));
	}
}
