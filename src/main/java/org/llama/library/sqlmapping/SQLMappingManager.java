package org.llama.library.sqlmapping;

/**
 * sql 配置文件载入工具，配置文件路径为classpath:SQLMapping.xml
 * 
 * @author tonny
 * 
 */
public interface SQLMappingManager {
	/**
	 * 通过名称获得配置 语句
	 * 
	 * @param name 包+查询的名称
	 * @return 配置语句
	 */
	Query getQuery(String name);
}
