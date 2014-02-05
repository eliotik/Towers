package org.game.towers.gfx;

import java.awt.Color;

public class RenderedString {

	private String msg;
	private int x;
	private int y;
	private int size;
	private int style;
	private Color color;

	public RenderedString(String msg, int x, int y, int size, int style, Color color) {
		setMsg(msg);
		setX(x);
		setY(y);
		setSize(size);
		setStyle(style);
		setColor(color);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStyle() {
		return style;
	}

	public void setStyle(int style) {
		this.style = style;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
