package org.game.towers.units.collections;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.units.towers.Tower;
import org.game.towers.units.towers.Towers;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

public class TowersCollection {

	private static ArrayList<Tower> items = new ArrayList<Tower>();

    public static void load() {
    	items = new ArrayList<Tower>();
        XmlReader.getStreamFromFile(Config.TOWERS_FILE);
        NodeList listOfElements = XmlReader.read(Config.TOWER_NODE_NAME);

        for( int j=0; j < listOfElements.getLength(); ++j ) {
            Node firstNode=listOfElements.item(j);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                org.w3c.dom.Element elemj = (org.w3c.dom.Element) firstNode;

                Tower item = new Tower();

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
                item.setPrice(Integer.parseInt(elemj.getAttribute("resources").toString()));
                item.setCanShoot(Boolean.parseBoolean(elemj.getAttribute("can_shoot").toString()));
                item.setRadarViewSize(Integer.parseInt(elemj.getAttribute("radar_view_size").toString()));
                item.setShootingDelay(Double.parseDouble(elemj.getAttribute("shooting_delay").toString()));
                item.setBulletType(elemj.getAttribute("bullet_type").toString());
                item.setModificator(elemj.getAttribute("modificator").toString());

                item.setSprites(getSprites(item.getId()));

                getItems().add(item);
                Game.debug(DebugLevel.INFO, "Added Tower type: " + item.getId());
            }
        }
        Game.debug(DebugLevel.INFO, "Loaded Towers types: " + getItems().size());
    }

	private static List<Sprite> getSprites(String id) {
		switch(id) {
		case Towers.BULB: return getBulbSprites();
		case Towers.BLOCKPOST: return getBlockpostSprites();
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

	private static List<Sprite> getBlockpostSprites() {
		List<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(SpritesData.TOWER_BLOCKPOST_0);
		sprites.add(SpritesData.TOWER_BLOCKPOST_1);
		sprites.add(SpritesData.TOWER_BLOCKPOST_2);
		sprites.add(SpritesData.TOWER_BLOCKPOST_3);
		sprites.add(SpritesData.TOWER_BLOCKPOST_4);
		sprites.add(SpritesData.TOWER_BLOCKPOST_5);
		sprites.add(SpritesData.TOWER_BLOCKPOST_6);
		sprites.add(SpritesData.TOWER_BLOCKPOST_7);
		sprites.add(SpritesData.TOWER_BLOCKPOST_8);
		sprites.add(SpritesData.TOWER_BLOCKPOST_9);
		sprites.add(SpritesData.TOWER_BLOCKPOST_10);
		sprites.add(SpritesData.TOWER_BLOCKPOST_11);
		sprites.add(SpritesData.TOWER_BLOCKPOST_12);
		sprites.add(SpritesData.TOWER_BLOCKPOST_13);
		sprites.add(SpritesData.TOWER_BLOCKPOST_14);
		sprites.add(SpritesData.TOWER_BLOCKPOST_15);
		sprites.add(SpritesData.TOWER_BLOCKPOST_16);
		sprites.add(SpritesData.TOWER_BLOCKPOST_17);
		sprites.add(SpritesData.TOWER_BLOCKPOST_18);
		sprites.add(SpritesData.TOWER_BLOCKPOST_19);
		sprites.add(SpritesData.TOWER_BLOCKPOST_20);
		sprites.add(SpritesData.TOWER_BLOCKPOST_21);
		sprites.add(SpritesData.TOWER_BLOCKPOST_22);
		sprites.add(SpritesData.TOWER_BLOCKPOST_23);
		sprites.add(SpritesData.TOWER_BLOCKPOST_24);
		sprites.add(SpritesData.TOWER_BLOCKPOST_25);
		sprites.add(SpritesData.TOWER_BLOCKPOST_26);
		sprites.add(SpritesData.TOWER_BLOCKPOST_27);
		sprites.add(SpritesData.TOWER_BLOCKPOST_28);
		sprites.add(SpritesData.TOWER_BLOCKPOST_29);
		sprites.add(SpritesData.TOWER_BLOCKPOST_30);
		sprites.add(SpritesData.TOWER_BLOCKPOST_0);
		return sprites;
	}

	public static ArrayList<Tower> getItems() {
		return items;
	}
}
