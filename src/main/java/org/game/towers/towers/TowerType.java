package org.game.towers.towers;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

public class TowerType extends Unit {

	private static final long serialVersionUID = 1L;

	private double radius;
	private int price;
	private int radarViewSize;
	private String bulletType;

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public void tick() {
		super.tick();
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
}
