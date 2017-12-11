package com.youcii.mvplearn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.activity.interfaces.ILoginView;
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.presenter.LoginPresenter;
import com.youcii.mvplearn.utils.PermissionUtils;
import com.youcii.mvplearn.utils.PhoneUtils;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author Administrator
 */
public class LoginActivity extends BaseActivity implements ILoginView, View.OnClickListener, TextView.OnEditorActionListener {

	private LoginPresenter loginPresenter;

	@Bind(R.id.qxjd)
	TextView qxjd;
	@Bind(R.id.login_progress)
	ProgressBar loginProgress;
	@Bind(R.id.bt_login)
	Button login;
	@Bind(R.id.et_user)
	AutoCompleteTextView etUser;
	@Bind(R.id.et_password)
	EditText etPassword;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);

		ButterKnife.bind(this);

		loginPresenter = new LoginPresenter(this);
		login.setOnClickListener(this);
		etUser.setOnEditorActionListener(this);
		etPassword.setOnEditorActionListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 取消焦点
		qxjd.setFocusable(true);
		qxjd.setFocusableInTouchMode(true);
		qxjd.requestFocus();

		turnLogin(PhoneUtils.isOnLine(this) != 0);

		PermissionUtils.examinePermission(this);
	}

	@Override
	public void loginSuccess() {
		turnProgress(false);
		clearPass();
		startActivity();
	}

	@Override
	public void loginFail(String errorInfo) {
		turnProgress(false);
		showToast("登录失败: " + errorInfo);
	}

	@Override
	public void clearPass() {
		etPassword.setText("");
	}

	@Override
	public void turnProgress(boolean onOff) {
		if (onOff) {
			loginProgress.setVisibility(View.VISIBLE);
		} else {
			loginProgress.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void turnLogin(boolean onOff) {
		login.setEnabled(onOff);
	}

	@Override
	public void showToast(String content) {
		ToastUtils.showShortSnack(getWindow().getDecorView(), content);
	}

	@Override
	public void startActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("userName", etUser.getText().toString());
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		ViewUtils.hideInput(this);
		loginPresenter.login(etUser.getText().toString(), etPassword.getText().toString());
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		switch (v.getId()) {
			case R.id.et_user:
				etPassword.requestFocus();
				break;
			case R.id.et_password:
				onClick(null);
				break;
			default:
				break;
		}
		return true;
	}
}

