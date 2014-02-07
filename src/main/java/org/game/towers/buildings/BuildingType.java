package org.game.towers.buildings;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

public class BuildingType extends Unit {
	private static final long serialVersionUID = 1L;

	public BuildingType(Level level) {
//		super(level);
	}

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
		// TODO Auto-generated method stub
		return false;
	}
}
