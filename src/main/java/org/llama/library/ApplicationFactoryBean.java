/**  
 * @Title: ApplicationFactoryBean.java
 * @Package works.tonny.library
 * @author Tonny
 * @date 2011-10-31 下午2:15:45
 */
package org.llama.library;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 通过spring配置产生组件库
 * 
 * @ClassName: ApplicationFactoryBean
 * @Description:
 * @author Tonny
 * @date 2011-10-31 下午2:15:45
 * @version 1.0
 */
public class ApplicationFactoryBean implements FactoryBean, InitializingBean, DisposableBean {

	/**
	 * 配置文件路径
	 */
	private String config;

	private EnterpriseApplication application;

	/*
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
	}

	/*
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		ApplicationLoader loader = new XmlApplicationContextLoader(config);
		application = loader.getApplication();
	}

	/*
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Object getObject() throws Exception {
		return this.application;
	}

	/*
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class getObjectType() {
		return EnterpriseApplication.class;
	}

	/*
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

}
