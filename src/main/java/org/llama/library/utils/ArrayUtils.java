package org.llama.library.utils;

/**
 * 数组工具
 * 
 * @author tonny
 * 
 */
public class ArrayUtils {
	/**
	 * 打印数组中的值
	 * 
	 * @param objs Object[]
	 */
	public static void println(Object[] objs) {
		System.out.println("***** Begin println the values of Object[] " + objs + "****");
		for (int i = 0; i < objs.length; i++) {
			System.out.println("value[" + i + "]=" + objs[i]);
		}
		System.out.println("***** End println the values of Object[] " + objs + "****");
	}
}
