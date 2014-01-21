/**
 * 
 */
package org.game.towers.towers;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

/**
 * @author eliotik
 *
 */
public class TowerType extends Unit {
	
	public TowerType(Level level) {
		//super(level);
	}

	private double radius;

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Screen screen) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		// TODO Auto-generated method stub
		return false;
	}
}
