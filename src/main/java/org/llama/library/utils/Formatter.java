/**  
 * @Title: Formatter.java
 * @Package works.tonny.library.utils
 * @author Tonny
 * @date 2012-4-18 下午3:16:58
 */
package org.llama.library.utils;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @ClassName: Formatter
 * @Description:
 * @author Tonny
 * @date 2012-4-18 下午3:16:58
 * @version 1.0
 */
public class Formatter {

	private static final String[] BYTE_UNITNAME = new String[] { "B", "KB", "MB", "GB", "TB" };

	/**
	 * 将字节转换为合适的显示
	 * 
	 * @param bytes
	 * @return
	 */
	public static String formatByte(long bytes) {
		final DecimalFormat formatter = new DecimalFormat("#,##0.##");
		int y = powy(bytes);
		return formatter.format(bytes / Math.pow(1024, y)) + BYTE_UNITNAME[y];
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return DateUtils.toString(date, pattern);
	}

	/**
	 * 计算字节数1024的指数整数
	 * 
	 * @Title: powy
	 * @param bytes
	 * @return
	 * @date 2012-4-18 下午3:46:17
	 * @author tonny
	 * @version 1.0
	 */
	private static int powy(double bytes) {
		return powy(bytes, 0);
	}

	/**
	 * 计算字节数1024的指数整数
	 * 
	 * @Title: powy
	 * @param bytes
	 *            字节数
	 * @param y
	 *            指数
	 * @return
	 * @date 2012-4-18 下午3:45:14
	 * @author tonny
	 * @version 1.0
	 */
	private static int powy(double bytes, int y) {
		if (bytes >= 1024) {
			return powy(bytes / 1024, y + 1);
		} else {
			return y;
		}
	}

	public static void main(String[] args) {
		System.out.println(formatByte(345));
		System.out.println(formatByte(1345));
		System.out.println(formatByte(1024 + 102));
		System.out.println(formatByte(102455));
		System.out.println(formatByte(3453435435435L));
		System.out.println(formatByte(34545));
		System.out.println(formatByte(345435435));
		System.out.println(formatByte(1024 * 1024 * 1024));
	}
}
