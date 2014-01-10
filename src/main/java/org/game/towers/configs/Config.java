package org.game.towers.configs;

public class Config {
	public static final String GAME_VERSION = "0.0.1";
	public static final String GAME_NAME = "Towers " + GAME_VERSION;
	
	public static final String XML_FILE_PATH = "/configs/";
	
	public static final String NPCS_FILE = "npcs.xml";
	public static final String TOWERS_FILE = "towers.xml";

	public static final String NPC_NODE_NAME = "npc";
	public static final String TOWER_NODE_NAME = "tower";

	public static final int SCREEN_WIDTH = 160;
	public static final int SCREEN_HEIGHT = SCREEN_WIDTH/12*9;
	public static final int SCALE = 3;
    
    public static final int AMOUNT_HORIZONTAL_PIX = 800;
    public static final int AMOUNT_VERTICAL_PIX = 800;
	public static final int BUFFER_STRATEGY_BUFFERS = 3;
}
