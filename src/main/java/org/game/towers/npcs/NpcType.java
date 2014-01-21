/**
 * 
 */
package org.game.towers.npcs;

import java.util.ArrayList;

import org.game.towers.configs.Config;
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
	public void tick() {
		int xa = 0;
		
		if (getMovingDirection() != 2)
			setMovingDirection(3);
		
		if (getX() < Config.SCREEN_WIDTH - 8*2 && getMovingDirection() == 3) {
			xa++;
		} else if (getX() >= Config.SCREEN_WIDTH - 8*2 && getMovingDirection() == 3) {
			setMovingDirection(2);
		} else if (getX() > 8 && getMovingDirection() == 2) {
			xa--;			
		} else if (getX() <= 8 && getMovingDirection() == 2) {
			setMovingDirection(3);
		}
		if (xa != 0) {
			move(xa, 0);
			setMoving(true);
		} else {
			setMoving(false);
		}
//		setNumSteps(16);
//		setScale(1);
	}

	@Override
	public void render(Screen screen) {
		
		int walkingSpeed = 4;
		int flip = (getMovingDirection() - 1) % 2;
		int shift = ((getNumSteps() >> walkingSpeed) & 1);
		int xTile = getTileX() + shift;
		
		
		if (getMovingDirection() == 1) {
			xTile += 1 - shift * 2;
			flip = (getMovingDirection() - 1) % 2;
		} else if (getMovingDirection() > 1) {
			xTile += 2;
			flip = (getMovingDirection() - 1) % 2;
		}
//		System.out.println("dir = " +getMovingDirection()+ ", steps = "+ getNumSteps() + ", tile = "+ xTile+", flip = "+flip+", shift = "+shift);
//		int modifier = 8 * getScale();		
//		int xOffset = (int) (getX() - modifier / 2);
//		int yOffset = (int) (getY() - modifier / 2);
		
		screen.render((int) getX(), (int) getY(), xTile + getTileY() * 32, getColor(), flip, getScale());
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
