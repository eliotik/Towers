package org.game.towers.gfx.sprites;

import org.game.towers.configs.Config;

public class SpritesData {
	private static SpriteSheet[] spriteSheets = {
			new BlockSpriteSheet(Config.SPRITESHEET_FILE),
			new BlockSpriteSheet(Config.SPRITESHEET_PORTALS_FILE),
			new NpcSpriteSheet(Config.SPRITESHEET_NPCS_FILE) };

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

	public static SpriteSheet[] getSpriteSheets() {
		return spriteSheets;
	}

	public static void setSpriteSheets(SpriteSheet[] s) {
		spriteSheets = s;
	}

	public static SpriteSheet getSpriteSheet(String type) {
		switch (type) {
		case "BLOCK":
			return getSpriteSheets()[0];
		case "PORTAL":
			return getSpriteSheets()[1];
		case "NPC":
			return getSpriteSheets()[2];
		default:
			return getSpriteSheets()[0];
		}
	}
}
