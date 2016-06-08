package org.llama.library.utils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.xpath.XPathAPI;
import org.llama.library.utils.HtmlUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class HtmlUtils {
	private static final String REGX_XPATH_NODE = "([\\w\\*^:]+)(\\[[^\\[]*\\])*(\\(\\))*";
	private static final String REGX_METHOD = "^([\\w]+)\\((.*)\\)";
	private static final String NBSP = "&nbsp;";
	private static final String BLANK = " ";
	private static final String REGX_BLANK = "[\\s\\u00A0]+";

	/**
	 * 将html标签过滤
	 * 
	 * @Title: escapeHtml
	 * @param source
	 * @return
	 * @date 2012-4-18 下午2:29:49
	 * @author tonny
	 * @version 1.0
	 */
	public static String escapeHtml(String source) {
		source = StringUtils.replace(source, "&", "&amp;");
		source = StringUtils.replace(source, "<", "&lt;");
		source = StringUtils.replace(source, ">", "&gt;");
		source = StringUtils.replace(source, " ", "&nbsp;");
		source = StringUtils.replace(source, "\"", "&#0034;");
		source = StringUtils.replace(source, "\'", "&#0039;");
		return source;
	}

	/**
	 * 将过滤的html还原
	 * 
	 * @Title: decodingHtml
	 * @param source
	 * @return
	 * @date 2012-4-18 下午2:30:15
	 * @author tonny
	 * @version 1.0
	 */
	public static String decodingHtml(String source) {
		source = StringUtils.replace(source, "&amp;", "&");
		source = StringUtils.replace(source, "&lt;", "<");
		source = StringUtils.replace(source, "&gt;", ">");
		source = StringUtils.replace(source, "&nbsp;", " ");
		source = StringUtils.replace(source, "&#0034;", "\"");
		source = StringUtils.replace(source, "&#0039;", "\'");
		return source;
	}

	/**
	 * 描述：获得HTML节点
	 * 
	 * @param node 节点
	 * @param id 节点ID
	 * @return HTML节点
	 * @throws Exception 解析异常
	 */
	public static Node getById(Node node, String id) throws Exception {
		List<Node> nodeList = getElementsByXPath(node, ".//*[@id='" + id + "']");
		if (nodeList != null && nodeList.size() > 0) {
			return nodeList.get(0);
		}
		return null;
	}

	/**
	 * 描述：获得HTML节点
	 * 
	 * @param node 节点
	 * @param tagName 节点名称
	 * @return HTML节点
	 * @throws Exception 解析异常
	 */
	public static List<Node> getByTagName(Node node, String tagName) throws Exception {
		List<Node> nodeList = getElementsByXPath(node, ".//" + tagName);
		return nodeList;
	}

	/**
	 * 描述：获得HTML节点
	 * 
	 * @param node 节点
	 * @param name 名称属性值
	 * @return 获得HTML节点
	 * @throws Exception 解析异常
	 */
	public static List<Node> getByName(Node node, String name) throws Exception {
		List<Node> nodeList = getElementsByXPath(node, ".//*[@name='" + name + "']");
		return nodeList;
	}

	/**
	 * 描述：获得属性
	 * 
	 * @param node 节点
	 * @param attr 属性
	 * @return 属性
	 * @throws Exception 解析异常
	 */
	public static String getAttribute(Node node, String attr) throws Exception {
		if (node instanceof Element) {
			return ((Element) node).getAttribute(attr);
		} else {
			if (node.getAttributes() == null) {
				return null;
			}
			Node n = node.getAttributes().getNamedItem(attr);
			if (n != null) {
				return node.getAttributes().getNamedItem(attr).getNodeValue();
			} else {
				return null;
			}
		}
	}

	/**
	 * 描述：获得HTML节点
	 * 
	 * @author 刘雨
	 * @Date 2010-2-10 上午10:38:35
	 * @param node 节点
	 * @param xpath XPath值
	 * @return 获得HTML节点
	 * @throws Exception 解析异常
	 */
	public static List<Node> getElementsByXPath(Node node, String xpath) throws Exception {
		NodeList nodeList = XPathAPI.selectNodeList(node, HtmlUtils.toXpath(xpath));
		List<Node> list = getList(nodeList);
		return list;
	}

	/**
	 * 将字符串转换为附合xpath语法的字符串
	 * 
	 * @param xpath 类xpath语法的字符串
	 * @return 附合xpath语法的字符串
	 */
	private static String toXpath(String xpath) {
		Pattern pattern = Pattern.compile(REGX_METHOD);
		Matcher matcher = pattern.matcher(xpath);
		if (matcher.find()) {
			StringBuffer bf = new StringBuffer();
			bf.append(matcher.group(1));
			bf.append("(");
			bf.append(xpath(matcher.group(2)));
			bf.append(")");
			return bf.toString();
		} else {
			return xpath(xpath);
		}
	}

	/**
	 * 将xpath中的tag转为大写
	 * 
	 * @param xpath xpath
	 * @return 合法的xpath
	 */
	private static String xpath(String xpath) {
		Pattern pattern = Pattern.compile(REGX_XPATH_NODE);
		Matcher matcher = pattern.matcher(xpath);
		StringBuffer bf = new StringBuffer();
		while (matcher.find()) {
			String tag = matcher.group(1);
			String attr = matcher.group(2);
			String method = matcher.group(3);
			if (StringUtils.isNotBlank(method) && StringUtils.isBlank(attr)) {
				matcher.appendReplacement(bf, StringUtils.stripToEmpty(tag) + StringUtils.stripToEmpty(attr)
						+ StringUtils.stripToEmpty(method));
			} else {
				matcher.appendReplacement(bf,
						StringUtils.stripToEmpty(tag).toUpperCase() + StringUtils.stripToEmpty(attr));
			}

		}
		matcher.appendTail(bf);
		return bf.toString();
	}

	/**
	 * 打印w2的html代码到系统
	 * 
	 * @param element w3node节点
	 * @throws Exception 异常
	 */
	public static void outElement(Element element) throws Exception {
		javax.xml.transform.Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.setOutputProperty(OutputKeys.ENCODING, "GBK");
		trans.setOutputProperty(OutputKeys.METHOD, "html");
		trans.transform(new javax.xml.transform.dom.DOMSource(element.getOwnerDocument()),
				new javax.xml.transform.stream.StreamResult(System.out));
	}

	/**
	 * 打印w2的html代码到系统
	 * 
	 * @param element w3node节点
	 * @throws Exception 异常
	 * @return 节点的html
	 */
	public static String outerHtml(Node element) throws Exception {
		javax.xml.transform.Transformer trans = TransformerFactory.newInstance().newTransformer();
		trans.setOutputProperty(OutputKeys.ENCODING, "GBK");
		Writer sw = new StringWriter();
		trans.transform(new javax.xml.transform.dom.DOMSource(element), new javax.xml.transform.stream.StreamResult(
				sw));
		sw.close();
		return StringUtils.substringAfter(sw.toString().toLowerCase(), "?>");
	}

	/**
	 * 描述：获得node节点值(去掉换行、制表符、转换&nbsp;等)
	 * 
	 * @author 刘雨
	 * @Date 2010-2-10 上午10:38:35
	 * @param node 节点
	 * @return 处理后字符
	 */
	public static String getText(Node node) {
		String str = node.getTextContent().trim();
		str = str.replaceAll(REGX_BLANK, BLANK);
		str = str.replaceAll(NBSP, BLANK);
		return str;
	}

	/**
	 * 描述：获得node节点值(去掉换行、制表符、转换&nbsp;等)
	 * 
	 * @author 刘雨
	 * @Date 2010-2-10 上午10:38:35
	 * @param node 节点
	 * @return 处理后字符
	 */
	public static String getTextLine(Node node) {
		StringBuffer buffer = new StringBuffer();
		if (node != null) {
			getInnerText(node, buffer);
		}
		return buffer.toString().trim();
	}

	/**
	 * 描述：NodeList转换List.
	 * 
	 * @author 刘雨
	 * @Date 2010-2-10 上午10:38:35
	 * @param nodeList node类型集合
	 * @return 节点集合
	 */
	public static List<Node> getList(final NodeList nodeList) {
		List<Node> list = new ArrayList<Node>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			list.add(nodeList.item(i));
		}
		return list;
	}

	/**
	 * 描述：获得node节点值(去掉换行、制表符、转换&nbsp;等)
	 * 
	 * @author 刘雨
	 * @Date 2010-2-10 上午10:38:35
	 * @param node 节点
	 * @param buffer 数据缓存
	 */
	public static void getInnerText(Node node, StringBuffer buffer) {
		if (buffer.lastIndexOf("&#xD;") < buffer.length() - 5
				&& (node.getNodeName().equals("P") || node.getNodeName().equals("DIV")
						|| node.getNodeName().startsWith("H") || node.getNodeName().equals("LI") || node
						.getNodeName().equals("TR"))) {
			buffer.append("&#xD;");
		}
		NodeList children = node.getChildNodes();
		if (children.getLength() == 0) {
			String str = node.getTextContent();
			str = str.replaceAll(REGX_BLANK, BLANK);
			str = str.replaceAll(NBSP, BLANK);
			buffer.append(str);
		}
		for (int i = 0; i < children.getLength(); i++) {
			getInnerText(children.item(i), buffer);
		}

		if (buffer.lastIndexOf("&#xD;") < buffer.length() - 5
				&& (node.getNodeName().equals("P") || node.getNodeName().equals("DIV")
						|| node.getNodeName().equals("BR") || node.getNodeName().startsWith("H")
						|| node.getNodeName().equals("LI") || node.getNodeName().equals("TR"))) {
			buffer.append("&#xD;");
		}
	}

}
