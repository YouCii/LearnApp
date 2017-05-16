package com.youcii.mvplearn.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.model.EasyEvent;
import com.youcii.mvplearn.presenter.fragment.FragSocketPresenter;
import com.youcii.mvplearn.service.PitPatService;
import com.youcii.mvplearn.view.fragment.interfaces.IFragSocketView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YouCii on 2016/12/3.
 */

public class SocketFragment extends Fragment implements IFragSocketView {

    @Bind(R.id.socket_message_window)
    TextView socketMessageWindow;

    FragSocketPresenter fragSocketPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_socket, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);

        fragSocketPresenter = new FragSocketPresenter(getContext(), this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        getContext().startService(new Intent(getContext(), PitPatService.class));
    }

    @Override
    public void onStop() {
        super.onStop();

        getContext().stopService(new Intent(getContext(), PitPatService.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
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
        }
    }

    @Subscribe
    public void onEventMainThread(String message) {
        if (message.contains(EasyEvent.PitPatGetCallBack)) {
            addMessageText("接收到数据: " + message.replace(EasyEvent.PitPatGetCallBack, ""));
        }
    }

    @Override
    public void addMessageText(String s) {
        socketMessageWindow.setText(getMessageText() + "\n" + s); // 哪怕执行到这, 也需要点一下屏幕控件才会显示出来
    }

    @Override
    public String getMessageText() {
        return socketMessageWindow.getText().toString();
    }
}
