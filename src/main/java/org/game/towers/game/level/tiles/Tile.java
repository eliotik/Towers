package org.game.towers.game.level.tiles;

import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;

public abstract class Tile {

	private Level level;
	private String name;
	private Sprite sprite;
	private boolean solid;
	private boolean emitter;
	private double opacity = 100;
	private int levelColor;
	private int x;
	private int y;
	private int mirrorMask = 0x00;
	private double highlight = 1;
	private boolean radiant = false;
	private int radiantRadius;
	private boolean radiantRepeater = false;

	public Tile(Level level, Sprite sprite, String name, int levelColor,
			boolean isSolid, boolean isEmitter, int x, int y, double opacity,
			boolean radiant, int radiantRadius, boolean radiantRepeater) {
	    setLevel(level);
	    setName(name);
	    setSprite(sprite);
	    setSolid(isSolid);
	    setEmitter(isEmitter);
	    setLevelColor(levelColor);
	    setX(x);
	    setY(y);
	    setOpacity(opacity);
	    setRadiant(radiant);
	    setRadiantRadius(radiantRadius);
	    setRadiantRepeater(radiantRepeater);
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

	public double getOpacity() {
		return opacity;
	}

	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}

	public boolean isRadiant() {
		return radiant;
	}

	public void setRadiant(boolean radiant) {
		this.radiant = radiant;
	}

	public int getRadiantRadius() {
		return radiantRadius;
	}

	public void setRadiantRadius(int radiantRadius) {
		this.radiantRadius = radiantRadius;
	}

	public boolean isRadiantRepeater() {
		return radiantRepeater;
	}

	public void setRadiantRepeater(boolean radiantRepeater) {
		this.radiantRepeater = radiantRepeater;
	}
}
