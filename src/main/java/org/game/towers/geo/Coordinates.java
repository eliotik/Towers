package org.game.towers.geo;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    public Coordinates() {
        this(0,0);
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
}