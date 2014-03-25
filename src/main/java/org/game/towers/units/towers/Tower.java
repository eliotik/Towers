package org.game.towers.units.towers;

import java.awt.Point;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.units.Unit;
import org.game.towers.units.UnitFactory;
import org.game.towers.units.bullets.Bullet;
import org.game.towers.units.npcs.Npc;

public class Tower extends Unit {

	private static final long serialVersionUID = 1L;

	private double radius;
	private int price;
	private int radarViewSize;
	private String bulletType;
	private int resources;
	private boolean canShoot = false;
	private double shootingDelay;
	private long lastShootTime;
	private boolean allowToShoot = true;

	public Tower() {
		setLastShootTime((long) (System.currentTimeMillis() - (getShootingDelay() * 1000D)));
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public void tick() {
		super.tick();
		if (isCanShoot() && allowToShoot) {
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
		int position = getPosition(x, y);

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
		allowToShoot = false;
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

	public String getBulletType() {
		return bulletType;
	}

	public void setBulletType(String bulletType) {
		this.bulletType = bulletType;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}

	public boolean isCanShoot() {
		return canShoot;
	}

	public void setCanShoot(boolean canShoot) {
		this.canShoot = canShoot;
	}

	public double getShootingDelay() {
		return shootingDelay;
	}

	public void setShootingDelay(double shootingDelay) {
		this.shootingDelay = shootingDelay;
	}

	public long getLastShootTime() {
		return lastShootTime;
	}

	public void setLastShootTime(long lastShootTime) {
		this.lastShootTime = lastShootTime;
	}
}
