package org.game.towers.bullets;

import java.awt.Point;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;
import org.game.towers.npcs.NpcType;
import org.game.towers.towers.TowerType;
import org.game.towers.units.Unit;
import org.game.towers.workers.Utils;

public class BulletType extends Unit {

	private static final long serialVersionUID = 1L;

	private double distance;
	private Unit owner;
	private Point point;

	@Override
	public void tick() {
		super.tick();
		updatePosition();
	}

	private void updatePosition() {
		int shiftX = Utils.doShift((int) getX(), (int) getPoint().getX());
        int shiftY = Utils.doShift((int) getY(), (int) getPoint().getY());
        if (shiftX == 0 && shiftY == 0) {
        	setMoving(false);
        } else {
        	setMoving(true);
	        setX(getX() + shiftX * getSpeed());
			setY(getY() + shiftY * getSpeed());
			setNumSteps(getNumSteps() + 1);
        }
	}

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this, getMirrorMask(), getScale());
	}

	@Override
	public int getDamage() {
		return ((TowerType) getOwner()).getDamage();
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
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

	public void setPoint(Point point) {
		this.point = point;
	}

	public Point getPoint() {
		return point;
	}
}
