/**
 * 
 */
package org.game.towers.npcs;

import java.util.ArrayList;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;

/**
 * @author eliotik
 *
 */
public class NpcType extends Unit {
	
	private static final long serialVersionUID = 1L;

//	public NpcType(Level level) {
//		super(level);
//	}

	private ArrayList<String> hands;

	public ArrayList<String> getHands() {
		return hands;
	}

	public void setHands(ArrayList<String> hands) {
		this.hands = hands;
	}

    public boolean isConstruction() {
        return false;
    }

	@Override
	public void tick() {}

	@Override
	public void render(Screen screen) {
		int modifier = 8 * getScale();
		int xOffset = (int) (getX() - modifier / 2);
		int yOffset = (int) (getY() - modifier / 2);
		
		screen.render((int) getX(), (int) getY(), getTileX() + getTileY() * 32, getColor());
	}
	
	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			setNumSteps(getNumSteps() - 1);
			return;
		}
		
		setNumSteps(getNumSteps() + 1);
		
		if(!hasCollided(xa, ya)) {
			if (ya < 0) {
				setMovingDirection(0);
			}
			if (ya > 0) {
				setMovingDirection(1);
			}
			if (xa < 0) {
				setMovingDirection(2);
			}
			if (xa > 0) {
				setMovingDirection(3);
			}
			setX(getX() + xa * getSpeed());
			setY(getY() + ya * getSpeed());
		}
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}
}
