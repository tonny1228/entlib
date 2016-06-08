package org.llama.library.cache;

/**
 * 简单的缓存框架，用户将数据存放到缓存中，从缓存中获取缓存数据
 * 
 * @author tonny
 */
public interface Cache {

	/**
	 * 将数据对象缓存起来
	 * 
	 * @param key 数据的键值
	 * @param value 数据
	 */
	void putInCache(String key, Object value);

	/**
	 * 将数据对象缓存起来
	 * 
	 * @param key 数据的键值
	 * @param value 数据
	 * @param seconds 超时时间，超时后，数据为空
	 */
	void putInCache(String key, Object value, int seconds);

	/**
	 * 通过键值查询数据
	 * 
	 * @param key 键值
	 * @return 缓存的数据
	 */
	<T> T getFromCache(String key);

	/**
	 * 通过键值查询数据
	 * 
	 * @param key 键值
	 * @param seconds 重新设置超时秒
	 * @return 缓存的数据
	 */
	<T> T getFromCache(String key, int seconds);

	/**
	 * 通过键值查询数据，缓存数据失效后通过失败处理方法获取数据，并再次缓存
	 * 
	 * @param key key 键值
	 * @param failover 失败处理方法
	 * @return 缓存的数据
	 */
	<T> T getFromCache(String key, Failover failover);

	/**
	 * 移除附合键值的缓存数据
	 * 
	 * @param key 键值
	 */
	void remove(String key);

	/**
	 * 清除所有的缓存数据
	 */
	void removeAll();

	/**
	 * 获取缓存的名称
	 * 
	 * @return 缓存器的名称
	 */
	String getName();
}
