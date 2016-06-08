/**  
 * @Title: ExceptionUtils.java
 * @Package works.tonny.masp.utils
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 */
package org.llama.library.sqlmapping;

/**
 * 异常处理工具
 * 
 * @ClassName: ExceptionUtils
 * @Description:
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 * @version 1.0
 */
public class SQLMappingUtils {
	/*
	 * 通过名称获得配置 语句
	 * 
	 * @param name 包+查询的名称
	 * 
	 * @return 配置语句
	 */
	public static Query getQuery(String name) {
		try {
			return (Query) SQLMappingManagerImpl.mappings.get(name).clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
