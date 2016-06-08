package org.llama.library.cache;

import java.util.HashMap;
import java.util.Map;

import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.setting.ApplicationSettingImpl;
import org.llama.library.utils.ClassUtils;
import org.llama.library.utils.ELHelper;

/**
 * 根据配置创建缓存框架
 * 
 * @author tonny
 * 
 */
public class CacheManagerImpl extends AbstractComponentContainer implements CacheManager {
	private Map<String, Cache> mappingedCache = null;

	private Cache defaultCache = null;

	public CacheManagerImpl(SimpleConfiguration configuration) {
		super(configuration);
		mappingedCache = new HashMap<String, Cache>();
		init();
	}

	/**
	 * 通过配置文件enterprise-library.xml或类创建缓存框架。 通过cache中的子节点ehcache或oscache创建缓存对象。
	 * 
	 * @return 缓存框架
	 * @throws CacheConfigurationException 配置缓存异常
	 */
	public void init() throws ConfigurationException {
		int ehsize = configuration.size("cache.ehcache");
		for (int i = 0; i < ehsize; i++) {
			Cache cache = new EHCache(configuration.getString("cache.ehcache", "name", i), configuration.getString(
					"cache.ehcache", "maxElementsInMemory", i), configuration.getString("cache.ehcache",
					"memoryStoreEvictionPolicy", i), configuration.getString("cache.ehcache", "overflowToDisk", i),
					configuration.getString("cache.ehcache", "diskStorePath", i), configuration.getString(
							"cache.ehcache", "eternal", i), configuration.getString("cache.ehcache",
							"timeToLiveSeconds", i), configuration.getString("cache.ehcache", "timeToIdleSeconds", i),
					configuration.getString("cache.ehcache", "diskPersistent", i), configuration.getString(
							"cache.ehcache", "diskExpiryThreadIntervalSeconds", i), configuration.getString(
							"cache.ehcache", "cacheEventListener", i));
			mappingedCache.put(cache.getName(), cache);
			if (defaultCache == null) {
				defaultCache = cache;
			}
			if ("true".equalsIgnoreCase(configuration.getString("cache.ehcache", "default", i))) {
				defaultCache = cache;
			}
		}

		int ossize = configuration.size("cache.oscache");
		for (int i = 0; i < ossize; i++) {
			Cache cache = new Oscache(configuration.getString("cache.oscache", "name", i), configuration.getString(
					"cache.oscache", "memory", i), configuration.getString("cache.oscache", "path", i),
					configuration.getString("cache.oscache", "overflowToDisk", i), configuration.getString(
							"cache.oscache", "capacity", i));
			mappingedCache.put(cache.getName(), cache);
			if (defaultCache == null) {
				defaultCache = cache;
			}
			if ("true".equalsIgnoreCase(configuration.getString("cache.oscache", "default", i))) {
				defaultCache = cache;
			}
		}

		int memsize = configuration.size("cache.memcached");
		for (int i = 0; i < memsize; i++) {
			String address = ELHelper.execute(configuration.getString("cache.memcached", "address", i),
					ApplicationSettingImpl.settings);
			Cache cache = new MemcachedCache(configuration.getString("cache.memcached", "name", i), address);
			mappingedCache.put(cache.getName(), cache);
			if (defaultCache == null) {
				defaultCache = cache;
			}
			if ("true".equalsIgnoreCase(configuration.getString("cache.memcached", "default", i))) {
				defaultCache = cache;
			}
		}
	}

	/**
	 * 检查包中是否包含ehcache或oscache的包，并且含有配置文件，返回相应的缓存对象
	 * 
	 * @return 缓存框架
	 * @throws CacheConfigurationException 配置缓存异常
	 */
	public static Cache create() throws CacheConfigurationException {
		if (ClassUtils.isClassExist("net.sf.ehcache.CacheManager")
				&& Thread.currentThread().getContextClassLoader().getResource("ehcache.xml") != null) {
			return new EHCache();
		}

		if (ClassUtils.isClassExist("com.opensymphony.oscache.general.GeneralCacheAdministrator")
				&& Thread.currentThread().getContextClassLoader().getResource("oscache.properties") != null) {
			return new Oscache();
		}
		return null;
	}

	public Cache getCache(String name) {
		return mappingedCache.get(name);
	}

	/**
	 * @param name
	 * @return
	 * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
	 */
	@Override
	public Object get(String name) {
		return mappingedCache.get(name);
	}

	public Cache getDefaultCache() {
		return defaultCache;
	}

}
