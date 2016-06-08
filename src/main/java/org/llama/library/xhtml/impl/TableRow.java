package org.llama.library.xhtml.impl;

import java.util.Hashtable;

import org.llama.library.xhtml.Element;


public class TableRow extends Element {

	public TableRow() {
		super("tr");
	}

	public TableCell addCol() {
		TableCell td = new TableCell();
		appendChild(td);
		return td;
	}

	public int size() {
		return children.size();
	}

	public void setDefaultValue() {
		this.addAttribute("align", this.ALIGN_LEFT);
	}

}
