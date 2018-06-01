package com.youcii.mvplearn.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.youcii.mvplearn.base.BasePresenter;
import com.youcii.mvplearn.fragment.interfaces.IFragSocketView;
import com.youcii.mvplearn.service.PitPatService;

/**
 * @author YouCii
 * @date 2016/12/3
 */
public class FragSocketPresenter extends BasePresenter<IFragSocketView> {
    private PitPatService pitPatService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            pitPatService = ((PitPatService.ServiceBinder) service).getService();
            pitPatService.setSocketStateListener(socketStateListener);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) { // 此方法只有在service异常时才调用
            pitPatService = null;
        }
    };

    public FragSocketPresenter(IFragSocketView iFragSocketView) {
        super(iFragSocketView);
    }

    public void socketSend() {
        if (pitPatService != null) {
            pitPatService.sentMessage("message from client");
        }
    }

    public void socketBreak() {
        if (pitPatService != null) {
            // 会触发unBind()和onDestroy()
            if (getView() != null) {
                getView().getContext().unbindService(connection);
            }
            pitPatService.onDestroy();
            pitPatService = null;
        }
    }

    public void socketConnect() {
        if (getView() != null) {
            Intent intent = new Intent(getView().getContext(), PitPatService.class);
            intent.putExtra("IP", getView().getIp());
            intent.putExtra("PORT", getView().getPort());
            // 会触发onCreate()和onBind()，不触发onStartCommand； 多次点击不会多次触发
            getView().getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    public void socketCurrentThread() {
        if (getView() != null) {
            getView().showShortSnack();
        }
    }

    private PitPatService.SocketStateListener socketStateListener = new PitPatService.SocketStateListener() {

        @Override
        public void onBreak() {
            if (getView() != null) {
                getView().addMessageText("连接已断开");
            }
        }

        @Override
        public void onConnect() {
            if (getView() != null) {
                getView().addMessageText("已连接");
            }
        }

        @Override
        public void onReceive(String string) {
            if (getView() != null) {
                getView().addMessageText("接收到消息：" + string);
            }
        }

        @Override
        public void onSend(String string) {
            if (getView() != null) {
                getView().addMessageText("发送消息：" + string);
            }
        }
    };

    @Override
    public void detach() {
        socketBreak();
    }

}
