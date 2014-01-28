package org.game.towers.level.tiles;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;

public abstract class Tile {

	protected byte id;
	protected boolean solid;
	protected boolean emitter;
	private int levelColor;

	public Tile(int id, boolean isSolid, boolean isEmitter, int levelColor) {
		this.id = (byte) id;
		if (TileTypes.tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
		this.solid = isSolid;
		this.emitter = isEmitter;
		setLevelColor(levelColor);
		TileTypes.tiles[id] = this;
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
