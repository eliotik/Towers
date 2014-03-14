package org.game.towers.towers;

import java.awt.Point;
import java.util.Iterator;

import org.game.towers.bullets.BulletType;
import org.game.towers.bullets.BulletTypesCollection;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;
import org.game.towers.npcs.NpcType;
import org.game.towers.units.Unit;
import org.game.towers.units.UnitFactory;

public class TowerType extends Unit {

	private static final long serialVersionUID = 1L;

	private double radius;
	private int price;
	private int radarViewSize;
	private String bulletType;
	private int resources;
	private boolean canShoot = false;
	private double shootingDelay;
	private long lastShootTime;

	public TowerType() {
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
		if (isCanShoot()) {
			checkShootingPossibility();
		}
	}

	private void checkShootingPossibility() {
		synchronized (Game.instance.getWorld().getLevel().getUnits()) {
			Level level = Game.instance.getWorld().getLevel();
			for (int i = 0, l = level.getUnits().size(); i < l; i++) {
				if (i > level.getUnits().size()-1) break;
				Unit unit = (Unit) level.getUnits().get(i);
				if (unit instanceof NpcType) {
					int distanceToTarget = getDistanceToTarget((int)unit.getX(), (int)unit.getY());
					if ( distanceToTarget <= getRadius() && (System.currentTimeMillis() - getLastShootTime()) >= getShootingDelay() * 1000D) {
						shoot((int)unit.getX(), (int)unit.getY());
						return;
					}
				}
			}


//			Iterator<Unit> it = Game.instance.getWorld().getLevel().getUnits().iterator();
//			while (it.hasNext()) {
//				Unit unit = (Unit) it.next();
//				if (unit instanceof NpcType) {
//
//					int distanceToTarget = getDistanceToTarget((int)unit.getX(), (int)unit.getY());
////					System.out.println(getX()+":"+getY()+", "+unit.getX()+":"+unit.getY()+", "+unit.getTileX()+":"+unit.getTileY()+", "+distanceToTarget+":"+getRadius());
////					int distanceToTarget = getDistanceToTarget(unit.getTileX(), unit.getTileY());
//					if ( distanceToTarget <= getRadius() && (System.currentTimeMillis() - getLastShootTime()) >= getShootingDelay() * 1000D) {
//						shoot((int)unit.getX(), (int)unit.getY());
////						shoot(unit.getTileX(), unit.getTileY());
//						return;
//					}
//				}
//			}

//			for (Iterator<Unit> it = Game.instance.getWorld().getLevel().getUnits().iterator(); it.hasNext();) {
//				Unit unit = (Unit) it.next();
//				if (unit instanceof NpcType) {
//
//					int distanceToTarget = getDistanceToTarget((int)unit.getX(), (int)unit.getY());
////					System.out.println(getX()+":"+getY()+", "+unit.getX()+":"+unit.getY()+", "+unit.getTileX()+":"+unit.getTileY()+", "+distanceToTarget+":"+getRadius());
////					int distanceToTarget = getDistanceToTarget(unit.getTileX(), unit.getTileY());
//					if ( distanceToTarget <= getRadius() && (System.currentTimeMillis() - getLastShootTime()) >= getShootingDelay() * 1000D) {
//						shoot((int)unit.getX(), (int)unit.getY());
////						shoot(unit.getTileX(), unit.getTileY());
//						return;
//					}
//				}
//			}
		}
	}

	private void shoot(int x, int y) {
		setLastShootTime(System.currentTimeMillis());

		BulletType bullet = UnitFactory.getBullet(getBulletType(), this);
		bullet.setPoint(new Point(x, y));
		bullet.setX(getX());
		bullet.setY(getY());

		Game.instance.getWorld().getLevel().addBullet(bullet);
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
