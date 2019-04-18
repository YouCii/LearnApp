package com.youcii.mvplearn.presenter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.orhanobut.logger.Logger;
import com.youcii.mvplearn.service.IPitPatAidlInterface;
import com.youcii.mvplearn.service.ISocketStateListener;
import com.youcii.mvplearn.base.BasePresenter;
import com.youcii.mvplearn.fragment.interfaces.IFragSocketView;
import com.youcii.mvplearn.model.ServiceData;
import com.youcii.mvplearn.service.PitPatAidlService;
import com.youcii.mvplearn.service.PitPatService;
import com.youcii.mvplearn.utils.ThreadPool;

/**
 * @author YouCii
 * @date 2016/12/3
 */
public class FragSocketPresenter extends BasePresenter<IFragSocketView> {

    private boolean isAIDL = true;

    /**
     * 同一进程的服务binder
     */
    private PitPatService.ServiceBinder normalBinder;
    /**
     * 多进程的服务binder
     */
    private IPitPatAidlInterface aidlBinder;

    /**
     * 同一进程的connection
     */
    private ServiceConnection normalConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            normalBinder = (PitPatService.ServiceBinder) service;
            normalBinder.setSocketStateListener(normalListener);
        }

        @Override
        // 此方法只有在service异常时才调用
        public void onServiceDisconnected(ComponentName name) {
            normalBinder = null;
        }
    };

    /**
     * 多进程的connection
     */
    private ServiceConnection aidlConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidlBinder = IPitPatAidlInterface.Stub.asInterface(service);
            try {
                aidlBinder.setSocketStateListener(iSocketStateListener);
            } catch (RemoteException e) {
                e.printStackTrace();
                Logger.e("aidlBinder: " + "setSocketStateListener调用失败：" + e.toString());
            }
        }

        @Override
        // 此方法只有在service异常时才调用
        public void onServiceDisconnected(ComponentName name) {
            aidlBinder = null;
        }
    };

    public FragSocketPresenter(IFragSocketView iFragSocketView, boolean isAIDL) {
        super(iFragSocketView);
        this.isAIDL = isAIDL;
    }

    public void socketSend() {
        if (isAIDL) {
            if (aidlBinder != null) {
                ThreadPool.getThreadPool().execute(() -> {
                    try {
                        aidlBinder.sendMessage(new ServiceData());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Logger.e("aidlBinder: " + "sendMessage调用失败：" + e.toString());
                    }
                });
            }
        } else {
            if (normalBinder != null) {
                normalBinder.sendMessage("message from client");
            }
        }
    }

    public void socketBreak() {
        if (isAIDL) {
            if (aidlBinder != null) {
                if (getView() != null) {
                    getView().getContext().unbindService(aidlConnection);
                }
                try {
                    aidlBinder.onDestroy();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    Logger.e("aidlBinder: " + "onDestroy调用失败：" + e.toString());
                }
                aidlBinder = null;
            }
        } else {
            if (normalBinder != null) {
                // 会触发unBind()和onDestroy()
                if (getView() != null) {
                    getView().getContext().unbindService(normalConnection);
                }
                normalBinder.onDestroy();
                normalBinder = null;
            }
        }
    }

    public void socketConnect() {
        if (getView() != null) {
            if (isAIDL) {
                Intent intent = new Intent(getView().getContext(), PitPatAidlService.class);
                intent.putExtra("IP", getView().getIp());
                intent.putExtra("PORT", getView().getPort());
                getView().getContext().bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE);
            } else {
                Intent intent = new Intent(getView().getContext(), PitPatService.class);
                intent.putExtra("IP", getView().getIp());
                intent.putExtra("PORT", getView().getPort());
                // bindService 会触发onBind，不触发onStartCommand； 多次点击不会多次触发onBind
                getView().getContext().bindService(intent, normalConnection, Context.BIND_AUTO_CREATE);
                // startService 会触发onStartCommand, 不触发onBind(), 多次点击触发多次onStartCommand
                // getView().getContext().startService(intent);
            }
        }
    }

    public void socketCurrentThread() {
        if (getView() != null) {
            getView().showShortSnack();
        }
    }

    /**
     * 同一进程的SocketState监听
     */
    private PitPatService.SocketStateListener normalListener = new PitPatService.SocketStateListener() {

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

    /**
     * 跨进程的SocketState监听
     */
    private ISocketStateListener.Stub iSocketStateListener = new ISocketStateListener.Stub() {

        @Override
        public void onBreak() throws RemoteException {
            if (getView() != null) {
                getView().addMessageText("连接已断开");
            }
        }

        @Override
        public void onConnect() throws RemoteException {
            if (getView() != null) {
                getView().addMessageText("已连接");
            }
        }

        @Override
        public void onReceive(String string) throws RemoteException {
            if (getView() != null) {
                getView().addMessageText("接收到消息：" + string);
            }
        }

        @Override
        public void onSend(String string) throws RemoteException {
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
