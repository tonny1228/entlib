package org.llama.library;

import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.configuration.XMLConfig;

/**
 * 系统配置器，获取系统配置
 * 
 * @author tonny
 * 
 */
public class ApplicationConfiguration {
	private static SimpleConfiguration configuration;

	/**
	 * 获取系统配置
	 * 
	 * @return 系统配置
	 * @throws ConfigurationException 读取配置异常
	 */
	public static synchronized SimpleConfiguration getConfiguration(String xmlFile) throws ConfigurationException {
		if (configuration == null) {
			configuration = new XMLConfig(xmlFile);
		}
		return configuration;
	}

}
