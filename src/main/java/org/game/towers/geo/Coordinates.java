package org.game.towers.geo;

import java.io.Serializable;

public class Coordinates implements Serializable {

	private static final long serialVersionUID = 1L;
	private int x;
    private int y;

    public Coordinates(int x, int y) {
        setX(x);
        setY(y);
    }

    public Coordinates() {
        this(0,0);
    }

    public Coordinates(double newX, double newY) {
    	setX((int)newX);
        setY((int)newY);
	}

	public boolean equals(Object o) {
    	Coordinates c = (Coordinates) o;
        return c.getX() == getX() && c.getY() == getY();
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

	public void setX(double newX) {
		this.x = (int) newX;
	}

	public void setY(double newY) {
		this.y = (int) newY;
	}
}