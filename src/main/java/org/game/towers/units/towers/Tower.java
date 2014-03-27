package org.game.towers.units.towers;

import java.awt.Point;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.units.ShootingUnit;
import org.game.towers.units.Unit;
import org.game.towers.units.UnitFactory;
import org.game.towers.units.bullets.Bullet;
import org.game.towers.units.npcs.Npc;

public class Tower extends ShootingUnit {

	private static final long serialVersionUID = 1L;

	private int price;
	private int radarViewSize;
	private int resources;

	public Tower() {
		setLastShootTime((long) (System.currentTimeMillis() - (getShootingDelay() * 1000D)));
	}

	@Override
	public void tick() {
		super.tick();
		if (isCanShoot() && isAllowToShoot()) {
			checkShootingPossibility();
		}
	}

	private void checkShootingPossibility() {
		synchronized (Game.getInstance().getWorld().getLevel().getUnits()) {
			Level level = Game.getInstance().getWorld().getLevel();
			for (int i = 0, l = level.getUnits().size(); i < l; i++) {
				if (i > level.getUnits().size()-1) break;
				Unit unit = (Unit) level.getUnits().get(i);
				if (unit instanceof Npc && !unit.isDead()) {
					int distanceToTarget = getDistanceToTarget((int)unit.getX(), (int)unit.getY());
					if ( distanceToTarget <= getRadius() && (System.currentTimeMillis() - getLastShootTime()) >= getShootingDelay() * 1000D) {
						shoot((int)unit.getX(), (int)unit.getY(), unit);
						return;
					}
				}
			}
		}
	}

	private void shoot(int x, int y, Unit unit) {
		setLastShootTime(System.currentTimeMillis());

		int halfBox = (int) (Config.BOX_SIZE/2 + (Config.BOX_SIZE/2)*unit.getSpeed());
		Bullet bullet = UnitFactory.getBullet(getBulletType(), this, unit);

		switch(unit.getMovingDirection()) {
		case 0:

			y -= halfBox + bullet.getMaxCollisionBox().getY();
			if (y < 0) y = 0;
			break;
		case 1:
			y += halfBox - bullet.getMaxCollisionBox().getY();
			break;
		case 2:
			x -= halfBox + bullet.getMaxCollisionBox().getX();
			if (x < 0) x = 0;
			break;
		case 3:
			x += halfBox -  bullet.getMaxCollisionBox().getX();
			break;
		}

		bullet.setEndPoint(new Point(x + halfBox, y + halfBox));
		bullet.setStartPoint(new Point((int) getX(), (int) getY()));
		bullet.setX(getX());
		bullet.setY(getY());

		Game.getInstance().getWorld().getLevel().addBullet(bullet);
		setAllowToShoot(false);
	}

	private int getDistanceToTarget(int x, int y) {
		return (int) Math.abs(Math.sqrt(Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2)));
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
