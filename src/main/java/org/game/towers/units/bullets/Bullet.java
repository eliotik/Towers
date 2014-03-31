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
		if (getTarget() != null) {
//			double speed = (getSpeed() == 0) ? getOwner().getSpeed() : getSpeed();
//			// calculate the vector from our center to their center
//			Point2D enemyVec = Vector.vec_sub(endPoint.getX(), endPoint.getY(), getX(), getY());
//			// measure the "distance" the bullet will travel
//			double dist = Vector.vec_mag(enemyVec.getX(), enemyVec.getY());
//			// adjust for target position based on the amount of "time units" to travel "dist"
//			// and the targets speed vector
//			Point2D targetSpeed = new Point2D.Double();
//			targetSpeed.setLocation(getTarget().getSpeed(), getTarget().getSpeed());
//			enemyVec = Vector.vec_add(endPoint.getX(), endPoint.getY(), Vector.vec_mul(targetSpeed, dist/speed));
//			// calculate trajectory of bullet
//			Point2D bulletTrajectory = Vector.vec_mul(Vector.vec_normal(enemyVec.getX(), enemyVec.getY()), speed);
//			// assign values
//			double nextX = bulletTrajectory.getX();
//			double nextY = bulletTrajectory.getY();
//
//			System.out.println(">>>>>>>>>>>>>>>");
//			System.out.println(getX()+", "+getY()+" | "+getStartPoint().getX()+", "+getStartPoint().getY()+" | "+getEndPoint().getX()+", "+getEndPoint().getY()+" | "+nextX+", "+nextY);

		}
		int shiftX = Utils.doShift((int) getX(), (int) getEndPoint().getX());
        int shiftY = Utils.doShift((int) getY(), (int) getEndPoint().getY());
        if (shiftX == 0 && shiftY == 0) {
        	setMoving(false);
        } else {
//        	move(shiftX, shiftY);
            move(getStartPoint().getX(), getEndPoint().getX(), getStartPoint().getY(), getEndPoint().getY());
        }
	}

//	private void move(int shiftX, int shiftY) {
//		setMoving(true);
//		double speed = (getSpeed() == 0) ? getOwner().getSpeed() : getSpeed();
//        setX(getX() + shiftX * speed);
//		setY(getY() + shiftY * speed);
//		setNumSteps(getNumSteps() + 1);
//	}

    private void move(double x1, double x2, double y1, double y2){
        setMoving(true);
        double eps = 0.05;
        double speed = (getSpeed() == 0) ? getOwner().getSpeed() : getSpeed();
//        double direction = LinearAlgorithms.direction(x1, x2, y1, y2);
        double direction = LinearAlgorithms.angleByCoordinate(x1, x2, y1, y2);
//        double radius = LinearAlgorithms.radiusVector(x1, x2, y1, y2);
//        double deltaRadius = radius / speed;

        if ((LinearAlgorithms.radiusVector(x1, getX(), y1, getY()) / (((Tower)getOwner()).getRadius() + (double)Config.BOX_SIZE) < 1 + eps) &&
            (LinearAlgorithms.radiusVector(x1, getX(), y1, getY()) / (((Tower)getOwner()).getRadius() + (double)Config.BOX_SIZE) > 1 - eps)) {
            setMoving(false);
            return;
        }

        double[] coordinates = LinearAlgorithms.shiftingByPolarSystem((speed * getNumSteps()), direction);
        setX(getX() + coordinates[0]);
        setY(getY() + coordinates[1]);
        setNumSteps(getNumSteps() + 1);

//        if ((direction > 67 && direction < 113)) {
//            System.out.println("test1 " +  direction);
//            double shiftX = LinearAlgorithms.dependentXFromY((getY() - speed), x1, x2, y1, y2);
//            System.out.println("shiftX =" + shiftX + " y =" + (getY() - speed));
//            setY(getY() - speed);
//            setNumSteps(getNumSteps() + 1);
//        }
//
//        if (direction < -67 && direction > -113) {
//            System.out.println("test2 " +  direction);
//            double shiftX = LinearAlgorithms.dependentXFromY((getY() + speed), x1, x2, y1, y2);
//            setX(shiftX);
//            setY(getY() + speed);
//            setNumSteps(getNumSteps() + 1);
//        }
//
//        if ( (direction < -113 && direction >= -180 ) || (direction >= 0 && direction <= 67) ) {
//            System.out.println("test3 " +  direction);
//            double shiftY = LinearAlgorithms.dependentYFromX((getX() - speed), x1, x2, y1, y2);
//            setX(getX() - speed);
//            setY(shiftY);
//            System.out.println("x =" + (getX() - speed) + " y ="+shiftY);
//            setNumSteps(getNumSteps() + 1);
//        }
//
//        if ((direction >= -67 && direction <= -1) || (direction >= 113 && direction <= 180))  {
//            System.out.println("test4 " +  direction);
//            double shiftY = LinearAlgorithms.dependentYFromX((getX() - speed), x1, x2, y1, y2);
//            setX(getX() - speed);
//            setY(shiftY);
//            setNumSteps(getNumSteps() + 1);
//        }
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
