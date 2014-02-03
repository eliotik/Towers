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
		System.out.println("tlx="+topLeft.getX()+", tly="+topLeft.getY());
		setTopLeft(topLeft);

		getTopLeft().setX(getTopLeft().getX() << 4);
		getTopLeft().setY(getTopLeft().getY() << 4);

		getTopRight().setX(getTopLeft().getX() + shiftRight);
		getTopRight().setY(getTopLeft().getY());

		getBottomLeft().setX(getTopLeft().getX());
		getBottomLeft().setY(getTopLeft().getY() + shiftDown);

		getBottomRight().setX(getTopLeft().getX() + shiftRight);
		getBottomRight().setY(getTopLeft().getY() + shiftDown);
	}

	public Geo(Coordinates coordinates, int shift) {
		init(coordinates, shift, shift);
	}

	public Coordinates getTopLeft() {
		return topLeft;
	}

	public void setTopLeft(Coordinates topLeft) {
		this.topLeft.setX(topLeft.getX());
		this.topLeft.setY(topLeft.getY());
	}

	public Coordinates getTopRight() {
		return topRight;
	}

	public void setTopRight(Coordinates topRight) {
		this.topRight.setX(topRight.getX());
		this.topRight.setY(topRight.getY());
	}

	public Coordinates getBottomLeft() {
		return bottomLeft;
	}

	public void setBottomLeft(Coordinates bottomLeft) {
		this.bottomLeft.setX(bottomLeft.getX());
		this.bottomLeft.setY(bottomLeft.getY());
	}

	public Coordinates getBottomRight() {
		return bottomRight;
	}

	public void setBottomRight(Coordinates bottomRight) {
		this.bottomRight.setX(bottomRight.getX());
		this.bottomRight.setY(bottomRight.getY());
	}
}
