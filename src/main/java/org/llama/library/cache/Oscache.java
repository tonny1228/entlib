package org.llama.library.cache;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;
import com.opensymphony.oscache.web.filter.ExpiresRefreshPolicy;

/**
 * 通过oscache缓存数据
 * 
 * @author tonny
 */
public class Oscache implements Cache {
	private GeneralCacheAdministrator cache;

	private String name;

	/**
	 * 初始化oscache缓存管理器
	 * 
	 * @param memory cache.memory 是否使用内存缓存
	 * @param path cache.path 缓存硬盘保存路径
	 * @param overflowToDisk cache.persistence.overflow.only 指定是否只有在内存不足的情况下才使用硬盘缓存
	 * @param capacity cache.capacity 缓存的最大数量 com.opensymphony.oscache.base.algorithm.LRUCache :
	 *            last in first out(最后插入的最先调用)。配置capacity时的默认选项。
	 *            com.opensymphony.oscache.base.algorithm.UnlimitedCache :
	 *            cache中的内容将永远不会被丢弃。如果capacity不指定值的话，它将被设为默认选项。
	 */
	public Oscache(String name, String memory, String path, String overflowToDisk, String capacity) {
		this.name = name;
		Properties properties = new Properties();
		properties.put("cache.memory", memory);
		if (StringUtils.isNotBlank(capacity)) {
			properties.put("cache.capacity", capacity);
		}
		properties.put("cache.blocking", "true");
		properties.put("cache.persistence.class",
				"com.opensymphony.oscache.plugins.diskpersistence.DiskPersistenceListener");
		properties.put("cache.path", path);
		properties.put("cache.persistence.overflow.only", overflowToDisk);
		properties.put("cache.unlimited.disk", "true");
		cache = new GeneralCacheAdministrator(properties);
	}

	/**
	 * 初始化oscache缓存管理器
	 */
	public Oscache() {
		cache = new GeneralCacheAdministrator();
	}

	/**
	 * 将数据对象缓存起来
	 * 
	 * @param key 数据的键值
	 * @param value 数据
	 */
	public void putInCache(String key, Object value) {
		cache.putInCache(key, value);
	}

	/**
	 * 将数据对象缓存起来
	 * 
	 * @param key 数据的键值
	 * @param value 数据
	 * @param expiresSeconds 数据超时时间
	 */
	public void putInCache(String key, Object value, int expiresSeconds) {
		cache.putInCache(key, new TimedCache(value, expiresSeconds), new ExpiresRefreshPolicy(expiresSeconds));
	}

	/**
	 * 通过键值查询数据
	 * 
	 * @param key 键值
	 * @return 缓存的数据
	 */
	public <T> T getFromCache(String key) {
		try {
			Object fromCache = cache.getFromCache(key);
			if (fromCache instanceof TimedCache) {
				TimedCache c = (TimedCache) fromCache;
				if (System.currentTimeMillis() - c.getDate().getTime() > c.getCacheSeconds() * 1000) {
					remove(key);
					c = null;
					fromCache = null;
					return null;
				}
				return (T) c.getObject();
			}
			return (T) fromCache;
		} catch (NeedsRefreshException e) {
			return null;
		}
	}

	/**
	 * 通过键值查询数据
	 * 
	 * @param key 键值
	 * @param expiresSeconds 数据超时时间，缓存超过错时间的返回空
	 * @return 缓存的数据
	 */
	public <T> T getFromCache(String key, int expiresSeconds) {
		try {
			return (T) cache.getFromCache(key, expiresSeconds);
		} catch (NeedsRefreshException e) {
			cache.cancelUpdate(key);
			return null;
		}
	}

	/**
	 * 移除附合键值的缓存数据
	 * 
	 * @param key 键值
	 */
	public void remove(String key) {
		cache.removeEntry(key);
	}

	/**
	 * 清除所有的缓存数据
	 */
	public void removeAll() {
		cache.flushAll();
	}

	/**
	 * 通过键值查询数据，缓存数据失效后通过失败处理方法获取数据，并再次缓存
	 * 
	 * @param key key 键值
	 * @param failover 失败处理方法
	 * @return 缓存的数据
	 */
	public <T> T getFromCache(String key, Failover failover) {
		T object = getFromCache(key);
		if (object == null) {
			object = (T) failover.getObject(key);
			putInCache(key, object);
		}
		return object;
	}

	public String getName() {
		return name;
	}

}
