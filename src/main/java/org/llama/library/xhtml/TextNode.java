package org.llama.library.xhtml;

import org.apache.commons.lang.StringEscapeUtils;

public class TextNode extends Node {
	private String text;

	TextNode(String text) {
		super("Text");
		this.text = asText(text);
	}

	public String asText(String text) {
		return StringEscapeUtils.escapeHtml(text);
	}

	public String toString() {
		return this.text;
	}
}
