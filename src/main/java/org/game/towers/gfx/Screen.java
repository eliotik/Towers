package org.game.towers.gfx;

import java.awt.Graphics;
import java.awt.Point;

import org.game.towers.level.Level;
import org.game.towers.level.tiles.Tile;
import org.game.towers.npcs.NpcType;
import org.game.towers.units.Unit;

public class Screen {

	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	private int[] pixels;
	private Graphics graphics;

	private int xOffset = 0;
	private int yOffset = 0;

	private int width;
	private int height;

	private Point mousePosition = new Point(0, 0);

//	public SpriteSheet sheet;

	public Screen(int width, int height) {
		setWidth(width);
		setHeight(height);

		setPixels(new int[width * height]);
	}

//	public Screen(int width, int height, SpriteSheet sheet) {
//		this.width = width;
//		this.height = height;
//		this.sheet = sheet;
//
//		pixels = new int[width * height];
//	}

	public void setGraphics(Graphics g) {
		graphics = g;
	}

	public void clear() {
		for (int i = 0; i < getPixels().length; i++) {
			getPixels()[i] = 0;
		}
	}

	public void renderTile(Level level, int xOrig, int yOrig, Tile tile) {
		int xp = (xOrig * tile.getSprite().getWidth()) - getxOffset();
		int yp = (yOrig * tile.getSprite().getHeight()) - getyOffset();

		for (int y = 0; y < tile.getSprite().getHeight(); y++) {
			int yt = y + yp;
			for (int x = 0; x < tile.getSprite().getWidth(); x++) {
				int xt = x + xp;
				if (0 - tile.getSprite().getWidth() > xt || xt >= getWidth()
						|| 0 - tile.getSprite().getHeight() > yt || yt >= getHeight()) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}

				if (yt < 0) {
					yt = 0;
				}
				int color = tile.getSprite().getPixels()[x + y * tile.getSprite().getWidth()];
				if ((color == 0xFFFF00FF || color == 0xFF800080)
						&& level != null) {
					Tile bgTile = level.getBackgroundTile(xOrig, yOrig);
					color = bgTile.getSprite().getPixels()[x	+ y * bgTile.getSprite().getWidth()];
				}
				getPixels()[xt + yt * getWidth()] = color;
			}
		}
	}

	public void fill(int color) {
		for (int i = 0; i < getPixels().length; i++) {
			getPixels()[i] = color;
		}
	}

//	public void render(int xPos, int yPos, int tile, int color) {
//		render(xPos, yPos, tile, color, 0x00, 1);
//	}
//
//	public void render(int xPos, int yPos, int tile, int color, int mirrorDir, int scale) {
////		xPos = sheet.getWidth() - xOffset;
////		yPos = sheet.getHeight() - yOffset;
//		xPos -= getxOffset();
//		yPos -= getyOffset();
//
//		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
//		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
//
//		int xTile = tile % 32;
//		int yTile = tile / 32;
//		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.getWidth();
//		int scaleMap = scale - 1;
//
//		for (int y = 0; y < 8; y++) {
//			int ySheet = y;
//			if (mirrorY) ySheet = 7 - y;
//
//			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);
//
//
//			for (int x = 0; x < 8; x++) {
//				int xSheet = x;
//				if (mirrorX) {
//					xSheet = 7 - x;
//				}
//				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
//				int col = (color >> (sheet.getPixels()[xSheet + ySheet * sheet.getWidth() + tileOffset] * 8)) & 255;
//				if (col < 255) {
//					for (int yScale = 0; yScale < scale; yScale++) {
//						if (yPixel + yScale < 0 || yPixel + yScale >= height) {
//							continue;
//						}
//						for (int xScale = 0; xScale < scale; xScale++) {
//							if (xPixel + xScale < 0 || xPixel + xScale >= width) {
//								continue;
//							}
//							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
//						}
//					}
//				}
//			}
//		}
//	}

	public void setOffset(int xOffset, int yOffset) {
		setxOffset(xOffset);
		setyOffset(yOffset);
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
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

	public void renderUnit(int xOrig, int yOrig, Unit unit) {
		int xp = xOrig - getxOffset();
		int yp = yOrig - getyOffset();
		for (int y = 0; y < unit.getCurrentSprite().getHeight(); y++) {
			int yt = y + yp;
			for (int x = 0; x < unit.getCurrentSprite().getWidth(); x++) {
				int xt = x + xp;
				if (0 - unit.getCurrentSprite().getWidth() > xt || xt >= getWidth()
						|| 0 - unit.getCurrentSprite().getHeight() > yt
						|| yt >= getHeight()) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}

				if (yt < 0) {
					yt = 0;
				}
				int colour = unit.getCurrentSprite().getPixels()[x + y
						* unit.getCurrentSprite().getWidth()];
				if (colour != 0xFFFF00FF && colour != 0xFF800080) {
					getPixels()[xt + yt * getWidth()] = colour;
				}
			}
		}

		if (unit instanceof NpcType) {
			yp -= 8;
			for (int y = 0; y < ((NpcType) unit).getCurrentHealthSprite().getHeight(); y++) {
				int yt = y + yp;
				for (int x = 0; x < ((NpcType) unit).getCurrentHealthSprite().getWidth(); x++) {
					int xt = x + xp;
					if (0 - ((NpcType) unit).getCurrentHealthSprite().getWidth() > xt
							|| xt >= getWidth()
							|| 0 - ((NpcType) unit).getCurrentHealthSprite().getHeight() > yt
							|| yt >= getHeight()) {
						break;
					}
					if (xt < 0) {
						xt = 0;
					}

					if (yt < 0) {
						yt = 0;
					}
					int colour = ((NpcType) unit).getCurrentHealthSprite().getPixels()[x + y * ((NpcType) unit).getCurrentHealthSprite().getWidth()];
					if (colour != 0xFFFF00FF && colour != 0xFF800080) {
						getPixels()[xt + yt * getWidth()] = colour;
					}
				}
			}
		}
	}
}
