package org.llama.library.validation.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.llama.library.log.LogFactory;
import org.llama.library.log.Logger;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;


/**
 * ip范围段验证器
 * 
 * @author tonny
 * 
 */
public class IPRangeValidator extends Validator<String> {

	private Logger log = LogFactory.getLogger(getClass());

	/**
	 * 规则
	 */
	private String pattern;

	/**
	 * ip前缀
	 */
	private String prefiex;

	private int start;

	private int end;

	public IPRangeValidator(String messageTemplate) {
		super(messageTemplate);

	}

	/**
	 * 验证ip是否匹配规则。
	 * 
	 * @param ip 待验证的ip
	 * @param pattern 规则。
	 * 
	 *            <pre>
	 * 可以是一ip范围：10.0.2.100-10.0.2.200
	 * </pre>
	 * 
	 * @return 是否附和
	 */
	protected void doValidate(String target, ValidationResults validationResults) {
		if (!prefiex.equals(StringUtils.substringBeforeLast(target, "."))) {
			addValidatorResult(target, validationResults, pattern, target);
			return;
		}
		int ipValue = NumberUtils.toInt(StringUtils.substringAfterLast(target, "."), 1);
		log.debug("验证ip{0},{1}", target, pattern);
		if (ipValue > end || ipValue < start) {
			addValidatorResult(target, validationResults, pattern, target);
		}
	}

	/**
	 * 设置规则
	 * 
	 * @param pattern 规则
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
		String from = StringUtils.substringBefore(pattern, "-");
		String to = StringUtils.substringAfter(pattern, "-");
		if (!StringUtils.substringBeforeLast(from, ".").equals(StringUtils.substringBeforeLast(to, "."))) {
			throw new IllegalArgumentException("两个ip段不在同一段");
		}
		prefiex = StringUtils.substringBeforeLast(from, ".");
		start = NumberUtils.toInt(StringUtils.substringAfterLast(from, "."), 1);
		end = NumberUtils.toInt(StringUtils.substringAfterLast(to, "."), 1);
	}

}
