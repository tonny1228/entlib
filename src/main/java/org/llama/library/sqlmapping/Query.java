package org.llama.library.sqlmapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.llama.library.sqlmapping.QueryParameters.Parameter;
import org.llama.library.utils.RegularUtils;

/**
 * sql查询，通过追加、参数拼成完整的sql语句
 * 
 * @author tonny
 */
public class Query implements Cloneable {

	/**
	 * 配置名称
	 */
	String name;

	/**
	 * 初始语句
	 */
	String sql;

	Map<String, String> mapedAppender;

	private List<Object> parameters;

	private List<Object> values;

	private List<String> paramNames;

	private String gSQL;

	private List<Object> gParames;

	private List<String> gNames;

	/**
	 * 
	 */
	public Query() {
	}

	public String getName() {
		return name;
	}

	/**
	 * 追加语句
	 * 
	 * @param appender 要追加语句的名称
	 * @return 当前配置
	 */
	public Query append(String... appender) {
		if (parameters == null) {
			parameters = new ArrayList<Object>();
		}
		for (String name : appender) {
			parameters.add(name);
		}
		return this;
	}

	/**
	 * 追加带参数的语句
	 * 
	 * @param appender 要追加语句的名称
	 * @param paramter 要追加语句使用的参数
	 * @return 当前配置
	 */
	public Query append(String appender, Object[] paramter) {
		if (parameters == null) {
			parameters = new ArrayList<Object>();
		}
		// append(appender);
		// addParamter(paramter);
		// // queryParameters.addParameter(appender, paramter);
		parameters.add(new Parameter(appender, paramter));
		return this;
	}

	/**
	 * 追加带参数的语句
	 * 
	 * @param mapedAppender 要追加语句的名称
	 * @param paramter 要追加语句使用的参数
	 * @return 当前配置
	 */
	public Query append(QueryParameters param) {
		if (parameters == null) {
			parameters = new ArrayList<Object>();
		}
		List<Parameter> params = param.getParameters();
		for (int i = 0; i < params.size(); i++) {
			// append(params.get(i).getName());
			// if (params.get(i) instanceof RangeParameter) {
			// addParamter(((RangeParameter) params.get(i)).getLower());
			// addParamter(((RangeParameter) params.get(i)).getUpper());
			// } else {
			// addParamter(params.get(i).getValue());
			// }
			// queryParameters.getParameters().add(params.get(i));
			parameters.add(params.get(i));
		}
		return this;
	}

	/**
	 * 根据条件判断是否追加语句，true时追加
	 * 
	 * @param appender 要追加语句的名称
	 * @param condition 要判断的条件，当真时追加
	 * @return 当前配置
	 */
	public Query append(boolean condition, String appender) {
		if (!condition)
			return this;

		append(appender);
		return this;
	}

	/**
	 * 根据条件判断是否追加语句和参数
	 * 
	 * @param paramter 要追加语句使用的参数
	 * @param appender 要追加语句的名称
	 * @param condition 要判断的条件，当真时追加
	 * @return 当前配置
	 */
	public Query append(boolean condition, String appender, Object[] paramter) {
		if (!condition)
			return this;
		append(appender, paramter);
		return this;
	}

	/**
	 * 添加语句使用的参数
	 * 
	 * @param paramter 要追加语句使用的参数
	 * @return 当前配置
	 */
	public Query addParamter(Object paramter) {
		if (values == null) {
			values = new ArrayList<Object>();
		}
		// parameters.add(paramter);
		values.add(paramter);
		return this;
	}

	/**
	 * 添加语句使用的参数
	 * 
	 * @param paramter 要追加语句使用的参数
	 * @return 当前配置
	 */
	public Query addParamter(Object[] paramter) {
		for (int i = 0; i < paramter.length; i++) {
			addParamter(paramter[i]);
		}
		return this;
	}

	public void addParamNames(String... paramNames) {
		if (paramNames == null) {
			return;
		}
		if (this.paramNames == null) {
			this.paramNames = new ArrayList<String>();
		}
		for (String paramName : paramNames) {
			this.paramNames.add(paramName);
		}
	}

	/**
	 * 当前查询使用的参数
	 * 
	 * @return
	 */
	public Object[] parameters() {
		return gParames.toArray();
	}

	/**
	 * 构建
	 */
	public void build() {
		if (containsParamNames() && paramNames.size() != values.size()) {
			throw new IllegalArgumentException("参数名称与参数个数不一致");
		}
		StringBuffer buffer = new StringBuffer();
		gNames = new ArrayList<String>();
		gParames = new ArrayList<Object>();
		int valIndex = 0;
		// if (parameters == null) {
		// parameters = new ArrayList<Object>();
		// }
		// parameters.add(0, sql);
		valIndex = appendSQL(buffer, sql, name, valIndex);
		for (int m = 0; parameters != null && m < parameters.size(); m++) {
			Object p = parameters.get(m);
			buffer.append(" ");
			if (p instanceof String) {
				String name = (String) p;
				String appender = getAppender(name);
				valIndex = appendSQL(buffer, appender, name, valIndex);
			} else if (p instanceof Parameter) {
				String name = ((Parameter) p).getName();
				String appender = getAppender(name);
				Object[] value = ((Parameter) p).getValue();
				if (appender.indexOf("?") >= 0) {
					String[] split = StringUtils.splitByWholeSeparator(appender, "?");
					for (int i = 0; i < split.length - 1; i++) {
						String pName = name + "_" + i;
						buffer.append(split[i]).append(":").append(pName);
						gNames.add(pName);
						gParames.add(value[i]);
					}
					buffer.append(split[split.length - 1]);
				} else if (appender.matches(".*:\\s*\\w+.*")) {
					String[] pNames = RegularUtils.matchedString(appender, ":\\s*(\\w+)");
					for (int i = 0; i < pNames.length; i++) {
						gNames.add(pNames[i]);
						gParames.add(value[i]);
					}
					buffer.append(appender);
				} else {
					buffer.append(appender);
				}
			}
		}
		this.gSQL = buffer.toString();
	}

	/**
	 * @param sqlBuffer
	 * @param appender
	 * @param name
	 * @param valIndex
	 * @return
	 */
	public int appendSQL(StringBuffer sqlBuffer, String appender, String name, int valIndex) {
		if (appender.indexOf("?") >= 0) {
			if (containsParamNames()) {
				throw new IllegalArgumentException("appender中" + name + "在指定参数名称时不能使用'?'");
			}
			String[] split = StringUtils.splitByWholeSeparator(appender, "?");
			for (int i = 0; i < split.length - 1; i++) {
				String pName = name.replaceAll("\\.", "_") + "_" + i;
				sqlBuffer.append(split[i]).append(":").append(pName);
				gNames.add(pName);
				gParames.add(values.get(valIndex++));
			}
			sqlBuffer.append(split[split.length - 1]);
		} else if (appender.matches("(?s).*:\\s*\\w+.*")) {
			String[] pNames = RegularUtils.matchedString(appender, ":\\s*(\\w+)");
			for (int i = 0; i < pNames.length; i++) {
				gNames.add(pNames[i]);
				if (!containsParamNames()) {
					gParames.add(values.get(valIndex++));
				} else {
					gParames.add(values.get(paramNames.indexOf(pNames[i])));
				}
			}
			sqlBuffer.append(appender);
		} else {
			sqlBuffer.append(appender);
		}
		return valIndex;
	}

	/**
	 * @return
	 */
	public boolean containsParamNames() {
		return paramNames != null && !paramNames.isEmpty();
	}

	/**
	 * @return
	 */
	public String[] getParamNames() {
		return gNames.toArray(new String[0]);
	}

	/**
	 * 输出到string
	 */
	public String toString() {
		return gSQL;
	}

	/**
	 * 返回追加语句
	 * 
	 * @param name 追加语句参数
	 * @return
	 */
	protected String getAppender(String name) {
		if (mapedAppender != null) {
			return mapedAppender.get(name);
		}
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
