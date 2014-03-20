package org.game.towers.units.npcs;

import java.awt.Point;
import java.util.ArrayList;

import org.game.towers.game.Game;
import org.game.towers.game.level.Portals;
import org.game.towers.gfx.Screen;
import org.game.towers.units.Unit;
import org.game.towers.units.towers.modificators.Modificator;

public class Npc extends Unit {

	private static final long serialVersionUID = 1L;

	private ArrayList<String> hands;
	private int award;
	private Modificator negativeImpact;

	public ArrayList<String> getHands() {
		return hands;
	}

	public void setHands(ArrayList<String> hands) {
		this.hands = hands;
	}

	@Override
	public void tick() {
		super.tick();

		if (!isDead() && !isFinished()) {

			applyNegativeImpact();

			Point shifts = new Point();

            setFinished(Math.abs(Portals.getExit().getCoordinates().getX() - getX()) < 2  && Math.abs(Portals.getExit().getCoordinates().getY() - getY()) < 2);

			Game.instance.getPathWorker().nextCoordinate((int) getX(), (int) getY(), shifts, hashCode());
	        move((int)shifts.getX(), (int)shifts.getY());
		}
	}

	private void applyNegativeImpact() {

	}

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this, getMirrorMask(), getScale());
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			setNumSteps(getNumSteps() - 1);
			return;
		}

		if(!hasCollided(xa, ya)) {
			if (ya < 0) {
				setMovingDirection(0);
			}
			if (ya > 0) {
				setMovingDirection(1);
			}
			if (xa < 0) {
				setMovingDirection(2);
			}
			if (xa > 0) {
				setMovingDirection(3);
			}
			setX(getX() + xa * getSpeed());
			setY(getY() + ya * getSpeed());
			setNumSteps(getNumSteps() + 1);
		} else {
			setMoving(false);
		}
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		double xMin = getMinCollisionBox().getX();
		double xMax = getMaxCollisionBox().getX();

		double yMin = getMinCollisionBox().getY();
		double yMax = getMaxCollisionBox().getY();

		for (double x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}

		for (double x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}

		for (double y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}

		for (double y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}

		return false;
	}

	public int getAward() {
		return award;
	}

	public void setAward(int award) {
		this.award = award;
	}

	public Modificator getNegativeImpact() {
		return negativeImpact;
	}

	public void setNegativeImpact(Modificator negativeImpact) {
		this.negativeImpact = negativeImpact;
	}
}
