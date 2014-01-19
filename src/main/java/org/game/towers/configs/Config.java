package org.game.towers.configs;

import java.awt.Dimension;
import java.io.File;

import org.game.towers.game.Game;

public class Config {
	public static final String GAME_VERSION = "0.0.1";
	public static final String GAME_NAME = "Towers " + GAME_VERSION;
	public static final String DEFAULT_WORLD_NAME = "towers_default_world";
	public static final String HOME_DIR = System.getProperty("user.home") + File.separator
			+ ".towersdata" + File.separator;
	public static final String WORLD_DIR = HOME_DIR + "saves" + File.separator;
	
	public static final String XML_FILE_PATH = "/configs/";
	public static final String IMAGES_FILE_PATH = "/images/";
	
	public static final String NPCS_FILE = "npcs.xml";
	public static final String TOWERS_FILE = "towers.xml";
	public static final String BUILDINGS_FILE = "buildings.xml";
	public static final String SPRITESHEET_FILE = "spritesheet.png";
	public static final String FONT_FILE = "font.png";

	public static final String NPC_NODE_NAME = "npc";
	public static final String TOWER_NODE_NAME = "tower";
	public static final String BUILDING_NODE_NAME = "building";

	public static final int SCREEN_WIDTH = 296;
	public static final int SCREEN_HEIGHT = SCREEN_WIDTH/12*9;
	public static final int SCALE = 3;
	public static final int REAL_SCREEN_WIDTH = SCREEN_WIDTH * SCALE;
	public static final int REAL_SCREEN_HEIGHT = SCREEN_HEIGHT * SCALE;
	public static final Dimension DIMENSIONS = new Dimension(REAL_SCREEN_WIDTH, REAL_SCREEN_HEIGHT);
	
	
    public static final int AMOUNT_HORIZONTAL_PIX = 800;
    public static final int AMOUNT_VERTICAL_PIX = 800;
	public static final int BUFFER_STRATEGY_BUFFERS = 3;
	
	public static final int BOX_SIZE = 8; 
	public static final int MAP_X_SIZE = 37; 
	public static final int MAP_Y_SIZE = 25;
	
	public static final boolean LEVEL_SHOW_IDS = false;
	public static final int DEFAULT_LEVEL_WIDTH = MAP_X_SIZE;
	public static final int DEFAULT_LEVEL_HEIGHT = MAP_Y_SIZE;
	public static final String DEFAULT_LEVELS_PATH = "/images/levels/";
	public static final String DEFAULT_LEVEL_FILENAME = "default_level.png";
}
