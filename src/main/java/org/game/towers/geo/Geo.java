package org.game.towers.geo;

public class Geo {

	private Coordinates topLeft = new Coordinates();
	private Coordinates topRight = new Coordinates();
	private Coordinates bottomLeft = new Coordinates();
	private Coordinates bottomRight = new Coordinates();

	public Geo() {}

	public Geo(Coordinates topLeft, Coordinates topRight, Coordinates bottomLeft, Coordinates bottomRight){
		setTopLeft(topLeft);
		setTopRight(topRight);
		setBottomLeft(bottomLeft);
		setBottomRight(bottomRight);
	}

	public Geo(Coordinates topLeft, int shiftRight, int shiftDown){
		init(topLeft, shiftRight, shiftDown);
	}

	private void init(Coordinates topLeft, int shiftRight, int shiftDown) {
		setTopLeft(topLeft);

		getTopLeft().setX(getTopLeft().getX() << 4);
		getTopLeft().setY(getTopLeft().getY() << 4);

		getTopRight().setX(topLeft.getX() + shiftRight);
		getTopRight().setY(topLeft.getY());

		getBottomLeft().setX(topLeft.getX());
		getBottomLeft().setY(topLeft.getY() + shiftDown);

		getBottomRight().setX(topLeft.getX() + shiftRight);
		getBottomRight().setY(topLeft.getY() + shiftDown);
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
