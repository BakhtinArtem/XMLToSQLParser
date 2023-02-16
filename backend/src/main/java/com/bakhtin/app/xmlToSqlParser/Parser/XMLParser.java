package com.bakhtin.app.xmlToSqlParser.Parser;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLParser implements IParser {

    private Document document;
    @Override
    public void parse(String filePath) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            document = builder.parse(filePath);                         //  build document from given path
            document.getDocumentElement().normalize();                  //  normalize xml

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public NodeList getTokensByName(String name) {
        return document.getElementsByTagName(name);
    }
}
