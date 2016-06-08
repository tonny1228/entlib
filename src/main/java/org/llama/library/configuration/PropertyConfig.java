package org.llama.library.configuration;

import org.apache.commons.configuration.PropertiesConfiguration;

public class PropertyConfig {
	public static void config() throws Exception {
		PropertiesConfiguration config = new PropertiesConfiguration("config.properties");
		config.setDefaultListDelimiter('/');
		config.setDelimiterParsingDisabled(true);
		System.out.println(config.getList("a.b").get(0).getClass());
	}
}
