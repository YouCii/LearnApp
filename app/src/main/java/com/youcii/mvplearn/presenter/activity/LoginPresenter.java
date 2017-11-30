package com.youcii.mvplearn.presenter.activity;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.app.App;
import com.youcii.mvplearn.network.LoginNetWork;
import com.youcii.mvplearn.activity.interfaces.ILoginView;

import java.util.Observable;
import java.util.Observer;

/**
 * @author YouCii
 * @date 2016/7/15
 */
public class LoginPresenter implements Observer {

	private ILoginView iLoginView;

	public LoginPresenter(ILoginView iLoginView) {
		this.iLoginView = iLoginView;
	}

	public void login(String user, String password) {
		iLoginView.turnProgress(true);

		LoginNetWork loginNetWork = new LoginNetWork(user, password);
		loginNetWork.addObserver(this);
		loginNetWork.postNetWork();
	}

	public void saveUser(String user, String password) {
		iLoginView.showToast("getEvent");
	}

	@Override
	public void update(Observable observable, Object data) {
		if (observable instanceof LoginNetWork) {
			if (data instanceof String) {
				if (App.getInstance().getString(R.string.success).equals(data)) {
					iLoginView.loginSuccess();
				} else {
					iLoginView.loginFail();
				}
			}
		}
	}
}
