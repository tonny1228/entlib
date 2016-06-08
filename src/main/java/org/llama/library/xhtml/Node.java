package org.llama.library.xhtml;

public class Node {
	public static final String ALIGN_LEFT = "left";

	public static final String ALIGN_CENTER = "center";

	public static final String ALIGN_RIGHT = "right";
	
	public static final String ID = "id";
	
	public static final String NAME = "name";
	
	public static final String CLASS_NAME = "class";
	
	protected String nodeType;
	
	protected Node parentNode;
	
	public Node getParentNode() {
		return parentNode;
	}

	public void setParentNode(Node parentNode) {
		this.parentNode = parentNode;
	}

	Node(String type){
		this.nodeType = type;
	}
	
	public String toString(){
		return nodeType+":"+super.toString();
	}

	public String getNodeType() {
		return nodeType;
	}
}
