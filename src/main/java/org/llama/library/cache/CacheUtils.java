package org.llama.library.cache;

import org.llama.library.EnterpriseApplication;

/**
 * 根据配置创建缓存框架
 * 
 * @author tonny
 * 
 */
public class CacheUtils {
	private static Cache cache;
	static {
		CacheManagerImpl manager = (CacheManagerImpl) EnterpriseApplication.getComponent("cache");
		cache = manager.getDefaultCache();
	}

	/**
	 * 将数据对象缓存起来
	 * 
	 * @param key 数据的键值
	 * @param value 数据
	 */
	public static void putInCache(String key, Object value) {
		cache.putInCache(key, value);
	}

	/**
	 * 通过键值查询数据
	 * 
	 * @param key 键值
	 * @return 缓存的数据
	 */
	public static <T> T getFromCache(String key) {
		return cache.getFromCache(key);
	}

	/**
	 * 通过键值查询数据，缓存数据失效后通过失败处理方法获取数据，并再次缓存
	 * 
	 * @param key key 键值
	 * @param failover 失败处理方法
	 * @return 缓存的数据
	 */
	public static <T> T getFromCache(String key, Failover failover) {
		return getFromCache(key, failover);
	}

	/**
	 * 移除附合键值的缓存数据
	 * 
	 * @param key 键值
	 */
	public static void remove(String key) {
		cache.remove(key);
	}

	/**
	 * 清除所有的缓存数据
	 */
	public static void removeAll() {
		cache.removeAll();
	}

}