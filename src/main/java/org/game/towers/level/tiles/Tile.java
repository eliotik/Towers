package org.game.towers.level.tiles;

import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;

public abstract class Tile {

	public static final Tile[] tiles = new Tile[256];
	public static final Tile VOID = new BasicTile(0, 0, 0, Colors.get(000, -1, -1, -1), true);
	public static final Tile STONE = new BasicTile(1, 1, 0, Colors.get(-1, 333, -1, -1), true);
	public static final Tile GRASS = new BasicTile(2, 2, 0, Colors.get(-1, 131, 141, -1));
	public static final Tile BUSH = new BasicTile(3, 3, 0, Colors.get(161, 151, 141, 131), true);
	public static final Tile ENTRENCE = new BasicTile(4, 4, 0, Colors.get(335, 131, 555, 777));

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	
	public Tile(int id, boolean isSolid, boolean isEmitter) {
		this.id = (byte) id;
		if (tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
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

}
