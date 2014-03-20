package org.game.towers.units.bullets;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.units.npcs.Npcs;
import org.game.towers.units.towers.Tower;
import org.game.towers.workers.Utils;
import org.game.towers.workers.XmlReader;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

public class BulletsCollection {

	private static ArrayList<Bullet> items = new ArrayList<Bullet>();

    public static void load() {
    	items = new ArrayList<Bullet>();
        XmlReader.getStreamFromFile(Config.BULLETS_FILE);
        NodeList listOfElements = XmlReader.read(Config.BULLET_NODE_NAME);

        for( int j=0; j < listOfElements.getLength(); ++j ) {
            Node firstNode=listOfElements.item(j);
            if( firstNode.getNodeType() == Node.ELEMENT_NODE ) {
                org.w3c.dom.Element elemj = (org.w3c.dom.Element) firstNode;

                Bullet item = new Bullet();

                item.setId(elemj.getAttribute("id").toString());

                item.setTypeName(elemj.getAttribute("name").toString());
                item.setType(elemj.getAttribute("type").toString());

                item.setSpeed(Double.parseDouble(elemj.getAttribute("speed").toString()));
                item.setAnimationSwitchDelay(Integer.parseInt(elemj.getAttribute("animation_switch_delay").toString()));
                item.setAnimationStartDelay(Integer.parseInt(elemj.getAttribute("animation_start_delay").toString()));

                item.setSprites(getSprites(item.getId()));

                getItems().add(item);
                Game.debug(DebugLevel.INFO, "Added Bullet type: " + item.getId());
            }
        }
        Game.debug(DebugLevel.INFO, "Loaded Bullets types: " + getItems().size());
    }

	private static List<Sprite> getSprites(String id) {
		switch(id) {
		case Bullets.BASE_DOT: return getBaseDotSprites();
		case Bullets.BASE_TRIPPLE: return getBaseTrippleSprites();
		}
		return new ArrayList<Sprite>();
	}

	private static List<Sprite> getBaseDotSprites() {
		List<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(SpritesData.BULLET_BASE_DOT_0);
		sprites.add(SpritesData.BULLET_BASE_DOT_1);
		sprites.add(SpritesData.BULLET_BASE_DOT_2);
		sprites.add(SpritesData.BULLET_BASE_DOT_3);
		sprites.add(SpritesData.BULLET_BASE_DOT_4);
		return sprites;
	}

	private static List<Sprite> getBaseTrippleSprites() {
		List<Sprite> sprites = new ArrayList<Sprite>();
		sprites.add(SpritesData.BULLET_BASE_TRIPPLE_0);
		sprites.add(SpritesData.BULLET_BASE_TRIPPLE_1);
		sprites.add(SpritesData.BULLET_BASE_TRIPPLE_2);
		sprites.add(SpritesData.BULLET_BASE_TRIPPLE_3);
		sprites.add(SpritesData.BULLET_BASE_TRIPPLE_4);
		return sprites;
	}

	public static ArrayList<Bullet> getItems() {
		return items;
	}
}
