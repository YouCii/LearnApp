package com.youcii.mvplearn.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

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
public class PitPatService extends Service { // 服务是主线程
	protected Context context;

	private String IP = ""; // 获取的IP和端口
	private int PORT = 0;

	private Socket socket = null;
	private OutputStream out = null;
	private BufferedReader bff = null;

	private ConnectThread connectThread;
	private ReceiveThread receiveThread;
	private DetectionThread detectionThread;

	private SocketStateListener socketStateListener;

	@Override
	public void onCreate() {
		super.onCreate();
		Logger.i("service: " + "onCreate"); // 生命周期：第一步
	}

	@Override
	public IBinder onBind(Intent intent) { // 生命周期：第二步
		IP = intent.getStringExtra("IP");
		PORT = Integer.parseInt(intent.getStringExtra("PORT"));

		socketConnected(IP, PORT);

		Logger.i("service: " + "onBind");
		return new ServiceBinder();
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
		Logger.i("service: " + "onRebind");

		IP = intent.getStringExtra("IP");
		PORT = Integer.parseInt(intent.getStringExtra("PORT"));

		tryConnectAgain();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Logger.i("service: " + "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) { // 生命周期：倒数第二步
		closeAll();

		Logger.i("service: " + "onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() { // 生命周期：最后一步
		super.onDestroy();
		Logger.i("service: " + "onDestroy");
	}

	public class ServiceBinder extends Binder {
		public PitPatService getService() {
			return PitPatService.this;
		}
	}

	/**
	 * 进行网络的连接,在线程中进行的网络的建立
	 */
	private void socketConnected(String ip, int port) {
		connectThread = new ConnectThread(ip, port);
		connectThread.start();
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

			// 开启断线重连
			if (detectionThread == null) detectionThread = new DetectionThread();
			detectionThread.start();

			try {
				Logger.i("socket: " + "new SocketConnect");

				InetSocketAddress address = new InetSocketAddress(ip, port);
				socket.connect(address, 20000);
				/* socket = new Socket(ip, port);
				socket.setSoTimeout(20000); // 用这种方式的话会出现卡机现象 */

				if (socketStateListener != null) socketStateListener.onConnect();

				// 开启数据读取
				if (receiveThread == null) receiveThread = new ReceiveThread();
				receiveThread.start();

				out = socket.getOutputStream();
				InputStream in = socket.getInputStream();
				bff = new BufferedReader(new InputStreamReader(in)); // 接受数据的对象

				sentMessage("new Connect");
			} catch (IOException e) {
				e.printStackTrace();
				Logger.e("socket: " + "连接失败：" + e.toString());
			}
		}
	}

	public void sentMessage(String string) {
		try {
			out.write((string + "\n").getBytes());
			out.flush();
			if (socketStateListener != null) socketStateListener.onSend(string);

			Logger.i("socket: " + "发送成功");
		} catch (Exception e) {
			Logger.e("socket: " + "发送失败");
			e.printStackTrace();
		}
	}

	/**
	 * 接收数据线程
	 */
	private class ReceiveThread extends Thread {
		public void run() {
			super.run();
			String read;
			try {
				while (!Thread.interrupted()) {
					try {
						if (bff != null) {
							read = bff.readLine();
							if (read != null) {
								Logger.i("socket: " + "接收到数据: " + read);
								if (socketStateListener != null)
									socketStateListener.onReceive(read);
							} else
								sleep(5000);
						}
					} catch (IOException e) {
						sleep(5000);
						Logger.e("socket接收失败：" + e);
						e.printStackTrace();
					}
				}
			} catch (InterruptedException e1) {
				Logger.e("接收数据进程" + this.getId() + "interrupt"); // 对sleep的try-catch必须放在while外面
			}
		}
	}

	/**
	 * 断线重连检测线程,创建后一直循环
	 */
	private class DetectionThread extends Thread {
		public void run() {
			try {
				while (!Thread.interrupted()) {
					sleep(5000);
					try {
						Logger.i("断线重连进程" + this.getId() + "在运行");
						if (socket != null) socket.sendUrgentData(0xFF);
					} catch (IOException e) {
						tryConnectAgain();
					}
				}
			} catch (InterruptedException ee) {
				Logger.e("断线重连进程" + this.getId() + "interrupt"); // 对sleep的try-catch必须放在while外面
			}
		}
	}

	/**
	 * 断线重连方法
	 */
	private void tryConnectAgain() {
		Logger.e("断线重连了");
		closeAll();
		socketConnected(IP, PORT);
	}

	/**
	 * 关闭所有
	 */
	private void closeAll() {
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

		if (receiveThread != null) {
			receiveThread.interrupt(); // 此方法并不是强制中断进程，而是取消阻塞，包括sleep，令其迅速执行完成
			receiveThread = null;
		}
		if (detectionThread != null) {
			detectionThread.interrupt();
			detectionThread = null;
		}
		if (connectThread != null) {
			connectThread.interrupt();
			connectThread = null;
		}

		if (socketStateListener != null)
			socketStateListener.onBreak();
	}

	public interface SocketStateListener {

		void onBreak();

		void onConnect();

		void onReceive(String string);

		void onSend(String string);

	}

	public void setSocketStateListener(SocketStateListener socketStateListener) {
		this.socketStateListener = socketStateListener;
	}

}
