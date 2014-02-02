/**
 *
 */
package org.game.towers.units;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.interfaces.IUnit;
import org.game.towers.level.Level;
import org.game.towers.level.tiles.Tile;

/**
 * @author eliotik
 *
 */
public abstract class Unit implements IUnit, Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private int armour;
	private int health;
	private int maxHealth;
	private int damage;
	private double speed;
	private String type;
	private String typeName;
	private Rectangle geo;
    private double x;
    private double y;
	private int tileX;
	private int tileY;
	private Level level;
	private int numSteps = 0;
	private boolean isMoving;
	private int movingDirection = 1;
	private int scale = 1;
	private List<Sprite> sprites;
	private int spriteIndex = 0;
	private boolean pauseAnimation = false;

	public Unit() {}

	public abstract void tick();

	public abstract void render(Screen screen);

	public abstract boolean hasCollided(int xa, int ya);

	public Sprite getCurrentSprite() {
		return getSprites().get(getSpriteIndex());
	}

	protected boolean isSolidTile(int xa, int ya, int x, int y) {
		if (getLevel() == null) {
			return false;
		}
		Tile lastTile = getLevel().getTile((int)(getX() + x) >> 4, (int)(getY() + y) >> 4);
		Tile newTile = getLevel().getTile((int)(getX() + x + xa) >> 4, (int)(getY() + y + ya) >> 4);
		if (!lastTile.equals(newTile) && newTile.isSolid()) {
			return true;
		}
		return false;
	}

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
		this.x = (int) cell.getX();
		this.y = (int) cell.getY();
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
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public boolean isConstruction() {
        return true;
    }

	public int getMovingDirection() {
		return movingDirection;
	}

	public void setMovingDirection(int movingDirection) {
		this.movingDirection = movingDirection;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public int getNumSteps() {
		return numSteps;
	}

	public void setNumSteps(int numSteps) {
		this.numSteps = numSteps;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public IUnit setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		return this;
	}

	public List<Sprite> getSprites() {
		return sprites;
	}

	public void setSprites(List<Sprite> sprites) {
		this.sprites = sprites;
	}

	public int getSpriteIndex() {
		return spriteIndex;
	}

	public void setSpriteIndex(int spriteIndex) {
		this.spriteIndex = spriteIndex;
	}

	public boolean isPauseAnimation() {
		return pauseAnimation;
	}

	public void setPauseAnimation(boolean pauseAnimation) {
		this.pauseAnimation = pauseAnimation;
		if (pauseAnimation) {
			spriteIndex = 0;
		}
	}
}
