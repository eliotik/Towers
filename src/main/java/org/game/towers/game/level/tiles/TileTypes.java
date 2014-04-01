package org.game.towers.game.level.tiles;

import org.game.towers.game.Config;
import org.game.towers.game.level.Level;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.workers.Utils;

public class TileTypes {

	private static final TileTypes[] types = { new TileTypes(Config.TILE_GRASS),
			new TileTypes(Config.TILE_SAND), new TileTypes(Config.TILE_STONE),
			new TileTypes(Config.TILE_BUSH), new TileTypes(Config.TILE_ENTRANCE),
			new TileTypes(Config.TILE_EXIT), new TileTypes("VOID"),
			new TileTypes(Config.TILE_ICE) };
	private String type;

	public TileTypes(String type) {
		setType(type);
	}

	public static TileTypes get(String type) {
		for (TileTypes tt : getTypes()) {
			if (tt.type.equals(type)) {
				return tt;
			}
		}
		return getTypes()[getTypes().length - 1];
	}

	public Tile get() {
		return get(null, 0, 0, false);
	}

	public Tile get(Level level, int x, int y, boolean staticSprite) {
		switch(type) {
		case Config.TILE_GRASS:    return getTileGrass(level, x, y, staticSprite);
		case Config.TILE_SAND:     return getTileSand(level, x, y, staticSprite);
		case Config.TILE_STONE:    return getTileStone(level, x, y, staticSprite);
		case Config.TILE_BUSH:     return getTileBush(level, x, y, staticSprite);
		case Config.TILE_ENTRANCE: return getTileEntrance(level, x, y, staticSprite);
		case Config.TILE_EXIT:     return getTileExit(level, x, y, staticSprite);
		case Config.TILE_ICE:      return getTileIce(level, x, y, staticSprite);
		default:                   return getTileVoid(level, x, y, staticSprite);
		}
	}

	private Tile getTileVoid(Level level, int x, int y, boolean staticSprite) {
		return new VoidTile(level, SpritesData.VOID, x, y);
	}

	private Tile getTileExit(Level level, int x, int y, boolean staticSprite) {
		return new AnimatedTile(level,
				new Sprite[] {
					SpritesData.EXIT_0,
					SpritesData.EXIT_1,
					SpritesData.EXIT_2,
					SpritesData.EXIT_3,
					SpritesData.EXIT_4,
					SpritesData.EXIT_5
				},
				Config.TILE_EXIT, 0xffff0000, false, false, x, y, 170, 0);
	}

	private Tile getTileEntrance(Level level, int x, int y, boolean staticSprite) {
		return new AnimatedTile(level,
				new Sprite[] {
					SpritesData.ENTRANCE_0,
					SpritesData.ENTRANCE_1,
					SpritesData.ENTRANCE_2,
					SpritesData.ENTRANCE_3,
					SpritesData.ENTRANCE_4,
					SpritesData.ENTRANCE_5
				},
				Config.TILE_ENTRANCE, 0xfffaa71a, false, false, x, y, 170, 0);
	}

	private Tile getTileBush(Level level, int x, int y, boolean staticSprite) {
		int mirrorMask = 0x00;
		if (Utils.randInt(0, 2) == 1) {
			mirrorMask = 0x01;
		}
		return new BasicTile(level, SpritesData.BUSH, Config.TILE_BUSH, 0xff69bd45, true, false, x, y, mirrorMask, 80);
	}

	private Tile getTileStone(Level level, int x, int y, boolean staticSprite) {
		int mirrorMask = 0x00;
		switch (Utils.randInt(0, 2)) {
		case 1:
			mirrorMask = 0x01;
			break;
		case 2:
			mirrorMask = 0x02;
			break;
		}
		return new BasicTile(level, SpritesData.STONE, Config.TILE_STONE, 0xff555555, true, false, x, y, mirrorMask, 100);
	}

	private Tile getTileIce(Level level, int x, int y, boolean staticSprite) {
		int mirrorMask = 0x00;
		switch (Utils.randInt(0, 2)) {
		case 1:
			mirrorMask = 0x01;
			break;
		}
		return new BasicTile(level, SpritesData.ICE, Config.TILE_ICE, 0xff51b1e6, true, false, x, y, mirrorMask, 50);
	}

	private Tile getTileSand(Level level, int x, int y, boolean staticSprite) {
		int mirrorMask = 0x00;
		switch (Utils.randInt(0, 2)) {
		case 1:
			mirrorMask = 0x01;
			break;
		case 2:
			mirrorMask = 0x02;
			break;
		}
		return new BasicTile(level, SpritesData.SAND, Config.TILE_SAND, 0xffffea00, false, false, x, y, mirrorMask, 0);
	}

	private Tile getTileGrass(Level level, int x, int y, boolean staticSprite) {
		Sprite sprite = SpritesData.GRASS_0;
		if (!staticSprite) {
			switch (Utils.randInt(0, 8)) {
			case 1:
			case 2:
				sprite = SpritesData.GRASS_1;
				break;
			case 3:
			case 4:
			case 5:
				sprite = SpritesData.GRASS_2;
				break;
			default:
			case 0:
				sprite = SpritesData.GRASS_0;
				break;
			}
		}
		int mirrorMask = 0x00;
		switch (Utils.randInt(0, 2)) {
		case 1:
			mirrorMask = 0x01;
			break;
		case 2:
			mirrorMask = 0x02;
			break;
		}
		return new BasicTile(level, sprite, Config.TILE_GRASS, 0xff00ff00, false, false, x, y, mirrorMask, 0);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static TileTypes[] getTypes() {
		return types;
	}
}
