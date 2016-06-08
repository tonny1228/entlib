/**
 * 
 */
package org.llama.library;

import java.beans.PropertyDescriptor;
import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.llama.library.validate.TestBean;

import junit.framework.TestCase;

/**
 * 
 * @author tonny
 * @date 2015年5月21日
 * @version 1.0.0
 */
public class CommonTest extends TestCase {

	public void testProperties() {
		PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(A.class);
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			System.out.println(propertyDescriptor.getName());
		}
	}
}

class A extends TestBean implements Serializable{
	
	/**
	 * @param name
	 */
	public A(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String a;
}