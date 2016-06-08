package org.llama.library.cache;

/**
 * 根据配置创建缓存框架
 * 
 * @author tonny
 * 
 */
public interface CacheManager {
	/**
	 * 根据缓存名称获取配置的缓存管理器
	 * 
	 * @Title: getCache
	 * @param name 配置的缓存名称
	 * @return 缓存管理器
	 * @date 2011-10-31 下午1:56:53
	 * @author tonny
	 * @version 1.0
	 */
	Cache getCache(String name);
}
