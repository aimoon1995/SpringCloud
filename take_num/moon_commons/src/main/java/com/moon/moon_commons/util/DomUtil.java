package com.moon.moon_commons.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Dom工具类
 * @author 浩
 *
 */
public class DomUtil {
    
    /** 
     * Returns the text of the child element identified by nodeTagName.
     *
     * @param element This is the element where the child element may be found.
     * @param nodeTagName This is the name of the child element.
     * @return text of the child element.
     * @throws Exception if an error is encountered.
     */
    public static String getChildNodeText (Element element, String nodeTagName) 
            throws Exception {

        NodeList elements = element.getElementsByTagName(nodeTagName); 
    
        for (int j =0; j < elements.getLength(); j++) {
            Node node = (Node) elements.item(j);
            NodeList elementsChild = node.getChildNodes();    
            for (int k =0; k < elementsChild.getLength(); k++) {
                Node node2 = (Node) elementsChild.item(k);
                if ( node2.getNodeType() == Node.TEXT_NODE ||
                        node2.getNodeType() == Node.CDATA_SECTION_NODE) {
                   return node2.getNodeValue();
                }
            }
        }
        return null;
    }

    /**
     * Returns the text of the child element identified by nodeTagName.
     *
     * @param element This is the element where the child element may be found.
     * @param nodeTagName This is the name of the child element.
     * @return text of the child element.
     * @throws Exception if an error is encountered.
     */
    public static List<String> getChildNodeTextList (Element element, String nodeTagName)
            throws Exception {
    	List<String> childNodeTextList = new ArrayList<String>();
        NodeList elements = element.getElementsByTagName(nodeTagName);

        for (int j =0; j < elements.getLength(); j++) {
            Node node = (Node) elements.item(j);
            NodeList elementsChild = node.getChildNodes();
            for (int k =0; k < elementsChild.getLength(); k++) {
                Node node2 = (Node) elementsChild.item(k);
                if ( node2.getNodeType() == Node.TEXT_NODE ) {
                   childNodeTextList.add(node2.getNodeValue());
                }
            }
        }
        return childNodeTextList;
    }

    /**
     * Returns the text of the element  
     *
     * @param element This is the element from which to retrieve the text.
     * @return text of the element.
     * @throws Exception if an error is encountered.
     */
    public static String getNodeText (Element element) throws Exception {
    
        NodeList elementsChild = element.getChildNodes();    

        for (int k =0; k < elementsChild.getLength(); k++) {
            Node node = (Node) elementsChild.item(k);
            if ( node.getNodeType() == Node.TEXT_NODE ) {
               return node.getNodeValue();
            }
        }
        return null;
    }

    public static Element getElement(Element parent, String tagName) {
        if (tagName == null) {
            return null;
        }
        NodeList variables = parent.getElementsByTagName(tagName);
        if (variables.getLength() > 0) {
            return (Element) variables.item(0);
        }
        return null;
    }

    public static ArrayList<Element> getElements(Element parent, String tagName) {
        if (parent == null) {
            return null;
        }
        NodeList variables = parent.getChildNodes();
        ArrayList<Element> elements = new ArrayList<Element>();
        for (int i = 0; i < variables.getLength(); i++) {
            Node node = variables.item(i);
            if (node instanceof Element) {
                Element element = (Element) node;
                if (tagName == null || "".equals(tagName)) {
                    elements.add(element);
                } else if (tagName.equals(element.getTagName())) {
                    elements.add(element);
                }
            }
        }
        return elements;
    }

    public static ArrayList<Element> getElements(Element parent) {
        return getElements(parent, null);
    }
}
