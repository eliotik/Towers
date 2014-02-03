package org.game.towers.npcs;

import java.awt.Point;
import java.util.ArrayList;

import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.units.Unit;
import org.game.towers.workers.Utils;

public class NpcType extends Unit {

	private static final long serialVersionUID = 1L;

	private long lastIterationTime;
	private long lastIterationStartTime;
	private int animationSwitchDelay;
	private int animationStartDelay;
	private ArrayList<String> hands;

	public NpcType() {
		setSpriteIndex(0);
		setAnimationStartDelay(0);
		lastIterationTime = System.currentTimeMillis();
		lastIterationStartTime = System.currentTimeMillis();
	}

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

		if ((System.currentTimeMillis() - lastIterationStartTime) >= (getAnimationStartDelay()) && !isPauseAnimation()) {
			if ((System.currentTimeMillis() - lastIterationTime) >= (getAnimationSwitchDelay())) {
				lastIterationTime = System.currentTimeMillis();
				setSpriteIndex((getSpriteIndex() + 1) % getSprites().size());
				if (getSpriteIndex() >= getSprites().size() - 1) {
					lastIterationStartTime = System.currentTimeMillis();
				}
			}
		}
		if (!isDead()) {
			Point shifts = new Point();
			Game.instance.getPathWorker().nextCoordinate((int)getX(), (int)getY(), shifts);
	        move((int)shifts.getX(), (int)shifts.getY());
	        if (Utils.randInt(0, 100) > 95 && !isDead()) setHealth(getHealth()-2);
		}
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
		//TODO: need to move collision box size to configuration file so we can use diferent sizes for each type of unit
		int xMin = 4;
		int xMax = 12;

		int yMin = 4;
		int yMax = 12;

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

	public int getAnimationSwitchDelay() {
		return animationSwitchDelay;
	}

	public void setAnimationSwitchDelay(int animationSwitchDelay) {
		this.animationSwitchDelay = animationSwitchDelay;
	}

	public int getAnimationStartDelay() {
		return animationStartDelay;
	}

	public void setAnimationStartDelay(int animationStartDelay) {
		this.animationStartDelay = animationStartDelay;
	}
}
