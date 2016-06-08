package org.llama.library.configuration;

import java.util.List;

/**
 * 简单的配置文件管理器
 * 
 * @author tonny
 * 
 */
public interface SimpleConfiguration {

	/**
	 * 通过key获取配置字符串信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	String getString(String key);

	/**
	 * 通过key获取配置字符串信息
	 * 
	 * @param parentKey 父键
	 * @param subKey 子键
	 * @param index 第index个父键
	 * @return 配置信息
	 */
	String getString(String parentKey, String subKey, int index);

	/**
	 * 通过key获取配置整型信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	int getInt(String key);

	/**
	 * 通过key获取配置float型信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	float getFloat(String key);

	/**
	 * 匹配key的配置的数量
	 * 
	 * @param key 关键字
	 * @return 匹配key的配置的数量
	 */
	int size(String key);

	/**
	 * 通过key获取配置字符串集合信息
	 * 
	 * @param key 关键字
	 * @return 配置信息
	 */
	List<String> getList(String key);

	/**
	 * 设置为key的值
	 * 
	 * @param key 关键字
	 * @param value 值
	 */
	void setProperty(String key, String value);

	/**
	 * 列出此附合此键的所有子键
	 * 
	 * @param key 键
	 * @return 子键集合
	 */
	List<String> keys(String key);

	/**
	 * 列出此所有附合此键根键
	 * 
	 * @param key 键
	 * @return 子键集合
	 */
	List<String> rootKeys();

}
