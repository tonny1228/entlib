/**
 * 
 */
package org.llama.library.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author tonny
 * @date 2015年5月26日
 * @version 1.0.0
 */
public class ThreadPoolQueueExecutor<T extends Runnable> implements QueueExecutor<T> {

	/**
	 * 核心线程大小
	 */
	private int minThreads;

	/**
	 * 最大线程大小
	 */
	private int maxThreads;
	/**
	 * 周期内最大处理能力，如1分钟最多处理100条数据，否则会不稳定等。
	 */
	private long capacityInPeriod;
	/**
	 * 处理能力周期，单位毫秒。
	 */
	private long period;

	/**
	 * 每个任务延迟时间，毫秒
	 */
	private long delay;

	private LinkedBlockingQueue<T> workQueue;

	private ScheduledExecutorService threadPool;

	/**
	 * 
	 */
	public ThreadPoolQueueExecutor(int minThreads) {
		this(minThreads, 0, 0);
	}

	/**
	 * 
	 */
	public ThreadPoolQueueExecutor(int minThreads, long capacityInPeriod, long period) {
		this.minThreads = minThreads;
		this.capacityInPeriod = capacityInPeriod;
		this.period = period;
		threadPool = Executors.newScheduledThreadPool(minThreads);
		workQueue = new LinkedBlockingQueue<T>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.utils.QueueExecutor#addObject(java.lang.Object)
	 */
	public void addObject(T t) {
		workQueue.add(t);
		execute();
	}

	/**
	 * 执行
	 */
	protected void execute() {
		while (!workQueue.isEmpty()) {
			try {
				threadPool.schedule(workQueue.take(), delay, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}