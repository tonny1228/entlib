package org.llama.library.xhtml;

public class HTMLFragment extends Node{
	private String HTMLFragment;

	public HTMLFragment(String fragment) {
		super("HTMLFragment");
		HTMLFragment = fragment;
	}

	@Override
	public String toString() {
		return HTMLFragment;
	}

}
