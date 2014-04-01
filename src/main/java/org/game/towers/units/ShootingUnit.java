package org.game.towers.units;

import java.awt.Point;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.units.bullets.Bullet;
import org.game.towers.units.npcs.Npc;
import org.game.towers.workers.Utils;

public class ShootingUnit extends Unit {

	private static final long serialVersionUID = 1L;

	private double radius;
	private String bulletType;
	private boolean canShoot = false;
	private double shootingDelay;
	private long lastShootTime;
	private boolean allowToShoot = true;

	public ShootingUnit() {
		setLastShootTime((long) (System.currentTimeMillis() - (getShootingDelay() * 1000D)));
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

		bullet.setEndPoint(new Point(x + halfBox, y + halfBox));
		bullet.setStartPoint(new Point((int) getX(), (int) getY()));
		bullet.setX(getX());
		bullet.setY(getY());

		Game.getInstance().getWorld().getLevel().addBullet(bullet);
		if (Config.UNIT_ONE_SHOOT_STRATEGY) setAllowToShoot(false);
	}

	private int getDistanceToTarget(int x, int y) {

		return Utils.getLineLength(getX(), getY(), x, y);
	}

	@Override
	public void tick() {
		super.tick();
		if (isCanShoot() && isAllowToShoot()) {
			checkShootingPossibility();
		}
	}

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

	@Override
	public void onMouseHover() {
		setHighlight(1.5);
	}

	@Override
	public void onMouseOut() {
		setHighlight(1);
	}

	@Override
	public void onMouseHolded() {}

	@Override
	public void onMouseClicked() {}

}
