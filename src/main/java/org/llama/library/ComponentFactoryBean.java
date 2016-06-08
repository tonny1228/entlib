/**  
 * @Title: ComponentFactoryBean.java
 * @Package works.tonny.library
 * @author Tonny
 * @date 2012-4-17 下午3:32:51
 */
package org.llama.library;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * spring配置组件产生工厂类，生产各组件
 * 
 * @ClassName: ComponentFactoryBean
 * @Description: 组件名称以管理器名称.组件名称方式，如cache.ehcache,cryptography.md5
 * @author Tonny
 * @date 2012-4-17 下午3:32:51
 * @version 1.0
 */
public class ComponentFactoryBean implements FactoryBean, InitializingBean, DisposableBean {

	/**
	 * 组件名称，以管理器名称.组件名称方式，
	 */
	private String component;

	/**
	 * 组件名
	 */
	private String name;

	/**
	 * @throws Exception
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
	}

	/**
	 * @throws Exception
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * @return
	 * @throws Exception
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	public Object getObject() {
		AbstractComponentContainer container = null;
		if (component != null) {
			container = EnterpriseApplication.getComponent(component);
		}
		if (name != null) {
			return container.get(name);
		} else {
			return container;
		}
	}

	/**
	 * @return
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	public Class getObjectType() {
		return getObject().getClass();
	}

	/**
	 * @return
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	public boolean isSingleton() {
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

}
