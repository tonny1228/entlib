package org.llama.library.xhtml.impl;

import org.llama.library.xhtml.Document;
import org.llama.library.xhtml.Element;

public class TableCell extends Element {

	public TableCell() {
		super("td");
	}

	public void setInnerText(String innerText) {
		this.clearChildren();
		this.appendChild(Document.createTextNode(innerText));
	}
}
