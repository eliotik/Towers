package org.game.towers.npcs;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.units.Unit;

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

		Point shifts = new Point();
		Game.instance.getPathWorker().nextCoordinate((int)getX(), (int)getY(), shifts);
        move((int)shifts.getX(), (int)shifts.getY());
        Random random = new Random();
        if (random.nextInt(10) > 7 && getHealth() > 0) setHealth(getHealth()-1);
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
