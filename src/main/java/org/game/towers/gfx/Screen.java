package org.game.towers.gfx;

import java.awt.Graphics;
import java.awt.Point;

public class Screen {

	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	public int[] pixels;
	private Graphics graphics;

	private int xOffset = 0;
	private int yOffset = 0;

	public int width;
	public int height;

	private Point mousePosition = new Point(0, 0);

//	public SpriteSheet sheet;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		pixels = new int[width * height];
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
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
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
}
