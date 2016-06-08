package org.llama.library.cache;

/**
 * 获取缓存生效后的处理，一般用于缓存超期后的获取
 * 
 * @author tonny
 * 
 */
public interface Failover {
	/**
	 * 在缓存生效后获取原始数据
	 * 
	 * @param key 键值
	 * @return 数据
	 */
	Object getObject(String key);
}
