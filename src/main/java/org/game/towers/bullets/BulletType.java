package org.game.towers.bullets;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

public class BulletType extends Unit {

	private static final long serialVersionUID = 1L;

	private double distance;
	private Unit owner;

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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setOwner(Unit owner) {
		this.owner = owner;
	}

	public Unit getOwner() {
		return owner;
	}
}
