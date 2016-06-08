package org.llama.library.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;
import org.llama.library.utils.Assert;
import org.llama.library.utils.ClassUtils;
import org.llama.library.validation.ClassValidator;
import org.llama.library.validation.ValidationManager;
import org.llama.library.validation.ValidationResults;
import org.llama.library.validation.Validator;
import org.llama.library.validation.validator.CompositeValidator;
import org.llama.library.validation.validator.DateConverter;
import org.llama.library.validation.validator.RangeBoundaryType;
import org.llama.library.validation.validator.RangeBoundaryTypeConverter;


/**
 * 验证器容器
 * 
 * @ClassName: ValidatorContainer
 * @Description: 创建和存储验证器组件
 * @author Tonny
 * @date 2011-10-28 上午9:11:12
 * @version 1.0
 */
public class ValidationManagerImpl extends AbstractComponentContainer implements ValidationManager {
	/**
	 * 名称验证器组件
	 */
	private Map<String, Validator> validators = new HashMap<String, Validator>();

	/**
	 * 对象验证器组件
	 */
	private Map<String, ClassValidator> classValidator = new HashMap<String, ClassValidator>();

	/**
	 * <p>
	 * </p>
	 * 
	 * @param configuration
	 */
	public ValidationManagerImpl(SimpleConfiguration configuration) {
		super(configuration);
		init();
	}

	/**
	 * 通过配置载入验证器
	 */
	@Override
	public void init() throws ConfigurationException {
		ConvertUtils.register(new RangeBoundaryTypeConverter(), RangeBoundaryType.class);
		ConvertUtils.register(new DateConverter(), Date.class);
		int count = configuration.size("validation.validators.validator");
		try {
			for (int i = 0; i < count; i++) {
				String name = configuration.getString("validation.validators.validator", "name", i);
				Validator validator = buildValidator(configuration, "validation.validators.validator", i);
				validators.put(name, validator);
			}
		} catch (Exception e) {
			throw new ConfigurationException("载入验证器失败", e);
		}
		count = configuration.size("validation.class");
		try {
			for (int i = 0; i < count; i++) {
				String className = configuration.getString("validation.class", "name", i);
				ClassValidator clzValidator = new ClassValidator();
				classValidator.put(className, clzValidator);

				int fieldCount = configuration.size("validation.class(" + i + ").field");
				for (int j = 0; j < fieldCount; j++) {
					String field = configuration.getString("validation.class(" + i + ").field", "name", j);
					Validator validator = buildValidator(configuration, "validation.class(" + i + ").field(" + j
							+ ").validator", 0);
					clzValidator.setFieldValidator(field, validator);
				}
			}
		} catch (Exception e) {
			throw new ConfigurationException("载入验证器失败", e);
		}
	}

	/**
	 * 根据配置创建验证器
	 * 
	 * @Title: buildValidator
	 * @param configuration
	 * @param parentKey
	 * @param i
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @date 2011-10-28 上午9:27:52
	 * @author tonny
	 * @version 1.0
	 */
	protected Validator buildValidator(SimpleConfiguration configuration, String parentKey, int i)
			throws IllegalAccessException, InvocationTargetException {
		int subCount = configuration.size(parentKey + "(" + i + ").validator");
		if (subCount > 0) {
			String clz = configuration.getString(parentKey, "class", i);
			CompositeValidator validator = ClassUtils.newInstance(clz, new Class[] { Validator[].class },
					new Object[] { new Validator[0] });
			for (int j = 0; j < subCount; j++) {
				Validator sub = buildValidator(configuration, parentKey + "(" + i + ").validator", j);
				validator.addValidator(sub);
			}
			return validator;
		}

		String clz = configuration.getString(parentKey, "class", i);
		String messageTemplate = configuration.getString(parentKey, "messageTemplate", i);
		Validator<Object> validator = ClassUtils.newInstance(clz, messageTemplate);
		Map<String, String> properties = new HashMap<String, String>();
		List<String> keys = configuration.keys(parentKey);
		keys.remove("name");
		keys.remove("messageTemplate");
		keys.remove("class");
		for (int j = 0; j < keys.size(); j++) {
			properties.put(keys.get(j), configuration.getString(parentKey, keys.get(j), i));
		}
		BeanUtils.populate(validator, properties);
		return validator;
	}

	public Validator getValidator(String name) {
		return validators.get(name);
	}

	public ValidationResults validate(Object target) {
		String className = target.getClass().getName();
		return classValidator.get(className).validate(target);
	}

	/*
	 * @see
	 * works.tonny.library.validation.ValidationManager#validate(java.lang.String,
	 * java.lang.Object)
	 */
	public ValidationResults validate(String name, Object target) {
		Validator validator = validators.get(name);
		Assert.notNull(validator);
		ValidationResults results = new ValidationResults();
		validator.validate(target, results);
		return results;
	}

	/**
	 * @param name
	 * @return
	 * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
	 */
	@Override
	public Object get(String name) {
		return getValidator(name);
	}

}
