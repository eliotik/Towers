package org.game.towers.units.bullets;

import java.awt.Point;
import java.awt.Rectangle;

import org.game.towers.game.Config;
import org.game.towers.gfx.Screen;
import org.game.towers.units.Unit;
import org.game.towers.units.towers.Tower;
import org.game.towers.workers.Algorithms.MathAlgorithms.LinearAlgorithms;
import org.game.towers.workers.Utils;

public class Bullet extends Unit {

	private static final long serialVersionUID = 1L;

	private double distance;
	private Unit owner;
	private Unit target;
	private Point endPoint;
	private Point startPoint;

    @Override
	public void tick() {
		super.tick();
		updatePosition();
	}

	private void updatePosition() {
		int shiftX = Utils.doShift((int) getX(), (int) getEndPoint().getX());
        int shiftY = Utils.doShift((int) getY(), (int) getEndPoint().getY());
        if (shiftX == 0 && shiftY == 0) {
        	setMoving(false);
        } else {
            move(getStartPoint().getX(), getEndPoint().getX(), getStartPoint().getY(), getEndPoint().getY() - Config.BOX_SIZE / 2);
        }
	}

    private void move(double x1, double x2, double y1, double y2){
        setMoving(true);
        double speed = (getSpeed() == 0) ? getOwner().getSpeed() : getSpeed();
        double direction = LinearAlgorithms.angleByCoordinate(x1, x2, y1, y2);
        double currentRadius = LinearAlgorithms.radiusVector(x1, getX(), y1, getY());
        if (currentRadius >= ((Tower)getOwner()).getRadius()) {
            setMoving(false);
            return;
        }

        double[] coordinates = LinearAlgorithms.shiftingByPolarSystem(speed, direction);
        setX(getX() + coordinates[0]);
        setY(getY() + coordinates[1]);
        setNumSteps(getNumSteps() + 1);

    }

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this, getMirrorMask(), getScale());
	}

	@Override
	public int getDamage() {
		return ((Tower) getOwner()).getDamage();
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public boolean hasCollision(Unit unit) {
		int bXMin = (int) getMinCollisionBox().getX();
		int bXMax = (int) getMaxCollisionBox().getX();
		int bYMin = (int) getMinCollisionBox().getY();
		int bYMax = (int) getMaxCollisionBox().getY();

		int uXMin = (int) unit.getMinCollisionBox().getX();
		int uXMax = (int) unit.getMaxCollisionBox().getX();
		int uYMin = (int) unit.getMinCollisionBox().getY();
		int uYMax = (int) unit.getMaxCollisionBox().getY();

//		System.out.println("-----------------------------------");
//		System.out.println(endPoint.getX()+", "+endPoint.getY());
//		System.out.println((int)getX()+", "+(int)getY()+" | "+bXMin+", "+bYMin+" | "+(int)(getX() + bXMin)+", "+(int)(getY() + bYMin)+" | "+(bXMax - bXMin)+", "+(bYMax - bYMin));
//		System.out.println((int)unit.getX()+", "+(int)unit.getY()+" | "+uXMin+", "+uYMin+" | "+(int)(unit.getX() + uXMin)+", "+(int)(unit.getY() + uYMin)+" | "+(uXMax - uXMin)+", "+(uYMax - uYMin));

		Rectangle bulletBox = new Rectangle((int) getX() + bXMin, (int) getY() + bYMin, bXMax - bXMin, bYMax - bYMin);
		Rectangle unitBox = new Rectangle((int) unit.getX() + uXMin, (int) unit.getY() + uYMin, uXMax - uXMin, uYMax - uYMin);

		return unitBox.intersects(bulletBox);
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	public Unit getOwner() {
		return owner;
	}

	public void setEndPoint(Point point) {
		this.endPoint = point;
	}

	public Point getEndPoint() {
		return endPoint;
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point point) {
		this.startPoint = point;
	}

	public Unit getTarget() {
		return target;
	}

	public void setTarget(Unit target) {
		this.target = target;
	}

	@Override
	public void onMouseHover() {}

	@Override
	public void onMouseOut() {}

	@Override
	public void onMouseHolded() {}

	@Override
	public void onMouseClicked() {}
}
