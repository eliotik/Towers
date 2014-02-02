/**
 *
 */
package org.game.towers.interfaces;

import java.awt.Rectangle;
import java.util.List;

import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

//import org.game.towers.level.Level;

/**
 * @author eliotik
 *
 */
public interface IUnit {
	public String getId();
	public IUnit  setId(String id);

	public IUnit setArmour(int armour);
	public int   getArmour();

	public IUnit setHealth(int health);
	public int   getHealth();

	public IUnit setMaxHealth(int maxHealth);
	public int   getMaxHealth();

	public IUnit setDamage(int damage);
	public int   getDamage();

	public IUnit  setSpeed(double speed);
	public double getSpeed();

	public IUnit setType(String type);
	public String getType();

	public IUnit  setTypeName(String typeName);
	public String getTypeName();

	public IUnit     setGeo(Rectangle cell);
	public Rectangle getGeo();

	public IUnit  setTileX(int tileX);
	public int    getTileX();

	public IUnit  setTileY(int tileY);
	public int    getTileY();

	public void  	setX(double x);
	public double	getX();

	public void  	setY(double y);
	public double   getY();

	public int 	getMovingDirection();
	public void setMovingDirection(int movingDirection);

	public int 	getScale();
	public void setScale(int scale);

	public boolean 	isMoving();
	public void 	setMoving(boolean isMoving);

	public int 	getNumSteps();
	public void setNumSteps(int numSteps);

	public Level 	getLevel();
	public void 	setLevel(Level level);

    public boolean isConstruction();

	public List<Sprite> getSprites();
	public void         setSprites(List<Sprite> sprites);

	public int  getSpriteIndex();
	public void setSpriteIndex(int spriteIndex);

	public boolean isPauseAnimation();
	public void    setPauseAnimation(boolean pauseAnimation);
}
