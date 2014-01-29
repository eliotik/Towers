package org.game.towers.gfx.sprites;

import org.game.towers.configs.Config;

public class SpritesData {
	private static SpriteSheet[] spriteSheets = {
			new BlockSpriteSheet(Config.SPRITESHEET_FILE),
			new NpcSpriteSheet(Config.SPRITESHEET_NPCS_FILE) };

	private static Sprite[] sprites = new Sprite[256];

	public static SpriteSheet[] getSpriteSheets() {
		return spriteSheets;
	}

	public static void setSpriteSheets(SpriteSheet[] s) {
		spriteSheets = s;
	}

	public static Sprite[] getSprites() {
		return sprites;
	}

	public static void setSprites(Sprite[] s) {
		sprites = s;
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
