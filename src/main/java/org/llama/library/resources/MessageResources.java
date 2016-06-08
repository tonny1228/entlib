package org.llama.library.resources;

import java.util.ResourceBundle;

/**
 * 资源管理器，用户国际化
 * 
 * @author tonny
 * 
 */
public interface MessageResources {

	/**
	 * 读取资源信息
	 * 
	 * @Title: getResource
	 * @param name 资源键
	 * @return 资源信息
	 * @date 2011-10-31 下午1:39:39
	 * @author tonny
	 * @version 1.0
	 */
	ResourceBundle getResource(String name);

}
