package org.game.towers.gfx.sprites;

import java.io.Serializable;

import org.game.towers.game.Config;

public class Sprite implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private int[] pixels;

	public Sprite(String name, int x, int y, int width, int height,
			String spriteType) {
		setName(name);
		setX(x << Config.COORDINATES_SHIFTING);
		setY(y << Config.COORDINATES_SHIFTING);
		setWidth(width);
		setHeight(height);
		setPixels(new int[width * height]);
		create(spriteType);
	}

	public Sprite(int color) {
		setX(0);
		setY(0);
		setWidth(Config.BOX_SIZE);
		setHeight(Config.BOX_SIZE);
		setPixels(new int[getWidth() * getHeight()]);
		create(color);
	}

	private void create(String spriteType) {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				SpriteSheet sheet = SpritesData.getSpriteSheet(spriteType);
				getPixels()[x + y * getWidth()] = sheet.getPixels()[(x + this.getX())
						+ (y + this.getY()) * sheet.getWidth()];
			}
		}
	}

	private void create(int color) {
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				getPixels()[x + y * getWidth()] = color;
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
}
