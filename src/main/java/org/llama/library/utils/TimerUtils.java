/**
 * 
 */
package org.llama.library.utils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 通用的定时器
 * 
 * @author 祥栋
 * @date 2014-3-13
 * @version 1.0.0
 */
public class TimerUtils {

	private static Timer timer;

	/**
	 * 创建定时器
	 */
	private static synchronized Timer getTimer() {
		if (timer == null) {
			newTimer();
		}
		return timer;
	}

	protected static void newTimer() {
		timer = new Timer("Entlib-common-timer", true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {

			}
		}, 0, 1000 * 3600 * 24);
	}

	/**
	 * @see java.util.Timer#schedule(java.lang.TimerTask,long)
	 */
	public static void schedule(TimerTask task, long delay) {
		try {
			getTimer().schedule(task, delay);
		} catch (IllegalStateException e) {
			timer = null;
			getTimer().schedule(task, delay);
		}
	}

	/**
	 * @see java.util.Timer#schedule(java.lang.TimerTask,long)
	 */
	public static void schedule(TimerTask task, Date time) {
		try {
			getTimer().schedule(task, time);
		} catch (IllegalStateException e) {
			timer = null;
			getTimer().schedule(task, time);
		}
	}

	/**
	 * @see java.util.Timer#schedule(TimerTask, long, long)
	 */
	public static void schedule(TimerTask task, long delay, long period) {
		try {
			getTimer().schedule(task, delay, period);
		} catch (IllegalStateException e) {
			timer = null;
			getTimer().schedule(task, delay, period);
		}
	}

	/**
	 * @see java.util.Timer#schedule(TimerTask, Date, long)
	 */
	public static void schedule(TimerTask task, Date firstTime, long period) {
		try {
			getTimer().schedule(task, firstTime, period);
		} catch (IllegalStateException e) {
			timer = null;
			getTimer().schedule(task, firstTime, period);
		}
	}

	/**
	 * @see java.util.Timer#scheduleAtFixedRate(TimerTask, long, long)
	 */
	public static void scheduleAtFixedRate(TimerTask task, long delay, long period) {
		try {
			getTimer().scheduleAtFixedRate(task, delay, period);
		} catch (IllegalStateException e) {
			timer = null;
			getTimer().schedule(task, delay);
			getTimer().scheduleAtFixedRate(task, delay, period);
		}
	}

	/**
	 * @see java.util.Timer#scheduleAtFixedRate(TimerTask, Date, long)
	 */
	public static void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
		try {
			getTimer().scheduleAtFixedRate(task, firstTime, period);
		} catch (IllegalStateException e) {
			timer = null;
			getTimer().scheduleAtFixedRate(task, firstTime, period);
		}
	}

	/**
	 * @see java.util.Timer#cancel()
	 */
	public static void cancel() {
		if (timer != null)
			timer.cancel();
	}

	/**
	 * 取消任务
	 * 
	 * @param task
	 */
	public static void cancelTask(TimerTask task) {
		task.cancel();
		timer.purge();
	}

}
