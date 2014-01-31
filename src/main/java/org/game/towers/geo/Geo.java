package org.game.towers.geo;

public class Geo {

	private Coordinates topLeft = new Coordinates();
	private Coordinates topRight = new Coordinates();
	private Coordinates bottomLeft = new Coordinates();
	private Coordinates bottomRight = new Coordinates();

	public Geo() {}

	public Geo(Coordinates topLeft, Coordinates topRight, Coordinates bottomLeft, Coordinates bottomRight){
		this.topLeft = topLeft;
		this.topRight = topRight;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}

	public Geo(Coordinates topLeft, int shiftRight, int shiftDown){
		init(topLeft, shiftRight, shiftDown);
	}

	private void init(Coordinates topLeft, int shiftRight, int shiftDown) {
		this.topLeft = topLeft;

		this.topRight.setX(topLeft.getX() + shiftRight);
		this.topRight.setY(topLeft.getY());

		this.bottomLeft.setX(topLeft.getX());
		this.bottomLeft.setY(topLeft.getY() + shiftDown);

		this.bottomRight.setX(topLeft.getX() + shiftRight);
		this.bottomRight.setY(topLeft.getY() + shiftDown);
	}

	public Geo(Coordinates coordinates, int shift) {
		init(topLeft, shift, shift);
	}

	public Coordinates getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(Coordinates topLeft) {
		this.topLeft = topLeft;
	}

	public Coordinates getTopRight() {
		return topRight;
	}

	public void setTopRight(Coordinates topRight) {
		this.topRight = topRight;
	}

	public Coordinates getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(Coordinates bottomLeft) {
		this.bottomLeft = bottomLeft;
	}

	public Coordinates getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Coordinates bottomRight) {
		this.bottomRight = bottomRight;
	}
}
