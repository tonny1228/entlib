package org.llama.library.xhtml;

public class Document {
	public static Element createElement(String tagName){
		Element element = new Element(tagName);
		return element;
	}
	
	public static TextNode createTextNode(String text){
		TextNode node = new TextNode(text);
		return node;
	}
}
