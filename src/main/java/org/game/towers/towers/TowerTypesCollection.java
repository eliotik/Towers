package org.game.towers.towers;

import org.game.towers.configs.Config;
import org.game.towers.configs.Npcs;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.workers.Utils;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Point;
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

                TowerType item = new TowerType();

                item.setId(elemj.getAttribute("id").toString());

                item.setTypeName(elemj.getAttribute("name").toString());
                item.setType(elemj.getAttribute("type").toString());

                item.setHealth(Integer.parseInt(elemj.getAttribute("health").toString()));
                item.setMaxHealth(item.getHealth());
                item.setArmour(Integer.parseInt(elemj.getAttribute("armour").toString()));
                item.setSpeed(Double.parseDouble(elemj.getAttribute("speed").toString()));
                item.setDamage(Integer.parseInt(elemj.getAttribute("damage").toString()));
                item.setAnimationSwitchDelay(Integer.parseInt(elemj.getAttribute("animation_switch_delay").toString()));
                item.setAnimationStartDelay(Integer.parseInt(elemj.getAttribute("animation_start_delay").toString()));

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

                item.setRadius(Integer.parseInt(elemj.getAttribute("radius").toString()));
                item.setPrice(Integer.parseInt(elemj.getAttribute("price").toString()));

                item.setRadarViewSize(Integer.parseInt(elemj.getAttribute("radar_view_size").toString()));

                item.setSprites(getSprites(item.getId()));

                getItems().add(item);
                Game.debug(DebugLevel.INFO, "Added Tower type: " + item.getId());
            }
        }
        Game.debug(DebugLevel.INFO, "Loaded Towers types: " + getItems().size());
    }

	private static List<Sprite> getSprites(String id) {
		switch(id) {
		case Npcs.BULB: return getBulbSprites();
		}
		return new ArrayList<Sprite>();
	}

	private static List<Sprite> getBulbSprites() {
		List<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(SpritesData.TOWER_BULB_0);
		sprites.add(SpritesData.TOWER_BULB_1);
		sprites.add(SpritesData.TOWER_BULB_2);
		sprites.add(SpritesData.TOWER_BULB_3);
		sprites.add(SpritesData.TOWER_BULB_4);
		sprites.add(SpritesData.TOWER_BULB_5);
		sprites.add(SpritesData.TOWER_BULB_6);
		sprites.add(SpritesData.TOWER_BULB_7);
		return sprites;
	}

	public static ArrayList<TowerType> getItems() {
		return items;
	}
}
