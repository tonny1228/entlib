package org.llama.library.xhtml.impl;

import java.util.Hashtable;

import org.llama.library.xhtml.Element;


public class Table extends Element {
	private boolean tbody;

	public Table() {
		super("table");
	}

	public int size() {
		return children.size();
	}

	public TableRow addRow() {
		TableRow tr = new TableRow();
		appendChild(tr);
		return tr;
	}

	public void setDefaultValue() {
		this.addAttribute("width", "100%");
		this.addAttribute("border", "1");
		this.addAttribute("cellspacing", "0");
		this.addAttribute("cellpadding", "2");
		this.addAttribute("align", ALIGN_CENTER);
		this.addStyle("border-collapse", "collapse");
	}


	public static void main(String[] argv) {
		Table tab = new Table();
		tab.setDefaultValue();
		TableRow tr = tab.addRow();
		tr.addAttribute("align", tr.ALIGN_CENTER);
		tr.addStyle("font-size", "12pt");
		for (int i = 0; i < 5; i++) {
			TableCell td = tr.addCol();
			td.setInnerHtml(Integer.toString(i));
			td.addStyle("font-size", "12pt");
		}
		System.out.println(tab);
	}

	public boolean isTbody() {
		return tbody;
	}

	public void setTbody(boolean tbody) {
		this.tbody = tbody;
	}
}
