package org.game.towers.buildings;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

public class BuildingType extends Unit {
	private static final long serialVersionUID = 1L;

	private int radarViewSize;

	private double value;

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Screen screen) {
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public int getRadarViewSize() {
		return radarViewSize;
	}

	public void setRadarViewSize(int radarViewSize) {
		this.radarViewSize = radarViewSize;
	}
}
