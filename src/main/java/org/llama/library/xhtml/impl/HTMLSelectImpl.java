package org.llama.library.xhtml.impl;

import org.llama.library.xhtml.Document;
import org.llama.library.xhtml.Element;

public class HTMLSelectImpl extends Element {
	public HTMLSelectImpl() {
		super("select");
	}

	public HTMLSelectImpl(String name) {
		super("select");
		setName(name);
	}

	public void setName(String name) {
		this.addAttribute("name", name);
	}

	public void setOnChange(String event) {
		this.addAttribute("onchange", event);
	}

	public void addOption(String value, String text) {
		Option opt = new Option(value, text);
		this.appendChild(opt);
	}

	public void addOption(String value, String text, boolean selected) {
		Option opt = new Option(value, text, selected);
		this.appendChild(opt);
	}

	public class Option extends Element {
		public String text;

		public Option() {
			super("option");
		}

		public Option(String value, String text) {
			super("option");
			this.addAttribute("value", value);
			this.text = text;
			this.appendChild(Document.createTextNode(text));
		}

		public Option(String value, String text, boolean selected) {
			super("option");
			this.addAttribute("value", value);
			this.text = text;
			this.appendChild(Document.createTextNode(text));
			if (selected)
				setSelected();
		}

		public void setValue(String value) {
			this.addAttribute("value", value);
		}

		public void setText(String text) {
			this.text = text;
		}

		public void setSelected() {
			this.addAttribute("selected", "selected");
		}

	}
}
