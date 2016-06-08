package org.llama.library.xhtml.impl;

import org.llama.library.xhtml.Element;

public class HTMLFormInputImpl  extends Element {
	public HTMLFormInputImpl() {
		super("input");
		this.setType("text");
	}

	public HTMLFormInputImpl(String type, String name, String value) {
		super("input");
		this.setType(type);
		this.setName(name);
		this.setValue(value);
	}

	public HTMLFormInputImpl(String type, String name, String value, int size) {
		super("input");
		this.setType(type);
		this.setName(name);
		this.setValue(value);
		this.setSize(size);
	}

	public HTMLFormInputImpl(String type, String name, String value, int size, String styleClass) {
		super("input");
		this.setType(type);
		this.setName(name);
		this.setValue(value);
		this.setSize(size);
		this.setClassName(styleClass);
	}

	public void setType(String type) {
		this.addAttribute("type", type);
	}

	public void setValue(String value) {
		this.addAttribute("value", value);
	}


	public void setSize(int size) {
		this.addAttribute("size", Integer.toString(size));
	}

}
