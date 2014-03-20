package org.game.towers.gfx.sprites;

import org.game.towers.game.Config;

public class SpritesData {

	private static SpriteSheet[] spriteSheets = {
		new BlockSpriteSheet(Config.SPRITESHEET_FILE),
		new BlockSpriteSheet(Config.SPRITESHEET_PORTALS_FILE),
		new NpcSpriteSheet(Config.SPRITESHEET_NPCS_FILE),
		new NpcSpriteSheet(Config.SPRITESHEET_GUI_FILE),
		new NpcSpriteSheet(Config.SPRITESHEET_TOWERS_FILE),
		new NpcSpriteSheet(Config.SPRITESHEET_BUILDINGS_FILE),
		new NpcSpriteSheet(Config.SPRITESHEET_BULLETS_FILE)
	};

	public static Sprite VOID = new Sprite(0x000000);
	public static Sprite GRASS_0 = new Sprite("GRASS", 0, 0, 16, 16, "BLOCK");
	public static Sprite GRASS_1 = new Sprite("GRASS", 0, 1, 16, 16, "BLOCK");
	public static Sprite GRASS_2 = new Sprite("GRASS", 1, 1, 16, 16, "BLOCK");
	public static Sprite SAND = new Sprite("SAND", 1, 0, 16, 16, "BLOCK");
	public static Sprite STONE = new Sprite("STONE", 2, 0, 16, 16, "BLOCK");
	public static Sprite BUSH = new Sprite("BUSH", 3, 0, 16, 16, "BLOCK");

	public static Sprite ENTRANCE_0 = new Sprite("ENTRANCE", 0, 0, 16, 16, "PORTAL");
	public static Sprite ENTRANCE_1 = new Sprite("ENTRANCE", 1, 0, 16, 16, "PORTAL");
	public static Sprite ENTRANCE_2 = new Sprite("ENTRANCE", 2, 0, 16, 16, "PORTAL");
	public static Sprite ENTRANCE_3 = new Sprite("ENTRANCE", 3, 0, 16, 16, "PORTAL");
	public static Sprite ENTRANCE_4 = new Sprite("ENTRANCE", 4, 0, 16, 16, "PORTAL");
	public static Sprite ENTRANCE_5 = new Sprite("ENTRANCE", 5, 0, 16, 16, "PORTAL");
	public static Sprite ENTRANCE_6 = new Sprite("ENTRANCE", 6, 0, 16, 16, "PORTAL");
	public static Sprite EXIT_0 = new Sprite("EXIT", 0, 1, 16, 16, "PORTAL");
	public static Sprite EXIT_1 = new Sprite("EXIT", 1, 1, 16, 16, "PORTAL");
	public static Sprite EXIT_2 = new Sprite("EXIT", 2, 1, 16, 16, "PORTAL");
	public static Sprite EXIT_3 = new Sprite("EXIT", 3, 1, 16, 16, "PORTAL");
	public static Sprite EXIT_4 = new Sprite("EXIT", 4, 1, 16, 16, "PORTAL");
	public static Sprite EXIT_5 = new Sprite("EXIT", 5, 1, 16, 16, "PORTAL");
	public static Sprite EXIT_6 = new Sprite("EXIT", 6, 1, 16, 16, "PORTAL");

	public static Sprite NPC_BULB_0 = new Sprite("NPC_BULB", 0, 0, 16, 16, "NPC");
	public static Sprite NPC_BULB_1 = new Sprite("NPC_BULB", 1, 0, 16, 16, "NPC");
	public static Sprite NPC_BULB_2 = new Sprite("NPC_BULB", 2, 0, 16, 16, "NPC");
	public static Sprite NPC_BULB_3 = new Sprite("NPC_BULB", 3, 0, 16, 16, "NPC");
	public static Sprite NPC_BULB_4 = new Sprite("NPC_BULB", 4, 0, 16, 16, "NPC");
	public static Sprite NPC_BULB_5 = new Sprite("NPC_BULB", 5, 0, 16, 16, "NPC");

	public static Sprite NPC_DRONE_0 = new Sprite("NPC_DRONE", 0, 1, 16, 16, "NPC");
	public static Sprite NPC_DRONE_1 = new Sprite("NPC_DRONE", 1, 1, 16, 16, "NPC");
	public static Sprite NPC_DRONE_2 = new Sprite("NPC_DRONE", 2, 1, 16, 16, "NPC");

	public static Sprite NPC_VENT_0 = new Sprite("NPC_VENT", 0, 2, 16, 16, "NPC");
	public static Sprite NPC_VENT_1 = new Sprite("NPC_VENT", 1, 2, 16, 16, "NPC");
	public static Sprite NPC_VENT_2 = new Sprite("NPC_VENT", 2, 2, 16, 16, "NPC");

	public static Sprite NPC_DEAD_0 = new Sprite("NPC_DEAD", 0, 2, 16, 16, "BLOCK");
	public static Sprite NPC_DEAD_1 = new Sprite("NPC_DEAD", 1, 2, 16, 16, "BLOCK");
	public static Sprite NPC_DEAD_2 = new Sprite("NPC_DEAD", 2, 2, 16, 16, "BLOCK");
	public static Sprite NPC_DEAD_3 = new Sprite("NPC_DEAD", 0, 3, 16, 16, "BLOCK");
	public static Sprite NPC_DEAD_4 = new Sprite("NPC_DEAD", 1, 3, 16, 16, "BLOCK");
	public static Sprite NPC_DEAD_5 = new Sprite("NPC_DEAD", 2, 3, 16, 16, "BLOCK");

	public static Sprite TOWER_BULB_0 = new Sprite("TOWER_BULB", 0, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_1 = new Sprite("TOWER_BULB", 1, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_2 = new Sprite("TOWER_BULB", 2, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_3 = new Sprite("TOWER_BULB", 3, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_4 = new Sprite("TOWER_BULB", 4, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_5 = new Sprite("TOWER_BULB", 5, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_6 = new Sprite("TOWER_BULB", 6, 0, 16, 16, "TOWER");
	public static Sprite TOWER_BULB_7 = new Sprite("TOWER_BULB", 7, 0, 16, 16, "TOWER");

	public static Sprite TOWER_BLOCKPOST_0 = new Sprite("TOWER_BLOCKPOST", 0, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_1 = new Sprite("TOWER_BLOCKPOST", 1, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_2 = new Sprite("TOWER_BLOCKPOST", 2, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_3 = new Sprite("TOWER_BLOCKPOST", 3, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_4 = new Sprite("TOWER_BLOCKPOST", 4, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_5 = new Sprite("TOWER_BLOCKPOST", 5, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_6 = new Sprite("TOWER_BLOCKPOST", 6, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_7 = new Sprite("TOWER_BLOCKPOST", 7, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_8 = new Sprite("TOWER_BLOCKPOST", 8, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_9 = new Sprite("TOWER_BLOCKPOST", 9, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_10 = new Sprite("TOWER_BLOCKPOST", 10, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_11 = new Sprite("TOWER_BLOCKPOST", 11, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_12 = new Sprite("TOWER_BLOCKPOST", 12, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_13 = new Sprite("TOWER_BLOCKPOST", 13, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_14 = new Sprite("TOWER_BLOCKPOST", 14, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_15 = new Sprite("TOWER_BLOCKPOST", 15, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_16 = new Sprite("TOWER_BLOCKPOST", 16, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_17 = new Sprite("TOWER_BLOCKPOST", 17, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_18 = new Sprite("TOWER_BLOCKPOST", 18, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_19 = new Sprite("TOWER_BLOCKPOST", 19, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_20 = new Sprite("TOWER_BLOCKPOST", 20, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_21 = new Sprite("TOWER_BLOCKPOST", 21, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_22 = new Sprite("TOWER_BLOCKPOST", 22, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_23 = new Sprite("TOWER_BLOCKPOST", 23, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_24 = new Sprite("TOWER_BLOCKPOST", 24, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_25 = new Sprite("TOWER_BLOCKPOST", 25, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_26 = new Sprite("TOWER_BLOCKPOST", 26, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_27 = new Sprite("TOWER_BLOCKPOST", 27, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_28 = new Sprite("TOWER_BLOCKPOST", 28, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_29 = new Sprite("TOWER_BLOCKPOST", 29, 1, 16, 16, "TOWER");
	public static Sprite TOWER_BLOCKPOST_30 = new Sprite("TOWER_BLOCKPOST", 30, 1, 16, 16, "TOWER");

	public static Sprite HEALTH_MAX  = new Sprite("HEALTH_MAX", 0, 0, 16, 16, "GUI");
	public static Sprite HEALTH_90   = new Sprite("HEALTH_90", 1, 0, 16, 16, "GUI");
	public static Sprite HEALTH_80   = new Sprite("HEALTH_80", 2, 0, 16, 16, "GUI");
	public static Sprite HEALTH_70   = new Sprite("HEALTH_70", 3, 0, 16, 16, "GUI");
	public static Sprite HEALTH_60   = new Sprite("HEALTH_60", 4, 0, 16, 16, "GUI");
	public static Sprite HEALTH_50   = new Sprite("HEALTH_50", 5, 0, 16, 16, "GUI");
	public static Sprite HEALTH_40   = new Sprite("HEALTH_40", 6, 0, 16, 16, "GUI");
	public static Sprite HEALTH_30   = new Sprite("HEALTH_30", 7, 0, 16, 16, "GUI");
	public static Sprite HEALTH_20   = new Sprite("HEALTH_20", 8, 0, 16, 16, "GUI");
	public static Sprite HEALTH_10   = new Sprite("HEALTH_10", 9, 0, 16, 16, "GUI");
	public static Sprite HEALTH_DEAD = new Sprite("HEALTH_DEAD", 10, 0, 16, 16, "GUI");

	public static Sprite MONEY = new Sprite("MONEY", 0, 2, 16, 16, "GUI");
	public static Sprite HEART = new Sprite("HEART", 1, 2, 16, 16, "GUI");
	public static Sprite RESOURCE = new Sprite("RESOURCE", 2, 2, 16, 16, "GUI");

	public static Sprite STORE_BG_LEFT = new Sprite("STORE_BG_LEFT", 0, 1, 16, 16, "GUI");
	public static Sprite STORE_BG_MIDDLE = new Sprite("STORE_BG_MIDDLE", 1, 1, 16, 16, "GUI");
	public static Sprite STORE_BG_RIGHT = new Sprite("STORE_BG_RIGHT", 2, 1, 16, 16, "GUI");

	public static Sprite BULLET_BASE_DOT_0 = new Sprite("BULLET_BASE_DOT", 0, 1, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_DOT_1 = new Sprite("BULLET_BASE_DOT", 1, 1, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_DOT_2 = new Sprite("BULLET_BASE_DOT", 2, 1, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_DOT_3 = new Sprite("BULLET_BASE_DOT", 3, 1, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_DOT_4 = new Sprite("BULLET_BASE_DOT", 4, 1, 16, 16, "BULLET");

	public static Sprite BULLET_BASE_TRIPPLE_0 = new Sprite("BULLET_BASE_TRIPPLE", 0, 0, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_TRIPPLE_1 = new Sprite("BULLET_BASE_TRIPPLE", 1, 0, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_TRIPPLE_2 = new Sprite("BULLET_BASE_TRIPPLE", 2, 0, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_TRIPPLE_3 = new Sprite("BULLET_BASE_TRIPPLE", 3, 0, 16, 16, "BULLET");
	public static Sprite BULLET_BASE_TRIPPLE_4 = new Sprite("BULLET_BASE_TRIPPLE", 4, 0, 16, 16, "BULLET");

	public static SpriteSheet[] getSpriteSheets() {
		return spriteSheets;
	}

	public static void setSpriteSheets(SpriteSheet[] s) {
		spriteSheets = s;
	}

	public static SpriteSheet getSpriteSheet(String type) {
		switch (type) {
		case "PORTAL":
			return getSpriteSheets()[1];
		case "NPC":
			return getSpriteSheets()[2];
		case "GUI":
			return getSpriteSheets()[3];
		case "TOWER":
			return getSpriteSheets()[4];
		case "BUILDING":
			return getSpriteSheets()[5];
		case "BULLET":
			return getSpriteSheets()[6];
		default:
		case "BLOCK":
			return getSpriteSheets()[0];
		}
	}
}
