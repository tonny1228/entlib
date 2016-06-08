/**
 * 
 */
package org.llama.library.utils;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 线程数据缓存
 * 
 * @author 祥栋
 * @date 2012-11-15
 * @version 1.0.0
 */
public class ThreadLocalMap {

	private static ThreadLocalMap map = new ThreadLocalMap();
	private static final ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal<Map<Object, Object>>();

	/**
	 * 
	 */
	protected ThreadLocalMap() {

	}

	public static ThreadLocalMap getInstance() {
		return map;
	}

	public void init() {
		if (threadLocal.get() == null) {
			threadLocal.set(new HashMap<Object, Object>());
		} else {
			threadLocal.get().clear();
		}
	}

	public void putObjectSoftly(String key, Object value) {
		initMap();
		threadLocal.get().put(key, new SoftReference<Object>(value));
	}

	/**
	 * 
	 */
	private void initMap() {
		if (threadLocal.get() == null) {
			threadLocal.set(new HashMap<Object, Object>());
		}
	}

	public void putObject(Object key, Object value) {
		initMap();
		threadLocal.get().put(key, value);
	}

	public <T> T getObject(Object key) {
		initMap();
		Object object = threadLocal.get().get(key);
		if (object != null && object instanceof SoftReference) {
			return (T) ((SoftReference) object).get();
		}
		return (T) object;
	}

}
