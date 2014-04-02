package org.game.towers.units.towers;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.units.ShootingUnit;

public class Tower extends ShootingUnit {

	private static final long serialVersionUID = 1L;

	private int price;
	private int radarViewSize;
	private int resources;

//	double maxX = 240 + 30;
//	double minX = 240 - 30;

	@Override
	public void tick() {
		super.tick();
//		return;
//		int xa = 0;
//
//		if (getX() < maxX && getMovingDirection() == 1) {
//			xa += 1;
//		}
//
//		if (getX() < maxX && getMovingDirection() == 3) {
//			xa += 1;
//		}
//		if (getX() >= maxX && getMovingDirection() == 3) {
//			xa -= 1;
//		}
//		if (getX() > minX && getMovingDirection() == 2) {
//			xa -= 1;
//		}
//		if (getX() <= minX && getMovingDirection() == 2) {
//			xa += 1;
//		}
//
//		if (xa != 0) {
//			if (xa < 0) {
//				setMovingDirection(2);
//			}
//			if (xa > 0) {
//				setMovingDirection(3);
//			}
//			setX(getX() + xa * (getSpeed()-4.5));
//			setNumSteps(getNumSteps() + 1);
//			setMoving(true);
//			Game.getInstance().getScreen().restoreFogLayer();
//			Game.getInstance().getScreen().refineFogLayer(getX()+Config.BOX_SIZE/2, getY()+Config.BOX_SIZE/2, getRadarViewSize());
//		} else {
//			setMoving(false);
//		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this, getMirrorMask(), getScale());
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRadarViewSize() {
		return radarViewSize;
	}

	public void setRadarViewSize(int radarViewSize) {
		this.radarViewSize = radarViewSize;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}
}
