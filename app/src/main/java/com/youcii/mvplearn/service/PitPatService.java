package com.youcii.mvplearn.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.orhanobut.logger.Logger;
import com.youcii.mvplearn.model.EasyEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by YouCii on 2016/5/11.
 */
public class PitPatService extends Service {
    protected Context context;

    private String IP = "10.11.204.64"; // 网络调试助手给予的IP和端口
    private int PORT = 8080;

    private static Socket socket = null;
    private static OutputStream out = null;
    private static InputStream in = null;
    private static BufferedReader bff = null;

    private static ReceiveThread rcvThread;
    private static ConnectThread cnctThread;

    private static boolean DetactionFlag = true; // 用于中断进程

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        SocketConnected(IP, PORT);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        CloseAll();

        super.onDestroy();
    }

    /**
     * 进行网络的连接,在线程中进行的网络的建立
     */
    private void SocketConnected(String ip, final int port) {
        cnctThread = new ConnectThread(ip, port);
        cnctThread.start();
    }

    private class ConnectThread extends Thread {
        String ip;
        int port;

        private ConnectThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            super.run();

            socket = new Socket();
            DetactionFlag = true;
            try {
                Logger.i("socket: " + "new SocketConnect");

                InetSocketAddress address = new InetSocketAddress(ip, port);
                socket.connect(address, 20000);

                /* socket = new Socket(ip, port);
                socket.setSoTimeout(20000); // 用这种方式的话会出现卡机现象 */

                out = socket.getOutputStream();
                in = socket.getInputStream();
                bff = new BufferedReader(new InputStreamReader(in)); // 接受数据的对象

                // 开启数据读取
                if (rcvThread == null || !rcvThread.isAlive()) {
                    rcvThread = new ReceiveThread();
                    rcvThread.start();
                }

                try {
                    out.write(("1, zzp\n").getBytes());  // TODO 发送信息
                    out.flush();
                    Logger.e("socket: " + "发送成功");
                } catch (Exception e) {
                    Logger.e("socket: " + "发送失败");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Logger.e("socket: " + "连接失败：" + e.toString());
            }
        }
    }

    /**
     * 接收数据线程
     */
    private class ReceiveThread extends Thread {
        public void run() {
            super.run();
            String read;
            while (DetactionFlag) {
                try {
                    if (bff != null) {
                        read = bff.readLine();
                        Logger.i("socket: " + "接收到数据: " + read); // TODO 接收信息
                        EventBus.getDefault().post(EasyEvent.PitPatGetCallBack + read);
                        break;
                    }
                } catch (IOException e) {
                    Logger.e("socket: " + "接收失败");
                    try {
                        sleep(5000);// 避免 网络出现错误后仍一直发送数据, 浪费电量流量
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }

            TryConnectedAgain();
        }
    }

    /**
     * 重新开启心跳发送
     */
    private void TryConnectedAgain() {
        CloseAll();

        SocketConnected(IP, PORT);
    }

    /**
     * 关闭所有
     */
    public static void CloseAll() {
        try {
            if (socket != null && !socket.isClosed()) {
                // 先停止在关闭，否则数据丢失
                socket.shutdownInput();
                socket.shutdownOutput();
                socket.close();  // 网络助手保存了历史链接, 但是发送不了, socket应该是断开了
            } else {
                Logger.i("socket: " + "socket was closed successfully!");
            }

            if (out != null) out.close();
            if (bff != null) bff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (rcvThread != null) {
            DetactionFlag = false;
            rcvThread.interrupt(); // 通过DDMS测试, 确实死掉了
        }
        if (cnctThread != null) {
            cnctThread.interrupt();// 通过DDMS测试, 确实死掉了
        }

    }
}
