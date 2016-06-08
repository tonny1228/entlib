/**  
 * @Title: ExceptionUtils.java
 * @Package works.tonny.masp.utils
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 */
package org.llama.library.resources;

/**
 * 异常处理工具
 * 
 * @ClassName: ExceptionUtils
 * @Description:
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 * @version 1.0
 */
public class MessageUtils {
	public static String getResourceValue(String name) {
		return MessageResourcesImpl.getResourceValue(name);
	}
}
