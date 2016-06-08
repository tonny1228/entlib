package org.llama.library;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;

/**
 * 组件创建抽象工厂类，各组件继承此类实现工厂类
 * 
 * @author tonny
 */
public abstract class AbstractComponentContainer {

	protected Log log = LogFactory.getLog(getClass());
	/**
	 * 配置
	 */
	protected SimpleConfiguration configuration;

	public AbstractComponentContainer(SimpleConfiguration configuration) {
		this.configuration = configuration;
	}

	public abstract void init() throws ConfigurationException;

	/**
	 * 设置配置
	 * 
	 * @Title: setConfiguration
	 * @param configuration 配置信息
	 * @date 2011-10-28 上午9:36:44
	 * @author tonny
	 * @version 1.0
	 */
	public void setConfiguration(SimpleConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * 通过名字获取组件
	 * 
	 * @Title: get
	 * @param name
	 * @return 组件
	 * @date 2012-4-17 下午3:55:43
	 * @author tonny
	 * @version 1.0
	 */
	public abstract Object get(String name);
}
