package org.game.towers.npcs;

import org.game.towers.configs.Config;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Rectangle;
import java.util.*;

public class NpcTypesCollection {
	
	private static ArrayList<NpcType> items = new ArrayList<NpcType>();

    public static void load() {
        XmlReader.getStreamFromFile(Config.NPCS_FILE);
        NodeList listOfElements = XmlReader.read(Config.NPC_NODE_NAME);
        
        for( int j=0; j < listOfElements.getLength(); ++j ) {
            Node firstNode=listOfElements.item(j);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                org.w3c.dom.Element elemj = (org.w3c.dom.Element) firstNode;
                
                NpcType item = new NpcType();

                item.setTypeName(elemj.getAttribute("name").toString());
                item.setType(elemj.getAttribute("type").toString());
                
                item.setId(elemj.getAttribute("id").toString());
                
                item.setTileX(Integer.parseInt(elemj.getAttribute("tile_x").toString()));
                item.setTileY(Integer.parseInt(elemj.getAttribute("tile_y").toString()));
                
                item.setHealth(Integer.parseInt(elemj.getAttribute("health").toString()));
                item.setArmour(Integer.parseInt(elemj.getAttribute("armour").toString()));
                item.setSpeed(Integer.parseInt(elemj.getAttribute("speed").toString()));
                item.setDamage(Integer.parseInt(elemj.getAttribute("damage").toString()));
                
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
