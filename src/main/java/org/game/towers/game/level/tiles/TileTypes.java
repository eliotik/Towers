package org.game.towers.game.level.tiles;

import org.game.towers.game.Config;
import org.game.towers.game.level.Level;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.workers.Utils;

public class TileTypes {

	public static final TileTypes[] types = { new TileTypes(Config.TILE_GRASS),
			new TileTypes(Config.TILE_SAND), new TileTypes(Config.TILE_STONE),
			new TileTypes(Config.TILE_BUSH), new TileTypes(Config.TILE_ENTRANCE),
			new TileTypes(Config.TILE_EXIT), new TileTypes("VOID") };
	private String type;

	// public static final Tile VOID = new BasicSolidTile(0, 0, 0,
	// Colors.get(000, -1, -1, -1), 0xFF000000);
	// public static final Tile STONE = new AnimatedTile(1, new int[][] {{0, 3},
	// {1, 3}, {2, 3}, {3, 3}, {0, 3}},
	// Colors.get(131, 333, 339, 318), true, 0xFF555555, 80, 4000);
	// public static final Tile GRASS = new AnimatedTile(2, new int[][] {{0, 4},
	// {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}, {8, 4}, {9, 4},
	// {10, 4}, {11, 4}, {12, 4}, {13, 4}, {14, 4}, {15, 4}, {16, 4}, {0, 4}},
	// Colors.get(-1, 131, 141, -1), false, 0xFF00FF00, 80, 4000);
	// public static final Tile BUSH = new AnimatedTile(3, new int[][] {{0, 5},
	// {1, 5}, {2, 5}, {1, 5}}, Colors.get(161, 151, 141, 131), true,
	// 0xFF69BD45, 3500);
	// public static final Tile ENTRANCE = new AnimatedTile(4, new int[][] {{0,
	// 6}, {1, 6}, {2, 6}, {3, 6}, {4, 6}, {5, 6}, {6, 6}, {7, 6}, {8, 6}, {9,
	// 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6}, {15, 6}, {0, 6}},
	// Colors.get(335, 131, 555, 777), false, 0xFFFAA71A, 100);
	// public static final Tile EXIT = new AnimatedTile(5, new int[][] {{0, 7},
	// {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}, {7, 7}, {8, 7}, {9, 7},
	// {10, 7}, {11, 7}, {12, 7}, {13, 7}, {14, 7}, {15, 7}},
	// Colors.get(335, 131, 555, 777), false, 0xFFFF0000, 100);
	// public static final Tile SAND = new AnimatedTile(6, new int[][] {{0, 8},
	// {1, 8}, {2, 8}, {3, 8}, {2, 8}, {1, 8}},
	// Colors.get(433, 455, 444, 552), false, 0xFFFFEA00, 2000, 500);

	public TileTypes(String type) {
		setType(type);
	}

	public static TileTypes get(String type) {
		for (TileTypes tt : types) {
			if (tt.type.equals(type)) {
				return tt;
			}
		}
		return types[types.length - 1];
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
				Config.TILE_EXIT, 0xffff0000, false, false, x, y, 170);
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
				Config.TILE_ENTRANCE, 0xfffaa71a, false, false, x, y, 170);
	}

	private Tile getTileBush(Level level, int x, int y, boolean staticSprite) {
		int mirrorMask = 0x00;
		if (Utils.randInt(0, 2) == 1) {
			mirrorMask = 0x01;
		}
		return new BasicTile(level, SpritesData.BUSH, Config.TILE_BUSH, 0xff69bd45, true, false, x, y, mirrorMask);
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
		return new BasicTile(level, SpritesData.STONE, Config.TILE_STONE, 0xff555555, true, false, x, y, mirrorMask);
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
		return new BasicTile(level, SpritesData.SAND, Config.TILE_SAND, 0xffffea00, false, false, x, y, mirrorMask);
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
		return new BasicTile(level, sprite, Config.TILE_GRASS, 0xff00ff00, false, false, x, y, mirrorMask);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
