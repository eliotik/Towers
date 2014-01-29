package org.game.towers.gfx.sprites;

import org.game.towers.configs.Config;

public class SpritesData {
	private static SpriteSheet[] spriteSheets = {
			new BlockSpriteSheet(Config.SPRITESHEET_FILE),
			new NpcSpriteSheet(Config.SPRITESHEET_NPCS_FILE) };

	public static Sprite GRASS = new Sprite("GRASS", 0, 0, 16, 16, "BLOCK");
	public static Sprite SAND = new Sprite("SAND", 1, 0, 16, 16, "BLOCK");
	public static Sprite STONE = new Sprite("STONE", 2, 0, 16, 16, "BLOCK");
	public static Sprite BUSH = new Sprite("BUSH", 3, 0, 16, 16, "BLOCK");

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
		case "NPC":
			return getSpriteSheets()[1];
		default:
			return getSpriteSheets()[0];
		}
	}
}
