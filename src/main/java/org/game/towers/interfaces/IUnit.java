/**
 * 
 */
package org.game.towers.interfaces;

import java.awt.Rectangle;

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
	
	public IUnit setDamage(int damage);
	public int   getDamage();
	
	public IUnit  setSpeed(double speed);
	public double getSpeed();
	
	public IUnit setType(String type);
	public String getType();
	
	public IUnit  setTypeName(String typeName);
	public String getTypeName();
	
	public IUnit     setSize(Rectangle cell);
	public Rectangle getSize();
	
	public IUnit  setTileX(int tileX);
	public int    getTileX();
	
	public IUnit  setTileY(int tileY);
	public int    getTileY();

	public void  setX(int x);
	public int    getX();

	public void  setY(int y);
	public int    getY();

    public boolean isConstruction();
}
