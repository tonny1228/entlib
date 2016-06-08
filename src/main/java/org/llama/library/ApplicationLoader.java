package org.llama.library;

import org.llama.library.configuration.ConfigurationException;

/**
 * 企业组件应用载入器
 * 
 * @author tonny
 * 
 */
public interface ApplicationLoader {
	/**
	 * 载入系统应用
	 * 
	 * @return 应用配置
	 * @throws ConfigurationException 配置异常
	 */
	EnterpriseApplication getApplication() throws ConfigurationException;
}
