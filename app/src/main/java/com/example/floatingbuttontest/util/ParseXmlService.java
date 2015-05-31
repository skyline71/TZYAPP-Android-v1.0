package com.example.floatingbuttontest.util;

import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author coolszy
 * @date 2012-4-26
 * @blog http://blog.92coding.com
 */
public class ParseXmlService {
    public HashMap<String, String> parseXml(InputStream inStream) throws Exception {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inStream);
        Element root = document.getDocumentElement();
        NodeList childNodes = root.getChildNodes();
        //NodeList childNodes = root.getElementsByTagName("updataInfo");
        for (int j = 0; j < childNodes.getLength(); j++) {
            Node childNode = (Node) childNodes.item(j);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                if ("versionCode".equals(childElement.getNodeName())) {
                    hashMap.put("versionCode", childElement.getFirstChild().getNodeValue());
                }
                else if (("versionName".equals(childElement.getNodeName())))
                {
                    hashMap.put("versionName",childElement.getFirstChild().getNodeValue());
                }
                else if (("apkURL".equals(childElement.getNodeName()))) {
                    hashMap.put("apkURL", childElement.getFirstChild().getNodeValue());
                }
            }
        }
        return hashMap;
    }
}
