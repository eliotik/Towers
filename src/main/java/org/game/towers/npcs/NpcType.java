/**
 *
 */
package org.game.towers.npcs;

import java.awt.Point;
import java.util.ArrayList;

import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.units.Unit;

/**
 * @author eliotik
 *
 */
public class NpcType extends Unit {

	private static final long serialVersionUID = 1L;

//	public NpcType(Level level) {
//		super(level);
//	}

	private ArrayList<String> hands;

	public ArrayList<String> getHands() {
		return hands;
	}

	public void setHands(ArrayList<String> hands) {
		this.hands = hands;
	}

    public boolean isConstruction() {
        return false;
    }

	@Override
	public void tick() {
//		int xa = 0;
//
//		if (getMovingDirection() != 2)
//			setMovingDirection(3);
//
//		if (getX() < Config.SCREEN_WIDTH - 8*2 && getMovingDirection() == 3) {
//			xa++;
//		} else if (getX() >= Config.SCREEN_WIDTH - 8*2 && getMovingDirection() == 3) {
//			setMovingDirection(2);
//		} else if (getX() > 8 && getMovingDirection() == 2) {
//			xa--;
//		} else if (getX() <= 8 && getMovingDirection() == 2) {
//			setMovingDirection(3);
//		}
//		if (xa != 0) {
//			if(!hasCollided(xa, 0)) {
//				move(xa, 0);
//			} else {
//				if (getMovingDirection() == 2) {
//					setMovingDirection(3);
//					move(xa++, 0);
//				} else {
//					setMovingDirection(2);
//					move(--xa, 0);
//				}
//			}
//
//			setMoving(true);
//		} else {
//			setMoving(false);
//		}
		Point shifts = new Point();
		Game.instance.getPathWorker().nextCoordinate((int)getX(), (int)getY(), shifts);
        move((int)shifts.getX(), (int)shifts.getY());
//		setNumSteps(16);
//		setScale(1);
	}

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this);
	}

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			setNumSteps(getNumSteps() - 1);
			return;
		}

		setNumSteps(getNumSteps() + 1);

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
		}
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		int xMin = 0;
		int xMax = 7;
		int yMin = 0;
		int yMax = 7;

		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMin)) {
				return true;
			}
		}
		for (int x = xMin; x < xMax; x++) {
			if (isSolidTile(xa, ya, x, yMax)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMin, y)) {
				return true;
			}
		}
		for (int y = yMin; y < yMax; y++) {
			if (isSolidTile(xa, ya, xMax, y)) {
				return true;
			}
		}

		return false;
	}

	public Sprite getCurrentHealthSprite() {
		int healthPercent = (int) (((double) getHealth() / (double) getMaxHealth()) * (double) 100);
		if (healthPercent >= 100) {
			return SpritesData.HEALTH_MAX;
		}
		if (healthPercent >= 90) {
			return SpritesData.HEALTH_90;
		}
		if (healthPercent >= 80) {
			return SpritesData.HEALTH_80;
		}
		if (healthPercent >= 70) {
			return SpritesData.HEALTH_70;
		}
		if (healthPercent >= 60) {
			return SpritesData.HEALTH_60;
		}
		if (healthPercent >= 50) {
			return SpritesData.HEALTH_50;
		}
		if (healthPercent >= 40) {
			return SpritesData.HEALTH_40;
		}
		if (healthPercent >= 30) {
			return SpritesData.HEALTH_30;
		}
		if (healthPercent >= 20) {
			return SpritesData.HEALTH_20;
		}
		if (healthPercent >= 10) {
			return SpritesData.HEALTH_10;
		}
		return SpritesData.HEALTH_DEAD;
	}
}
