package org.game.towers.level.tiles;

import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicTile(0, 0, 0, Colors.get(000, -1, -1, -1), true, 0xFF000000);
//	public static final Tile STONE = new BasicTile(1, 1, 0, Colors.get(131, 333, 339, 318), true, 0xFF555555);
	public static final Tile STONE = new AnimatedTile(1, new int[][] {{0, 3}, {1, 3}, {2, 3}, {3, 3}, {0, 3}}, 
			Colors.get(131, 333, 339, 318), true, 0xFF555555, 80, 4000);
//	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, -1), false, 0xFF00FF00);
	public static final Tile GRASS = new AnimatedTile(2, new int[][] {{0, 4}, {1, 4}, {2, 4}, {3, 4}, {4, 4}, {5, 4}, {6, 4}, {7, 4}, {8, 4}, {9, 4}, {10, 4}, {11, 4}, {12, 4}, {13, 4}, {14, 4}, {15, 4}, {16, 4}, {0, 4}}, 
			Colors.get(-1, 131, 141, -1), false, 0xFF00FF00, 80, 4000);
//	public static final Tile BUSH = new BasicTile(3, 3, 0, Colors.get(161, 151, 141, 131), true, 0xFF69BD45);
	public static final Tile BUSH = new AnimatedTile(3, new int[][] {{0, 5}, {1, 5}, {2, 5}, {1, 5}}, Colors.get(161, 151, 141, 131), true, 0xFF69BD45, 3500);
//	public static final Tile ENTRANCE = new BasicTile(4, 4, 0, Colors.get(335, 131, 555, 777), false, 0xFFFAA71A);
	public static final Tile ENTRANCE = new AnimatedTile(4, new int[][] {{0, 6}, {1, 6}, {2, 6}, {3, 6}, {4, 6}, {5, 6}, {6, 6}, {7, 6}, {8, 6}, {9, 6}, {10, 6}, {11, 6}, {12, 6}, {13, 6}, {14, 6}, {15, 6}, {0, 6}}, 
			Colors.get(335, 131, 555, 777), false, 0xFFFAA71A, 100);
//	public static final Tile EXIT = new BasicTile(5, 5, 0, Colors.get(335, 131, 555, 777), false, 0xFFFF0000);
	public static final Tile EXIT = new AnimatedTile(5, new int[][] {{0, 7}, {1, 7}, {2, 7}, {3, 7}, {4, 7}, {5, 7}, {6, 7}, {7, 7}, {8, 7}, {9, 7}, {10, 7}, {11, 7}, {12, 7}, {13, 7}, {14, 7}, {15, 7}}, 
			Colors.get(335, 131, 555, 777), false, 0xFFFF0000, 100);
	public static final Tile SAND = new AnimatedTile(6, new int[][] {{0, 8}, {1, 8}, {2, 8}, {3, 8}, {2, 8}, {1, 8}}, 
			Colors.get(433, 455, 444, 552), true, 0xFFFFEA00, 2000, 500);	

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColor;
	
	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		setLevelColor(levelColor);
		tiles[id] = this;
	}
	
	public byte getId() {
		return id;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean isEmiter() {
		return emitter;
	}
	
	public abstract void render(Screen screen, Level level, int x, int y);
	public abstract void render(Screen screen, Level level, int x, int y, int mirrorDir);

	public int getLevelColor() {
		return levelColor;
	}

	public void setLevelColor(int levelColor) {
		this.levelColor = levelColor;
	}

	public abstract void tick();
	
}
