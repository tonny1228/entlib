package org.llama.library.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;


/**
 * ip规则验证器
 * 
 * @author tonny
 * 
 */
public class IPPatternValidator extends Validator<String> {
	private Logger log = LogFactory.getLogger(getClass());

	/**
	 * 规则
	 */
	private String pattern;

	private String inputPattern;

	public IPPatternValidator(String messageTemplate) {
		super(messageTemplate);
	}

	/**
	 * 验证ip是否匹配规则。
	 * 
	 * @param ip 待验证的ip
	 * @param pattern 规则。
	 * 
	 *            <pre>
	 * ip任意位可以使用*代替，那么此位可以是任意数字，如:10.0.2.*，匹配10.0.2.1 - 10.0.2.254
	 * </pre>
	 * 
	 * @return 是否附和
	 */
	protected void doValidate(String target, ValidationResults validationResults) {
		log.debug("验证ip{0},{1}", target, inputPattern);
		if (!target.matches(pattern)) {
			addValidatorResult(target, validationResults, inputPattern, target);
		}
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		this.pattern = StringUtils.replace(this.pattern, "*", "\\d+");
		this.pattern = StringUtils.replace(this.pattern, ".", "\\.");
		this.inputPattern = pattern;
	}
}
