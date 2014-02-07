package org.game.towers.towers;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

public class TowerType extends Unit {

	private static final long serialVersionUID = 1L;
	private double radius;

	public TowerType() {
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
	}

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this, getMirrorMask(), getScale());
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		// TODO Auto-generated method stub
		return false;
	}
}
