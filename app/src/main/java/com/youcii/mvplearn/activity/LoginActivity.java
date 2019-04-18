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

import com.jakewharton.rxbinding2.view.RxView;
import com.orhanobut.logger.Logger;
import com.youcii.mvplearn.R;
import com.youcii.mvplearn.activity.interfaces.ILoginView;
import com.youcii.mvplearn.base.BasePresenterActivity;
import com.youcii.mvplearn.presenter.LoginPresenter;
import com.youcii.mvplearn.utils.PermissionUtils;
import com.youcii.mvplearn.utils.PhoneUtils;
import com.youcii.mvplearn.utils.ToastUtils;
import com.youcii.mvplearn.utils.ViewUtils;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 */
public class LoginActivity extends BasePresenterActivity<ILoginView, LoginPresenter> implements ILoginView, TextView.OnEditorActionListener {

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
        Logger.i("Login--onCreate");

        ButterKnife.bind(this);

        etUser.setOnEditorActionListener(this);
        etPassword.setOnEditorActionListener(this);

        subscribeLoginObserver(RxView.clicks(login));
    }

    private void subscribeLoginObserver(Observable<Object> observable) {
        Disposable ignore = observable
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe((v) -> {
                    ViewUtils.hideInput(this);
                    presenter.login(etUser.getText().toString(), etPassword.getText().toString());
                });
    }

    @NotNull
    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.i("Login--onResume");

        // 取消焦点
        qxjd.setFocusable(true);
        qxjd.setFocusableInTouchMode(true);
        qxjd.requestFocus();

        turnLogin(PhoneUtils.isOnLine());

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
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.et_user:
                etPassword.requestFocus();
                break;
            case R.id.et_password:
                subscribeLoginObserver(Observable.fromArray(1));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Logger.i("Login--onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.i("Login--onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Logger.i("Login--onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.i("Login--onDestroy");

        // A > B
        // Login--onPause
        // Main--onCreate
        // Main--onStart
        // Main--onResume
        // Login--onStop

        // B > A
        // Main--onPause
        // Login--onStart
        // Login--onResume
        // Main--onStop
        // Main--onDestroy

        // Activity会在onResume后显示
    }
}

