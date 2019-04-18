package com.youcii.mvplearn.utils;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jdw
 * @date 2017/11/21
 */
public class ThreadPool {

	/**
	 * 线程池核心线程数
	 */
	private static int CORE_POOL_SIZE = 0;
	/**
	 * 线程池最大线程数
	 */
	private static int MAX_POOL_SIZE = Integer.MAX_VALUE;
	/**
	 * 额外线程空状态生存时间
	 */
	private static int KEEP_ALIVE_TIME = 60;
	/**
	 * 阻塞队列。当核心线程都被占用，且阻塞队列已满的情况下，才会开启额外线程W
	 * SynchronousQueue 类似于一个缓存值为1的阻塞队列, 但它内部没有任何容量
	 */
	private static SynchronousQueue<Runnable> workQueue = new SynchronousQueue<>();
	/**
	 * 线程池
	 */
	private static ThreadPoolExecutor threadPool;

	private ThreadPool() {
	}

	public static ExecutorService getThreadPool() {
		if (threadPool == null) {
			// 其实就是 Executors.newCachedThreadPool()
			threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, workQueue, threadFactory);
		}
		return threadPool;
	}

	public static ThreadFactory threadFactory = new ThreadFactory() {
		/**
		 * 线程安全的int
		 */
		private final AtomicInteger integer = new AtomicInteger();

		@Override
		public Thread newThread(@NonNull Runnable runnable) {
			// getAndIncrement 相当于 i++
			return new Thread(runnable, "pool thread id: " + integer.getAndIncrement());
		}
	};

}
