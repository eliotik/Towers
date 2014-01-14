/**
 * 
 */
package org.game.towers.units;

import java.awt.Rectangle;

import org.game.towers.interfaces.IUnit;

/**
 * @author eliotik
 *
 */
public class Unit implements IUnit {
	private String id;
	private int armour;
	private int health;
	private int damage;
	private double speed;
	private String type;
	private String typeName;
	private Rectangle geo;
    private int x;
    private int y;
	private int tileX;
	private int tileY;

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setArmour(int)
	 */
	@Override
	public IUnit setArmour(int armour) {
		this.armour = armour;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getArmour()
	 */
	@Override
	public int getArmour() {
		return armour;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setHealth(int)
	 */
	@Override
	public IUnit setHealth(int health) {
		this.health = health;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getHealth()
	 */
	@Override
	public int getHealth() {
		return health;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setDamage(int)
	 */
	@Override
	public IUnit setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getDamage()
	 */
	@Override
	public int getDamage() {
		return damage;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setSpeed(double)
	 */
	@Override
	public IUnit setSpeed(double speed) {
		this.speed = speed;		
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getSpeed()
	 */
	@Override
	public double getSpeed() {
		return speed;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setType(java.lang.String)
	 */
	@Override
	public IUnit setType(String type) {
		this.type = type;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setTypeName(java.lang.String)
	 */
	@Override
	public IUnit setTypeName(String typeName) {
		this.typeName = typeName;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return typeName;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#setSize(java.awt.Rectangle)
	 */
	@Override
	public IUnit setGeo(Rectangle cell) {
		this.geo = cell;
		return this;
	}

	/* (non-Javadoc)
	 * @see org.game.towers.interfaces.IUnit#getSize()
	 */
	@Override
	public Rectangle getGeo() {
		return geo;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public IUnit setId(String id) {
		this.id = id;
		return this;
	}

	@Override
	public IUnit setTileX(int tileX) {
		this.tileX = tileX;
		return this;
	}

	@Override
	public int getTileX() {
		return tileX;
	}
	
	@Override
	public IUnit setTileY(int tileY) {
		this.tileY = tileY;
		return this;
	}
	
	@Override
	public int getTileY() {
		return tileY;
	}

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isConstruction() {
        return true;
    }


}
