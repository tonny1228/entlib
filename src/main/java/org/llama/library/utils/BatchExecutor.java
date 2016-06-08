/**
 * 
 */
package org.llama.library.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 批量任务执行工具，用于批量处理任务，防止单一任务造成较大开销
 * 
 * @author 祥栋
 * @date 2014-3-13
 * @version 1.0.0
 */
public class BatchExecutor<T> {

	private Log log = LogFactory.getLog(getClass());

	/**
	 * 批量执行的大小周期，每达到此数时就执行任务
	 */
	private int size;

	/**
	 * 临时缓存任务队列
	 */
	private List<T> list;

	/**
	 * 任务执行器
	 */
	private Executeable<T> executor;

	/**
	 * 任务最大执行周期，超过此时间必定执行一次任务，即使队列不满
	 */
	private long delay;

	/**
	 * 最大周期执行任务
	 */
	private TimerTask executeTask;

	/**
	 * 临时队列
	 */
	private List<List<T>> tempList;

	/**
	 * @param size
	 *            批量执行的大小周期，每达到此数时就执行任务
	 * @param delay
	 *            任务最大执行周期，超过此时间必定执行一次任务，即使队列不满
	 * @param executor
	 *            任务执行器
	 */
	public BatchExecutor(int size, long delay, Executeable<T> executor) {
		super();
		this.size = size;
		this.delay = delay;
		this.executor = executor;
		list = new ArrayList<T>(size);
		tempList = new ArrayList<List<T>>(2);
	}

	/**
	 * 添加一个待执行的对象
	 * 
	 * @param t
	 */
	public synchronized void addObject(T t) {
		list.add(t);
		if (list.size() >= size) {
			execute();
		} else if (list.size() == 1) {
			executeTask = new TimerTask() {
				@Override
				public void run() {
					try {
						execute();
					} catch (Exception e) {
						log.error("批量任务执行出错", e);
					}
				}
			};
			TimerUtils.schedule(executeTask, delay);
		}
	}

	/**
	 * 批量执行任务
	 */
	protected void execute() {
		synchronized (list) {
			final List<T> tpm = getEmptyTemp();
			tpm.addAll(list);
			list.clear();
			TimerUtils.cancelTask(executeTask);
			TimerUtils.schedule(new TimerTask() {
				@Override
				public void run() {
					executor.execute(tpm);
					tpm.clear();
					if (tempList.size() < 5)
						tempList.add(tpm);
				}
			}, 0);
		}
	}

	/**
	 * 获取或创建一个空的临时队列
	 * 
	 * @return 空的队列
	 */
	protected List<T> getEmptyTemp() {
		if (tempList.isEmpty()) {
			return new ArrayList<T>(size);
		} else {
			List<T> temp = tempList.get(0);
			tempList.remove(0);
			return temp;
		}

	}

	/**
	 * 批量执行器
	 * 
	 * @author 祥栋
	 * @date 2014-3-13
	 * @version 1.0.0
	 * @param <T>
	 */
	public interface Executeable<T> {
		void execute(List<T> list);
	}

}
