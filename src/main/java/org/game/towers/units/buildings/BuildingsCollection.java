package org.game.towers.units.buildings;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

public class BuildingsCollection {

	private static ArrayList<Building> items = new ArrayList<Building>();

    public static void load() {
    	items = new ArrayList<Building>();
        XmlReader.getStreamFromFile(Config.TOWERS_FILE);
        NodeList listOfElements = XmlReader.read(Config.TOWER_NODE_NAME);

        for( int j=0; j < listOfElements.getLength(); ++j ) {
            Node firstNode=listOfElements.item(j);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                org.w3c.dom.Element elemj = (org.w3c.dom.Element) firstNode;
                Building item = new Building();

                item.setId(elemj.getAttribute("id").toString());

                item.setTypeName(elemj.getAttribute("name").toString());
                item.setType(elemj.getAttribute("type").toString());

                item.setHealth(Integer.parseInt(elemj.getAttribute("health").toString()));
                item.setArmour(Integer.parseInt(elemj.getAttribute("armour").toString()));
                item.setSpeed(Double.parseDouble(elemj.getAttribute("speed").toString()));
                item.setValue(Double.parseDouble(elemj.getAttribute("damage").toString()));

                item.setGeo(new Rectangle(
                        0,
                        0,
                		Integer.parseInt(elemj.getAttribute("width").toString()),
                		Integer.parseInt(elemj.getAttribute("height").toString())
            		)
                );

                item.setMinCollisionBox(
                		new Point(
                				Integer.parseInt(elemj.getAttribute("collision_box_min_x").toString()),
                				Integer.parseInt(elemj.getAttribute("collision_box_min_y").toString())
                				)
                		);

                item.setMaxCollisionBox(
	            		new Point(
		        				Integer.parseInt(elemj.getAttribute("collision_box_max_x").toString()),
		        				Integer.parseInt(elemj.getAttribute("collision_box_max_y").toString())
	            				)
                		);

                item.setRadarViewSize(Integer.parseInt(elemj.getAttribute("radar_view_size;").toString()));

                getItems().add(item);
            }
        }
        Game.debug(DebugLevel.INFO, "Loaded Buildings types: " + getItems().size());
    }

	public static ArrayList<Building> getItems() {
		return items;
	}
}
