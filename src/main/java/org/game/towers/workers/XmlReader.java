package org.game.towers.workers;

import static java.lang.String.format;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.game.towers.game.Config;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {

	private static DataInputStream out;

    public static void getStreamFromFile(String fileName) {
        try {
            String file = format("%s%s", Config.XML_FILE_PATH, fileName);
            out = new DataInputStream( new BufferedInputStream( Config.class.getResourceAsStream(file) ) );
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

    public static NodeList read(String nodeName){
        NodeList items = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse( out );
            doc.getDocumentElement().normalize();

            items = doc.getElementsByTagName(nodeName);

        } catch (ParserConfigurationException e) {
        	e.printStackTrace();
        } catch (SAXException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }

        return items;
    }

}
