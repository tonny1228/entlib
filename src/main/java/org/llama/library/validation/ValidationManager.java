/**  
 * @Title: ValidationManager.java
 * @Package works.tonny.library.validation
 * @author Tonny
 * @date 2011-10-28 上午9:18:17
 */
package org.llama.library.validation;

import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;

/**
 * 验证器组件管理
 * 
 * @ClassName: ValidationManager
 * @Description:
 * @author Tonny
 * @date 2011-10-28 上午9:18:17
 * @version 1.0
 */
public interface ValidationManager {
	/**
	 * 通过对象验证器验证对象验证结果
	 * 
	 * @Title: validate
	 * @param target 待验证的对象
	 * @return 验证结果
	 * @date 2011-10-28 上午9:26:04
	 * @author tonny
	 * @version 1.0
	 */
	ValidationResults validate(Object target);

	/**
	 * 通过规则验证器验证对象验证结果
	 * 
	 * @Title: validate
	 * @param name 验证规则
	 * @param target 待验证的对象
	 * @return 验证结果
	 * @date 2011-10-28 上午9:25:41
	 * @author tonny
	 * @version 1.0
	 */
	ValidationResults validate(String name, Object target);

	/**
	 * 根据规则名称获取验证器
	 * 
	 * @Title: getValidator
	 * @param name 规则名称
	 * @return 验证器
	 * @date 2011-10-31 下午2:05:17
	 * @author tonny
	 * @version 1.0
	 */
	Validator getValidator(String name);
}
