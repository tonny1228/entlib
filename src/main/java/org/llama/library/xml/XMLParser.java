package org.llama.library.xml;

import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.*;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


/**
 * Created by tonny on 2015/7/6.
 */
public class XMLParser {
    private Map<String, Object> datas = new LinkedHashMap();
    private Node node;

    private void o(Object o) {
        System.out.println(o);
    }


    public List<Object> getList(String key) {
        return get(node, StringUtils.substringAfter(key, "."));
    }

    private List<Object> get(Node node, String key) {
        Object o = node.mapChild.get(key);
        o(o);
        if (o instanceof List) {
            return (List<Object>) o;
        }else{

        }
        return null;
    }

    private void toMap() {
        Map<String, Object> o = new HashMap();
    }


    /**
     * 解析
     *
     * @param xml
     * @throws Exception
     */
    public void parse(String xml) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xml.getBytes()));
            Element root = document.getDocumentElement();
            o("开始");
            node = new Node();
            node.name = root.getTagName();
            node.depth = 0;
            listChildren(root, node);
            rebuild(node);
            datas.put(node.getPath(), node.mapChild);
            mappping(node);
            Set<String> strings = datas.keySet();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 将所有数据添加到map中
     *
     * @param node
     */
    private void mappping(Node node) {
        for (String key : node.mapChild.keySet()) {
            Object o = node.mapChild.get(key);
            if (o instanceof Node) {
                Node o1 = (Node) o;
                putInDatas(o1);
                mappping(o1);
            } else if (o instanceof List) {
                List l = (List) o;
                for (int i = 0; i < l.size(); i++) {

                    Object o1 = l.get(i);
                    if (o1 instanceof Node) {
                        String substring = ((Node) o1).getPath().substring(0, ((Node) o1).getPath().lastIndexOf("["));
                        if (i == 0) {
                            List list = new ArrayList();
                            list.add(((Node) o1).mapChild);
//                            datas.put(substring, list);
                        } else {
                            List list = (List) datas.get(substring);
                            list.add(((Node) o1).mapChild);
                        }

                        putInDatas((Node) o1);

                    } else {
//                        datas.put(node.getPath(), o1);
                    }
                }
            } else if (o instanceof LinkedHashMap) {
//                datas.put(node.getPath() + "." + key, o);
                LinkedHashMap map = (LinkedHashMap) o;
                Set<String> strings = ((LinkedHashMap) o).keySet();
                for (String string : strings) {
//                    datas.put(node.getPath() + "." + key + "." + string, map.get(string));
                }
            } else {
//                datas.put(node.getPath() + "." + key, o);
//                datas.put(node.getPath() + "." + key, o);
            }
        }

    }


    /**
     * 添加node到data
     *
     * @param o1
     */
    void putInDatas(Node o1) {
        for (String aname : o1.attributes.keySet()) {
            datas.put(o1.getPath() + "[" + aname + "]", o1.attributes.get(aname));
        }
        datas.put(o1.getPath(), o1.mapChild);
    }


    /**
     * 重构数据
     *
     * @param n
     */
    private void rebuild(Node n) {
        if (n.children != null) {
            for (int i = 0; i < n.children.size(); i++) {
                rebuild(n.children.get(i));
            }
        }
        n.rebuild();

    }


    /**
     * @param element
     * @param parent
     */
    private void listChildren(Element element, Node parent) {
        NodeList childNodes = element.getChildNodes();
        o(StringUtils.leftPad("", parent.depth + 1, "\t") + "开始:" + element.getTagName());
        boolean hasChildren = false;
        for (int i = 0; i < childNodes.getLength(); i++) {
            org.w3c.dom.Node c = childNodes.item(i);
            if (!(c instanceof Element)) {
//                o("非节点");
                continue;
            }
            o(StringUtils.leftPad("", parent.depth + 1, "\t") + "子节点:" + c.getNodeName());
            hasChildren = true;
            Element item = (Element) c;
            Node node = new Node();
            node.name = item.getTagName();
            node.depth = parent.depth + 1;
            node.setParent(parent);
            NamedNodeMap attributes = item.getAttributes();
            for (int j = 0; j < attributes.getLength(); j++) {
                o(StringUtils.leftPad("", parent.depth + 1, "\t") + c.getNodeName() + " add " + attributes.item(j).getNodeName() + " " + attributes.item(j).getTextContent().trim());
                node.attributes.put(attributes.item(j).getNodeName(), attributes.item(j).getTextContent().trim());
            }
            listChildren(item, node);
        }
        if (!hasChildren) {
            parent.text = element.getTextContent().trim();
            o(StringUtils.leftPad("", parent.depth + 1, "\t") + element.getNodeName() + " text " + parent.text);
        }
    }

    public Map<String, Object> getDatas() {
        return datas;
    }

    public Node getNode() {
        return node;
    }

    public class Node implements Serializable {
        Node parent;
        List<Node> children;
        String name;
        String text;
        int depth;
        int index;
        Node child;
        LinkedHashMap<String, Object> mapChild = new LinkedHashMap();
        Map<String, String> attributes = new HashMap<String, String>();

        public void rebuild() {

            if (!attributes.isEmpty()) {
                Set<String> keySet = attributes.keySet();
                for (String s : keySet) {
                    mapChild.put(s, attributes.get(s));
                    o(name + " add attr " + s + "=" + attributes.get(s));
                }
            }
            if (children == null && mapChild.isEmpty()) {
                o(parent.name + " add child " + name + " " + text);
                index = parent.put(name, text);
//                Log.info("put in text " + name);
                o(getPath() + "=" + text);
//                datas.put(getPath(), text);
                return;
            }

            if (!mapChild.isEmpty() && parent != null) {
                index = parent.put(name, this.mapChild);
                o(getPath() + "=" + mapChild);
//                datas.put(getPath(), this.mapChild);
                this.children = null;
            }
        }

        public int put(String name, Object o) {
            if (!mapChild.containsKey(name)) {
                mapChild.put(name, o);
                return -1;
            } else {
                Object e = mapChild.get(name);
                if (e instanceof List) {
                    ((List) e).add(o);
                    return ((List) e).size() - 1;
                } else {
                    ArrayList list = new ArrayList();
                    list.add(e);
                    list.add(o);
                    mapChild.put(name, list);
                    return list.size() - 1;
                }
            }

        }

        public void setParent(Node parent) {
            this.parent = parent;
            if (parent.children == null) {
                parent.children = new ArrayList<Node>();
            }
            this.index = parent.children.size();
            parent.children.add(this);
        }

        String getPath() {
            o("path: " + name + " index= " + index + " child =" + mapChild.size());
            if (parent == null)
                return name;
            if (index > -1 && parent.mapChild.size() > 1)
                return parent.getPath() + "." + name + "[" + index + "]";
            else
                return parent.getPath() + "." + name;
        }

        @Override
        public String toString() {
            return getPath() + "=" + (mapChild != null ? "(m)" + mapChild : (children != null ? "(c)" + children : text));
        }
    }


    public static void main(String[] args) {

    }
}
