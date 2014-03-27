package org.game.towers.units;

import org.game.towers.gfx.Screen;

public class ShootingUnit extends Unit {

	private static final long serialVersionUID = 1L;

	private double radius;
	private String bulletType;
	private boolean canShoot = false;
	private double shootingDelay;
	private long lastShootTime;
	private boolean allowToShoot = true;

	@Override
	public void render(Screen screen) {}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getBulletType() {
		return bulletType;
	}

	public void setBulletType(String bulletType) {
		this.bulletType = bulletType;
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

	public boolean isAllowToShoot() {
		return allowToShoot;
	}

	public void setAllowToShoot(boolean allowToShoot) {
		this.allowToShoot = allowToShoot;
	}

}
