/**
 * 
 */
package org.llama.library.utils;

/**
 * 队列执行工具
 * 
 * @author tonny
 * @date 2015年5月26日
 * @version 1.0.0
 */
public interface QueueExecutor<T extends Runnable> {
	void addObject(T t);
}