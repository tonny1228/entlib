/**  
 * @Title: ExceptionUtils.java
 * @Package works.tonny.masp.utils
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 */
package org.llama.library.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.llama.library.EnterpriseApplication;

/**
 * 异常处理工具
 * 
 * @ClassName: ExceptionUtils
 * @Description:
 * @author Tonny
 * @date 2011-11-9 下午2:27:19
 * @version 1.0
 */
public class ExceptionUtils {

	/**
	 * 
	 */
	private static final String NEW_LINE = "\r\n";
	/**
	 * 异常处理框架
	 */
	private static ExceptionManager exceptionManager = (ExceptionManager) EnterpriseApplication
			.getComponent("exception");

	/**
	 * 处理异常
	 * 
	 * @Title: handler
	 * @param e
	 *            异常
	 * @param policy
	 *            策略
	 * @param message
	 *            消息
	 * @param <T>
	 *            异常类型
	 * @return 封装的异常
	 * @date 2011-11-9 下午2:38:23
	 * @author tonny
	 * @version 1.0
	 */
	public static <T extends Exception> T handle(Exception e, String policy, String... message) {
		return exceptionManager.process(e, policy, message);
	}

	/**
	 * 输出错误信息信息
	 * 
	 * @param throwable
	 * @return
	 */
	public static String getExceptionDetail(Throwable throwable) {
		// StringBuilder builder = new StringBuilder();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		throwable.printStackTrace(pout);
		String message = new String(out.toByteArray());
		pout.close();
		try {
			out.close();
		} catch (Exception e) {
		}
		return message;
	}

	private static void appendDetail(StringBuilder builder, Throwable throwable) {
		StackTraceElement[] es = throwable.getStackTrace();
		for (int i = 0; i < es.length; i++) {
			builder.append(es[i]).append(NEW_LINE);
		}
		Throwable cause = throwable.getCause();
		if (cause != null) {
			appendDetail(builder, cause);
		}
	}

	public ExceptionManager getExceptionManager() {
		return ExceptionUtils.exceptionManager;
	}

	public void setExceptionManager(ExceptionManager exceptionManager) {
		ExceptionUtils.exceptionManager = exceptionManager;
	}

}
