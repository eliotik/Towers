package org.game.towers.buildings;

import org.game.towers.configs.Config;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Rectangle;
import java.util.*;

public class BuildingTypesCollection {
	
	private static ArrayList<BuildingType> items = new ArrayList<BuildingType>();

    public static void load() {
        XmlReader.getStreamFromFile(Config.TOWERS_FILE);
        NodeList listOfElements = XmlReader.read(Config.TOWER_NODE_NAME);
        
        for( int j=0; j < listOfElements.getLength(); ++j ) {
            Node firstNode=listOfElements.item(j);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                org.w3c.dom.Element elemj = (org.w3c.dom.Element) firstNode;
                BuildingType item = new BuildingType();

                item.setId(elemj.getAttribute("id").toString());
                
                item.setTypeName(elemj.getAttribute("name").toString());
                item.setType(elemj.getAttribute("type").toString());
                
                item.setTileX(Integer.parseInt(elemj.getAttribute("tile_x").toString()));
                item.setTileY(Integer.parseInt(elemj.getAttribute("tile_y").toString()));
                
                item.setHealth(Integer.parseInt(elemj.getAttribute("health").toString()));
                item.setArmour(Integer.parseInt(elemj.getAttribute("armour").toString()));
                item.setSpeed(Integer.parseInt(elemj.getAttribute("speed").toString()));
                item.setValue(Double.parseDouble(elemj.getAttribute("damage").toString()));
                
                item.setSize(new Rectangle(
                		Integer.parseInt(elemj.getAttribute("width").toString()), 
                		Integer.parseInt(elemj.getAttribute("height").toString())
            		)
                );
                
                items.add(item);
            }
        }
    }
}
