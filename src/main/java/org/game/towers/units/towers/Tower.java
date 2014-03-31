package org.game.towers.units.towers;

import java.awt.Rectangle;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.units.ShootingUnit;

public class Tower extends ShootingUnit {

	private static final long serialVersionUID = 1L;

	private int price;
	private int radarViewSize;
	private int resources;

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

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}
}
