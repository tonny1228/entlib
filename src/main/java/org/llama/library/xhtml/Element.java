package org.llama.library.xhtml;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Element extends Node {
	protected String tagName;

	protected String id;

	protected String name;

	protected Hashtable attributes;

	protected Hashtable style;

	protected List<Node> children;

	protected String className;

	protected Element(String tagName) {
		super("Object");
		this.tagName = tagName;
		attributes = new Hashtable();
		style = new Hashtable();
		children = new ArrayList();
	}

	public Element addAttribute(String key, String prop) {
		attributes.put(key, StringUtils.stripToEmpty(prop));
		return this;
	}

	public Element addAttributes(Map attriutes) {
		attributes.putAll(attriutes);
		return this;
	}

	public Element removeAttributes(String key) {
		attributes.remove(key);
		return this;
	}

	public boolean containsAttributes(String key) {
		return attributes.contains(key);
	}

	public Element addStyle(String key, String prop) {
		style.put(key, prop);
		return this;
	}

	public Element removeStyle(String key) {
		style.remove(key);
		return this;
	}

	public boolean containsStyle(String key) {
		return style.contains(key);
	}

	protected String getAttributes() {
		StringBuffer tr = new StringBuffer();
		for (int i = 0; i < attributes.size(); i++) {
			String key = (String) attributes.keySet().toArray()[i];
			tr.append(" " + key + "=\"" + attributes.get(key) + "\" ");
		}
		if (style.size() > 0)
			tr.append("style=\"" + getStyle() + "\"");
		return tr.toString();
	}

	protected String getStyle() {
		StringBuffer tr = new StringBuffer();
		for (int i = 0; i < style.size(); i++) {
			String key = (String) style.keySet().toArray()[i];
			tr.append(key + ":" + style.get(key) + ";");
		}
		return tr.toString();
	}

	public void appendChild(Node child) {
		children.add(child);
		child.setParentNode(this);
	}

	protected void clearChildren() {
		children.clear();
	}

	public String getInnerHtml() {
		StringBuffer html = new StringBuffer();
		for (int i = 0; i < children.size(); i++) {
			html.append(children.get(i).toString());
		}
		return html.toString();
	}

	public String getOuterHtml() {
		StringBuffer html = new StringBuffer("<").append(tagName);
		html.append(this.getAttributes());
		if (children.size() > 0) {
			html.append(">");
			html.append(getInnerHtml());
			html.append("</").append(tagName).append(">");
		} else
			html.append("/").append(">");
		return html.toString();
	}

	public void setInnerHtml(String innerHtml) {
		clearChildren();
		this.appendChild(new HTMLFragment(innerHtml));
	}

	public void insertBefore(Element child, Element before) {
		if (!children.contains(before))
			return;
		children.add(children.indexOf(before), child);
		child.setParentNode(this);
	}

	public String toString() {
		return getOuterHtml();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
		this.addAttribute(ID, id);
	}

	public void setName(String name) {
		this.name = name;
		this.addAttribute(NAME, name);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
		this.addAttribute("class", className);
	}

}
