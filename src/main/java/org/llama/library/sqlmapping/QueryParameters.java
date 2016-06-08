/**  
 * @Title: ParametersQuery.java
 * @Package works.tonny.framework.dao
 * @author Tonny
 * @date 2011-10-22 下午1:02:46
 */
package org.llama.library.sqlmapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数
 * 
 * @ClassName: ParametersQuery
 * @Description:
 * @author Tonny
 * @date 2011-10-22 下午1:02:46
 * @version 1.0.1
 */
public class QueryParameters {

	private List<Parameter> parameters = new ArrayList<Parameter>();

	private List<String> parameterNames = new ArrayList<String>();

	/**
	 * 添加属性参数
	 * 
	 * @Title: addParameter
	 * @Description:
	 * @param @param name
	 * @param @param parameter 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void addParameter(String name, Object... parameter) {
		parameters.add(new Parameter(name, parameter));
		parameterNames.add(name);
	}

	/**
	 * 设置属性参数
	 * 
	 * @Title: addParameter
	 * @Description:
	 * @param @param name
	 * @param @param parameter 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void setParameter(String name, Object... parameter) {
		if (parameterNames.contains(name)) {
			getParameter(name).setValue(parameter);
		} else {
			addParameter(name, parameter);
		}
	}

	/**
	 * 添加范围参数
	 * 
	 * @Title: addRangeParameter
	 * @Description:
	 * @param @param name
	 * @param @param lower
	 * @param @param upper 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void addRangeParameter(String name, Object lower, Object upper) {
		parameters.add(new Parameter(name, lower, upper));
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	/**
	 * 是否包含此参数名
	 * 
	 * @Title: contains
	 * @param name 参数名
	 * @return
	 * @date 2012-7-26 上午9:25:50
	 * @author tonny
	 * @version 1.0
	 */
	public boolean contains(String name) {
		return parameterNames.contains(name);
	}

	/**
	 * 根据参数名获取参数
	 * 
	 * @Title: contains
	 * @param name 参数名
	 * @return
	 * @date 2012-7-26 上午9:25:50
	 * @author tonny
	 * @version 1.0
	 */
	public Parameter getParameter(String name) {
		if (!parameterNames.contains(name)) {
			return null;
		}
		return parameters.get(parameterNames.indexOf(name));
	}

	/**
	 * 参数
	 * 
	 * @ClassName: Parameter
	 * @Description:
	 * @author Tonny
	 * @date 2011-10-22 下午1:13:27
	 * @version 1.0
	 */
	public static class Parameter {
		protected String name;

		protected Object[] value;

		public Parameter(String name, Object... value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Object[] getValue() {
			return value;
		}

		public void setValue(Object[] value) {
			this.value = value;
		}
	}

	// /**
	// * 范围参数
	// *
	// * @ClassName: Parameter
	// * @Description:
	// * @author Tonny
	// * @date 2011-10-22 下午1:13:27
	// * @version 1.0
	// */
	// public static class RangeParameter extends Parameter {
	// protected Object upper;
	//
	// protected Object lower;
	//
	// public RangeParameter(String name, Object lower, Object upper) {
	// super(name, new Object[] { lower, upper });
	// this.upper = upper;
	// this.lower = lower;
	// }
	//
	// public Object getUpper() {
	// return upper;
	// }
	//
	// public void setUpper(String upper) {
	// this.upper = upper;
	// }
	//
	// public Object getLower() {
	// return lower;
	// }
	//
	// public void setLower(Object lower) {
	// this.lower = lower;
	// }
	//
	// }
}
