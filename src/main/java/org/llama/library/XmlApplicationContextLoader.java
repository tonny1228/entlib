package org.llama.library;

import java.util.List;

import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.configuration.XMLConfig;


/**
 * 通过xml配置获得组件应用
 * 
 * @author tonny
 * 
 */
public class XmlApplicationContextLoader implements ApplicationLoader {
	private String config;

	private EnterpriseApplication application;

	public XmlApplicationContextLoader(String config) {
		this.config = config;
	}

	/**
	 * 读取唯一的应用配置
	 */
	public synchronized EnterpriseApplication getApplication() throws ConfigurationException {
		if (application == null) {
			createApplication();
		}
		return application;
	}

	/**
	 * 根据xml文件创建应用
	 * 
	 * @throws ConfigurationException
	 * @throws SQLMappingException
	 */
	private void createApplication() throws ConfigurationException {
		SimpleConfiguration configuration = new XMLConfig(config);
		List<String> root = configuration.rootKeys();
		// try {
		// application = (EnterpriseApplication)
		// ctClass.toClass().newInstance();
		// for (Iterator<String> iterator = fieldValue.keySet().iterator();
		// iterator.hasNext();) {
		// String key = (String) iterator.next();
		// BeanUtils.setProperty(application, key, fieldValue.get(key));
		// EnterpriseApplication.setComponent(key, fieldValue.get(key));
		// }
		// } catch (Exception e) {
		// throw new ConfigurationException(e);
		// }
	}
}
