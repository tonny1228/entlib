package org.llama.library.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.utils.ClassUtils;

/**
 * 通过ehcache进行数据缓存
 * 
 * @author tonny
 */
public class EHCache implements org.llama.library.cache.Cache {
	private CacheManager manager;
	private Cache cache;

	/**
	 * 初始化ehcache缓存
	 * 
	 * @param name the name of the cache
	 * @param maxElementsInMemory the maximum number of elements in memory,
	 *            before they are evicted
	 * @param memoryStoreEvictionPolicy one of LRU, LFU and FIFO. Optionally
	 *            null, in which case it will be set to LRU.
	 * @param overflowToDisk whether to use the disk store
	 * @param diskStorePath this parameter is ignored. CacheManager sets it
	 *            using setter injection.
	 * @param eternal whether the elements in the cache are eternal, i.e. never
	 *            expire
	 * @param timeToLiveSeconds the default amount of time to live for an
	 *            element from its creation date
	 * @param timeToIdleSeconds the default amount of time to live for an
	 *            element from its last accessed or modified date
	 * @param diskPersistent whether to persist the cache to disk between JVM
	 *            restarts
	 * @param diskExpiryThreadIntervalSeconds how often to run the disk store
	 *            expiry thread. A large number of 120 seconds plus is
	 *            recommended
	 */
	public EHCache(String name, String maxElementsInMemory, String memoryStoreEvictionPolicy, String overflowToDisk,
			String diskStorePath, String eternal, String timeToLiveSeconds, String timeToIdleSeconds,
			String diskPersistent, String diskExpiryThreadIntervalSeconds, String cacheEventListenerClass) {
		manager = CacheManager.getInstance();
		cache = new Cache(name, NumberUtils.toInt(maxElementsInMemory, 10000),
				MemoryStoreEvictionPolicy.fromString(memoryStoreEvictionPolicy),
				BooleanUtils.toBoolean(overflowToDisk), diskStorePath, BooleanUtils.toBoolean(eternal),
				NumberUtils.toLong(timeToLiveSeconds, 24 * 3600), NumberUtils.toLong(timeToIdleSeconds, 24 * 3600),
				BooleanUtils.toBoolean(diskPersistent), NumberUtils.toLong(diskExpiryThreadIntervalSeconds, 3600), null);
		if (StringUtils.isNotEmpty(cacheEventListenerClass)) {
			cache.getCacheEventNotificationService().registerListener(
					(CacheEventListener) ClassUtils.newInstance(cacheEventListenerClass));
		}
		manager.addCache(cache);
	}

	/**
	 * 通过ehcache读取配置文件初始化缓存，缓存名称为cache，配置文件为ehcache.xml
	 */
	public EHCache() {
		manager = CacheManager.create();
		cache = manager.getCache("cache");
	}

	/**
	 * 将数据对象缓存起来
	 * 
	 * @param key 数据的键值
	 * @param value 数据
	 */
	public void putInCache(String key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#putInCache(java.lang.String,
	 * java.lang.Object, int)
	 */
	public void putInCache(String key, Object value, int seconds) {
		putInCache(key, new TimedCache(value, seconds));
	}

	/**
	 * 通过键值查询数据
	 * 
	 * @param key 键值
	 * @return 缓存的数据
	 */
	public <T> T getFromCache(String key) {
		Element element = cache.get(key);
		if (element == null) {
			return null;
		}
		if (element.getObjectValue() instanceof TimedCache) {
			TimedCache c = (TimedCache) element.getObjectValue();
			if (System.currentTimeMillis() - c.getDate().getTime() > c.getCacheSeconds() * 1000) {
				cache.remove(key);
				c = null;
				element = null;
				return null;
			}
			return (T) c.getObject();
		}
		return (T) element.getObjectValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.llama.library.cache.Cache#getFromCache(java.lang.String, int)
	 */
	public <T> T getFromCache(String key, int seconds) {
		final T fromCache = getFromCache(key);
		putInCache(key, fromCache, seconds);
		return fromCache;
	}

	/**
	 * 移除附合键值的缓存数据
	 * 
	 * @param key 键值
	 */
	public void remove(String key) {
		cache.remove(key);
	}

	/**
	 * 清除所有的缓存数据
	 */
	public void removeAll() {
		cache.removeAll();
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
		return cache.getName();
	}

}
