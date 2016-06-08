package org.llama.library;

import org.junit.Test;
import org.llama.library.xml.XMLParser;
import org.llama.library.xml.XMlParser2;

/**
 * Created by tonny on 2016/3/21.
 */
public class XMLParserTest {
    @Test
    public void test() throws Exception {
        String xml =
                "<data>\n" +
                        "\t<list>\n" +
                        "\t\t\n" +
                        "\t          <item>\n" +
//                        "\t\t\t\t <img>http://www.csvclub.org:80/res/csvclub/u/1603/16034981663489423221901.gif</img>\n" +
                        "                 <name>2015年12期杂志</name>\n" +
                        "                 <orderNo>12</orderNo>\n" +
                        "                 <width><item>1</item><item>2</item></width>\n" +
//                        "                 <height>100</height>\n" +
//                        "                 <url>http://www.csvclub.org:80/jsp/csvclub/csvclient/me/csvtimesinfo.jsp?id=93c876bfcbca4ada8b25871ac0df72cb</url>\n" +
                        "\t\t\t</item>\t\n" +
                        "\t\t\t\n" +
                        "\t          <item>\n" +
//                        "\t\t\t\t <img>http://www.csvclub.org:80/res/csvclub/u/1603/16032119847092927017424.gif</img>\n" +
                        "                 <name>2015年11期杂志</name>\n" +
                        "                 <orderNo>11</orderNo>\n" +
//                        "                 <width>100</width>\n" +
//                        "                 <height>100</height>\n" +
//                        "                 <url>http://www.csvclub.org:80/jsp/csvclub/csvclient/me/csvtimesinfo.jsp?id=e5150bc5fdf147af9346edfbb9f9a136</url>\n" +
                        "\t\t\t</item>\t\n" +
                        "\t\t\t\n" +
                        "\t</list>\n" +
                        "\t<menu> \n" +
                        "\t\t<mid>200</mid> \t\n" +
                        "\t\t<mproxyurl>\n" +
                        "\t\t\thttp://www.csvclub.org:80/jsp/csvclub/csvclient/me/csvtimeslist.jsp?years=&amp;pages=100\n" +
                        "\t\t</mproxyurl>\n" +
                        "\t\t<years>2016,2015,2014</years>\n" +
                        "\t</menu>\n" +
                        "</data>";
        XMlParser2 parser = new XMlParser2();
        parser.parse(xml);
        System.out.println("____________________________________________");
        System.out.println(parser.getList("data"));
        System.out.println(parser.getList("data.list"));
        System.out.println(parser.getList("data.list.item"));
        System.out.println(parser.getList("data.list.item.name"));
        System.out.println(parser.getList("data.list.item[1].name"));
        System.out.println(parser.getList("data.list.item.width"));
        System.out.println(parser.getList("data.list.item.width.item.name"));
        System.out.println(parser.getList("data.list.item.width.item[1]"));
        System.out.println(parser.getList("data.list.item[0].width.item[0]"));

        System.out.println(parser.get("data"));
        System.out.println(parser.get("data.list"));
        System.out.println(parser.get("data.list.item"));
        System.out.println(parser.get("data.list.item.name"));
        System.out.println(parser.get("data.list.item[1].name"));
        System.out.println(parser.get("data.list.item.width"));
        System.out.println(parser.get("data.list.item.width.item"));
        System.out.println(parser.get("data.list.item.width.item[1]"));
        System.out.println(parser.get("data.list.item[0].width.item[0]"));


    }
}