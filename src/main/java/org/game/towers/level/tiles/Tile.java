package org.game.towers.level.tiles;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public abstract class Tile {

	private Level level;
	private String name;
	private Sprite sprite;
	private boolean solid;
	private boolean emitter;
	private int levelColor;
	private int x;
	private int y;
	private int mirrorMask = 0x00;
	private double highlight = 0;

	public Tile(Level level, Sprite sprite, String name, int levelColor,
			boolean isSolid, boolean isEmitter, int x, int y) {
	    setLevel(level);
	    setName(name);
	    setSprite(sprite);
	    setSolid(isSolid);
	    setEmitter(isEmitter);
	    setLevelColor(levelColor);
	    setX(x);
	    setY(y);
	}

	public abstract void render(Screen screen);
	public abstract void tick();

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
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

	public int getMirrorMask() {
		return mirrorMask;
	}

	public void setMirrorMask(int mirrorMask) {
		this.mirrorMask = mirrorMask;
	}

	public double getHighlight() {
		return highlight;
	}

	public void setHighlight(double highlight) {
		this.highlight = highlight;
	}
}
