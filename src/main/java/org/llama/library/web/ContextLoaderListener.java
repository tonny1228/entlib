package org.llama.library.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.llama.library.ContextLoader;
import org.llama.library.EnterpriseApplication;
import org.llama.library.configuration.ConfigurationException;


public class ContextLoaderListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {

	}

	public void contextInitialized(ServletContextEvent event) {
		ContextLoader contextLoader = new ContextLoader(event.getServletContext());
		try {
			contextLoader.getApplication();
		} catch (ConfigurationException e) {
			throw new IllegalStateException(e);
		}

	}
}
