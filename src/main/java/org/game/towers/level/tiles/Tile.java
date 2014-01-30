package org.game.towers.level.tiles;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public abstract class Tile {

	private byte id;
	private Level level;
	private String name;
	private Sprite sprite;
	private boolean solid;
	private boolean emitter;
	private int levelColor;
	private int x;
	private int y;

	public Tile(Level level, int id, Sprite sprite, String name, int levelColor,
			boolean isSolid, boolean isEmitter, int x, int y) {
		setId((byte) id);
//		if (TileTypes.tiles[id] != null) throw new RuntimeException("Duplicate tile id on " + id);
	    setLevel(level);
	    setName(name);
	    setSprite(sprite);
	    setSolid(isSolid);
	    setEmitter(isEmitter);
	    setLevelColor(levelColor);
	    setX(x);
	    setY(y);
//	    TileTypes.tiles[id] = this;
	}

//	public abstract void render(Screen screen, Level level, int x, int y);
//	public abstract void render(Screen screen, Level level, int x, int y, int mirrorDir);
	public abstract void render(Screen screen);
	public abstract void tick();

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public byte getId() {
		return id;
	}

	public boolean isSolid() {
		return solid;
	}

	public int getLevelColor() {
		return levelColor;
	}

	public void setLevelColor(int levelColor) {
		this.levelColor = levelColor;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSolid(boolean solid) {
		this.solid = solid;
	}

	public boolean isEmitter() {
		return emitter;
	}

	public void setEmitter(boolean emitter) {
		this.emitter = emitter;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
