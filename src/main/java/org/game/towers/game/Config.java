
package org.game.towers.game;
import java.awt.Dimension;
import java.io.File;

public class Config {
	public static final String GAME_VERSION = "0.0.1";
	public static final String GAME_NAME = "Towers " + GAME_VERSION;
	public static final String DEFAULT_WORLD_NAME = "towers_default_world";
	public static final String HOME_DIR = System.getProperty("user.home") + File.separator + ".towersdata" + File.separator;
	public static final String WORLD_DIR = HOME_DIR + "saves" + File.separator;
	public static final Boolean DEFAULT_PAUSE_ON_FOCUS_LOST = false;

	public static final String XML_FILE_PATH = "/configs/";
	public static final String IMAGES_FILE_PATH = "/images/";

	public static final String NPCS_FILE = "npcs.xml";
	public static final String TOWERS_FILE = "towers.xml";
	public static final String BUILDINGS_FILE = "buildings.xml";
	public static final String BULLETS_FILE = "bullets.xml";
	public static final String SPRITESHEET_FILE = "spritesheets/tiles.png";
	public static final String SPRITESHEET_NPCS_FILE = "spritesheets/npcs.png";
	public static final String SPRITESHEET_PORTALS_FILE = "spritesheets/portals.png";
	public static final String SPRITESHEET_GUI_FILE = "spritesheets/gui.png";
	public static final String SPRITESHEET_TOWERS_FILE = "spritesheets/towers.png";
	public static final String SPRITESHEET_BUILDINGS_FILE = "spritesheets/buildings.png";
	public static final String SPRITESHEET_BULLETS_FILE = "spritesheets/bullets.png";
	public static final String FONT_FILE = "font.png";

	public static final String NPC_NODE_NAME = "npc";
	public static final String TOWER_NODE_NAME = "tower";
	public static final String BUILDING_NODE_NAME = "building";
	public static final String BULLET_NODE_NAME = "bullet";

	public static final int SCREEN_WIDTH = 240;
	public static final int SCREEN_HEIGHT = SCREEN_WIDTH/12*9;
	public static final int SCALE = 2;
	public static final int REAL_SCREEN_WIDTH = SCREEN_WIDTH * SCALE;
	public static final int REAL_SCREEN_HEIGHT = SCREEN_HEIGHT * SCALE;
	public static final Dimension DIMENSIONS = new Dimension(REAL_SCREEN_WIDTH, REAL_SCREEN_HEIGHT);
	public static final int DEFAULT_BKG_COLOR = 0x555555;

	public static final int BUFFER_STRATEGY_BUFFERS = 3;
	public static final int COORDINATES_SHIFTING = 4;

	public static final int BOX_SIZE = 16;
	public static final int BOX_SIZE_FIXED = BOX_SIZE - 1;

	public static final String DEFAULT_LEVELS_PATH = "/images/levels/";
	public static final String DEFAULT_LEVEL_FILENAME = "labirinth_level.png";
//	public static final String DEFAULT_LEVEL_FILENAME = "default_level.png";
//	public static final String DEFAULT_LEVEL_FILENAME = "simple_level.png";
	public static final boolean DEFAULT_LEVEL_USE_FOG = true;
	public static final int DEFAULT_LEVEL_ENTRANCE_RADAR_VIEW_SIZE = 80;
	public static final long NPC_DEATH_MARK_VISIBILITY = 5000;

	public static final int DEFAULT_PLAYER_HEALTH = 20;
	public static final int DEFAULT_PLAYER_MONEY = 500;
	public static final int EDGE_SIZE = 3;

	public static final int DEFAULT_PLAYER_RESOURCE = 120;

	public static final int LEVEL_WAVE_TIMEOUT = 10000;
	public static final double LEVEL_WAVE_MULTIPLIER = 1.5;
	public static final boolean LEVEL_HIGHLIGHT_PATH_TILES = false;

	public static final String TILE_GRASS = "GRASS";
	public static final String TILE_SAND = "SAND";
	public static final String TILE_STONE = "STONE";
	public static final String TILE_BUSH = "BUSH";
	public static final String TILE_VOID = "VOID";
	public static final String TILE_ENTRANCE = "ENTRANCE";
	public static final String TILE_EXIT = "EXIT";
	public static final String TILE_NPC_BULB = "NPC_BULB";
}
