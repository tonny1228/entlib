package org.llama.library.validate;

import java.util.HashMap;
import java.util.Map;

public class TestBean {
	private String name;

	private int age;

	private Map map = new HashMap();
	private TestBean child ;

	public TestBean(String name) {
		super();
		this.name = name;
	}

	public TestBean(String name, int age) {
		super();
		this.name = name;
		this.age = age;
		child = new TestBean("my child");
		map.put("child", child);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public TestBean getChild() {
		return child;
	}

	public void setChild(TestBean child) {
		this.child = child;
	}
}
