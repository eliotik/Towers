package org.game.towers.bullets;

import org.game.towers.configs.Bullets;
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

public class BulletTypesCollection {

	private static ArrayList<BulletType> items = new ArrayList<BulletType>();

    public static void init() {
    	items = new ArrayList<BulletType>();

    	BulletType b = new BulletType();
    	b.setType(Bullets.BASE_DOT);
    	b.setSprites(getBaseDotSprites());
    	items.add(b);
    	Game.debug(DebugLevel.INFO, "Added Bullet type: " + Bullets.BASE_DOT);

    	b = new BulletType();
    	b.setType(Bullets.BASE_TRIPPLE);
    	b.setSprites(getBaseTrippleSprites());
    	items.add(b);
        Game.debug(DebugLevel.INFO, "Added Bullet type: " + Bullets.BASE_TRIPPLE);

        Game.debug(DebugLevel.INFO, "Initialized Bullets types: " + getItems().size());
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

	public static ArrayList<BulletType> getItems() {
		return items;
	}
}
