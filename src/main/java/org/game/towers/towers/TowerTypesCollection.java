package org.game.towers.towers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.gfx.Colors;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Rectangle;
import java.util.*;

public class TowerTypesCollection {

	private static ArrayList<TowerType> items = new ArrayList<TowerType>();

    public static void load() {
    	items = new ArrayList<TowerType>();
        XmlReader.getStreamFromFile(Config.TOWERS_FILE);
        NodeList listOfElements = XmlReader.read(Config.TOWER_NODE_NAME);

        for( int j=0; j < listOfElements.getLength(); ++j ) {
            Node firstNode=listOfElements.item(j);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                org.w3c.dom.Element elemj = (org.w3c.dom.Element) firstNode;
                TowerType item = new TowerType(Game.instance.getWorld().getLevel());

                item.setId(elemj.getAttribute("id").toString());

                item.setTypeName(elemj.getAttribute("name").toString());
                item.setType(elemj.getAttribute("type").toString());

                item.setTileX(Integer.parseInt(elemj.getAttribute("tile_x").toString()));
                item.setTileY(Integer.parseInt(elemj.getAttribute("tile_y").toString()));

                item.setHealth(Integer.parseInt(elemj.getAttribute("health").toString()));
                item.setArmour(Integer.parseInt(elemj.getAttribute("armour").toString()));
                item.setSpeed(Double.parseDouble(elemj.getAttribute("speed").toString()));
                item.setDamage(Integer.parseInt(elemj.getAttribute("damage").toString()));

                item.setGeo(new Rectangle(
                        0,
                        0,
                		Integer.parseInt(elemj.getAttribute("width").toString()),
                		Integer.parseInt(elemj.getAttribute("height").toString())
            		)
                );

                item.setRadius(Integer.parseInt(elemj.getAttribute("radius").toString()));

                getItems().add(item);
            }
        }
        Game.debug(DebugLevel.INFO, "Loaded Towers types: " + getItems().size());
    }

	public static ArrayList<TowerType> getItems() {
		return items;
	}
}
