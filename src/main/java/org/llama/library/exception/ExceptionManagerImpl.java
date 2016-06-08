package org.llama.library.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.llama.library.AbstractComponentContainer;
import org.llama.library.configuration.ConfigurationException;
import org.llama.library.configuration.SimpleConfiguration;


/**
 * 异常处理器，通过异常处理策略进行异常处理，是否返回新的异常
 * 
 * @author tonny
 * @version 1.0
 * @created 23-八月-2011 9:01:10
 */
public class ExceptionManagerImpl extends AbstractComponentContainer implements ExceptionManager {
	/**
	 * <p>
	 * </p>
	 * 
	 * @param configuration
	 */
	public ExceptionManagerImpl(SimpleConfiguration configuration) {
		super(configuration);
		init();
	}

	/**
	 * 所有的策略
	 */
	public Map<String, ExceptionPolicy> exceptionPolicies;

	/**
	 * 通过异常处理策略进行异常处理，是否返回新的异常
	 * 
	 * @param e 异常
	 * @param policy
	 * @param args 异常处理的参数
	 */
	public <T extends Exception> T process(Exception e, String policy, Object... args) {
		ExceptionPolicy exceptionPolicy = exceptionPolicies.get(policy);
		if (exceptionPolicy == null) {
			throw new ExceptionHandlingException("策略没有找到:" + policy);
		}
		return exceptionPolicy.handleException(e, args);
	}

	/*
	 * @see works.tonny.library.AbstractComponentContainer#init()
	 */
	@Override
	public void init() throws ConfigurationException {
		exceptionPolicies = createPolicies();
	}

	/**
	 * 创建所有策略
	 * 
	 * @return 所有策略
	 * @throws org.llama.library.configuration.ConfigurationException
	 * @throws ConfigurationException
	 */
	protected Map<String, ExceptionPolicy> createPolicies() throws ConfigurationException {
		Map<String, ExceptionPolicy> policies = new HashMap<String, ExceptionPolicy>();
		int policyCount = configuration.size("exceptionHandling.policy");
		for (int i = 0; i < policyCount; i++) {
			int typeCount = configuration.size("exceptionHandling.policy(" + i + ").type");
			List<ExceptionPolicyEntry> entries = new ArrayList<ExceptionPolicyEntry>();
			for (int j = 0; j < typeCount; j++) {
				ExceptionPolicyEntry entry = createEntry(configuration, "exceptionHandling.policy(" + i + ").type("
						+ j + ")");
				entries.add(entry);
			}
			String name = configuration.getString("exceptionHandling.policy(" + i + ").name");
			ExceptionPolicy exceptionPolicy = new ExceptionPolicyImpl(name, entries);
			policies.put(name, exceptionPolicy);
		}
		return policies;
	}

	/**
	 * 创建策略条目
	 * 
	 * @param configuration 配置
	 * @param type 条目配置
	 * @return 条目
	 */
	protected ExceptionPolicyEntry createEntry(SimpleConfiguration configuration, String type) {
		int handlerCount = configuration.size(type + ".handler");
		List<ExceptionHandler> handlers = new ArrayList<ExceptionHandler>();
		for (int i = 0; i < handlerCount; i++) {
			ExceptionHandler exceptionHandler = createHandler(configuration, type + ".handler(" + i + ")");
			handlers.add(exceptionHandler);
		}

		String clazz = configuration.getString(type + ".class");
		String typeName = configuration.getString(type + ".type");
		ExceptionPolicyEntry entry = null;
		try {
			entry = new ExceptionPolicyEntry(handlers, (Class<? extends Exception>) Class.forName(clazz),
					HandlerType.parse(typeName));

		} catch (ClassNotFoundException e) {
			throw new ExceptionHandlingException(e);
		}
		return entry;
	}

	/**
	 * 创建handler
	 * 
	 * @param configuration 配置信息
	 * @param handler handler配置
	 */
	protected ExceptionHandler createHandler(SimpleConfiguration configuration, String handler) {
		try {
			String clazz = configuration.getString(handler + ".class");
			ExceptionHandler exceptionHandler = (ExceptionHandler) Class.forName(clazz).newInstance();
			List<String> keys = configuration.keys(handler);
			for (int i = 0; i < keys.size(); i++) {
				String node = configuration.getString(handler + "." + keys.get(i));
				BeanUtils.setProperty(exceptionHandler, keys.get(i), node);
			}
			return exceptionHandler;
		} catch (Exception e) {
			throw new ExceptionHandlingException(e);
		}
	}

	/**
	 * @param name
	 * @return
	 * @see org.llama.library.AbstractComponentContainer#get(java.lang.String)
	 */
	@Override
	public Object get(String name) {
		return this;
	}

}