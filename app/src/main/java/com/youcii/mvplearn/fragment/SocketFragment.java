package com.youcii.mvplearn.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BasePresenterFragment;
import com.youcii.mvplearn.fragment.interfaces.IFragSocketView;
import com.youcii.mvplearn.presenter.FragSocketPresenter;
import com.youcii.mvplearn.utils.ThreadPool;
import com.youcii.mvplearn.utils.ToastUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YouCii on 2016/12/3.
 */

public class SocketFragment extends BasePresenterFragment<IFragSocketView, FragSocketPresenter> implements IFragSocketView {

    @Bind(R.id.socket_message_window)
    TextView socketMessageWindow;
    @Bind(R.id.etServerIp)
    EditText etServerIp;
    @Bind(R.id.etServerPort)
    EditText etServerPort;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_socket, container, false);
        ButterKnife.bind(this, view);

		/* 取消下方EditText焦点 */
        socketMessageWindow.setFocusableInTouchMode(true);
        socketMessageWindow.requestFocus();

        socketMessageWindow.setMovementMethod(ScrollingMovementMethod.getInstance());

        return view;
    }

    @NotNull
    @Override
    public FragSocketPresenter initPresenter() {
        return new FragSocketPresenter(this, true);
    }

    @Override
    public void onStop() {
        super.onStop();

        presenter.socketBreak();
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
                presenter.socketSend();
                break;
            case R.id.socket_break:
                presenter.socketBreak();
                break;
            case R.id.socket_connect:
                presenter.socketConnect();
                break;
            case R.id.socket_current_thread:
                presenter.socketCurrentThread();
                break;
            default:
                break;
        }
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void showShortSnack() {
        ToastUtils.showShortSnack(activity.getWindow().getDecorView(), "待写");
    }

    @Override
    public void addMessageText(String s) {
        activity.runOnUiThread(() -> socketMessageWindow.append("\n" + s));

        ThreadPool.getThreadPool().execute(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 滚到底部
            activity.runOnUiThread(() -> {
                int offset = socketMessageWindow.getLineCount() * socketMessageWindow.getLineHeight();
                if (offset > socketMessageWindow.getHeight()) {
                    socketMessageWindow.scrollTo(0, offset - socketMessageWindow.getHeight());
                }
            });
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
