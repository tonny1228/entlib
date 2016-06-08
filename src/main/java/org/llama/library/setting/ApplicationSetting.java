package org.llama.library.setting;

/**
 * 读取系统配置属性。配置文件为中的appSettings.key
 * 
 * @author tonny
 * 
 */
public interface ApplicationSetting {

	/**
	 * 通过键值读取配置信息
	 * 
	 * @param name 键值
	 * @return 值
	 */
	String getSetting(String name);

	/**
	 * 通过键值读取配置信息整形值
	 * 
	 * @param name 键值
	 * @return 整形值
	 */
	int getIntSetting(String name);

	/**
	 * 通过键值读取配置信息整形值
	 * 
	 * @param name 键值
	 * @return 浮点值
	 */
	float getFloatSetting(String name);
}
