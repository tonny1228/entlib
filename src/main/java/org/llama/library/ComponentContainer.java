package org.llama.library;

/**
 * 组件管理类，用户组件初始化配置，获取部署的应用
 * 
 * @author tonny
 * 
 */

public interface ComponentContainer<T> {
	/**
	 * 通过部署的名称获取组件
	 * 
	 * @param name 组件名
	 * @return 组件
	 */
	T getComponent(String name);

}
