package com.youcii.mvplearn.view.activity;

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
import com.youcii.mvplearn.base.BaseActivity;
import com.youcii.mvplearn.presenter.activity.LoginPresenter;
import com.youcii.mvplearn.utils.PermissionUtils;
import com.youcii.mvplearn.utils.PhoneUtils;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.utils.ViewUtils;
import com.youcii.mvplearn.view.activity.interfaces.ILoginView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        EventBus.getDefault().register(this);

        loginPresenter = new LoginPresenter(this);
        login.setOnClickListener(this);
        etUser.setOnEditorActionListener(this);
        etPassword.setOnEditorActionListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        /** 取消焦点 */
        qxjd.setFocusable(true);
        qxjd.setFocusableInTouchMode(true);
        qxjd.requestFocus();

        turnLogin(PhoneUtils.isOnLine(this) != 0);

        PermissionUtils.examinePermission(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEventMainThread(String loginResponse) {
        if ("登陆成功".equals(loginResponse)) {
            turnProgress(false);
            clearPass();
            loginPresenter.saveUser(etUser.getText().toString(), etPassword.getText().toString());

            startActivity();
        } else {
            showToast("登录失败");
        }
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

