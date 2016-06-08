package org.llama.library;

import javax.servlet.ServletContext;

import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.configuration.XMLConfig;


/**
 * 根据web环境配置创建应用
 * 
 * @author tonny
 * 
 */
public class ContextLoader implements ApplicationLoader {

	public static final String ENTER_PRISE_APPLICATION = "EnterPriseApplication";
	private ServletContext context;

	private EnterpriseApplication application;

	public ContextLoader(ServletContext context) {
		this.context = context;
	}

	public synchronized EnterpriseApplication getApplication() throws ConfigurationException {
		if (application == null) {
			createApplication();
		}
		return application;
	}

	private void createApplication() throws ConfigurationException {
		String config = context.getInitParameter("entlib-ConfigLocation");
		application = new XmlApplicationContextLoader(config).getApplication();
		context.setAttribute(ENTER_PRISE_APPLICATION, application);
	}

}
