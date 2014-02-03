/**
 *
 */
package org.game.towers.units;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.level.Level;
import org.game.towers.level.tiles.Tile;
import org.game.towers.workers.Utils;

/**
 * @author eliotik
 *
 */
public abstract class Unit implements Serializable {

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
	private boolean isDead = false;

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

	public Unit setArmour(int armour) {
		this.armour = armour;
		return this;
	}

	public int getArmour() {
		return armour;
	}

	public Unit setHealth(int health) {
		this.health = health;
		if (health <= 0) {
			setDead(true);
		}
		return this;
	}

	public int getHealth() {
		return health;
	}

	public Unit setDamage(int damage) {
		this.damage = damage;
		return this;
	}

	public int getDamage() {
		return damage;
	}

	public Unit setSpeed(double speed) {
		this.speed = speed;
		return this;
	}

	public double getSpeed() {
		return speed;
	}

	public Unit setType(String type) {
		this.type = type;
		return this;
	}

	public String getType() {
		return type;
	}

	public Unit setTypeName(String typeName) {
		this.typeName = typeName;
		return this;
	}

	public String getTypeName() {
		return typeName;
	}

	public Unit setGeo(Rectangle cell) {
		this.geo = cell;
		this.x = (int) cell.getX();
		this.y = (int) cell.getY();
		return this;
	}

	public Rectangle getGeo() {
		return geo;
	}

	public String getId() {
		return id;
	}

	public Unit setId(String id) {
		this.id = id;
		return this;
	}

	public Unit setTileX(int tileX) {
		this.tileX = tileX;
		return this;
	}

	public int getTileX() {
		return tileX;
	}

	public Unit setTileY(int tileY) {
		this.tileY = tileY;
		return this;
	}

	public int getTileY() {
		return tileY;
	}

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

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
		setPauseAnimation(!isMoving);
	}

	public int getNumSteps() {
		return numSteps;
	}

	public void setNumSteps(int numSteps) {
		if (this.numSteps + numSteps > this.numSteps) {
			setMoving(true);
		}
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

	public Unit setMaxHealth(int maxHealth) {
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
		if (!isMoving()) {
			setPauseAnimation(true);
		}
		return pauseAnimation;
	}

	public void setPauseAnimation(boolean pauseAnimation) {
		this.pauseAnimation = pauseAnimation;
		if (pauseAnimation) {
			spriteIndex = 0;
		}
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
		if (isDead) {
			switch (Utils.randInt(0, 8)) {
			case 1:
			case 2:
			case 3:
			case 4:
				getSprites().clear();
				getSprites().add(SpritesData.NPC_DEAD_0);
				getSprites().add(SpritesData.NPC_DEAD_1);
				getSprites().add(SpritesData.NPC_DEAD_2);
				break;
			default:
			case 0:
				getSprites().clear();
				getSprites().add(SpritesData.NPC_DEAD_3);
				getSprites().add(SpritesData.NPC_DEAD_4);
				getSprites().add(SpritesData.NPC_DEAD_5);
				break;
			}
		}
	}

	public Sprite getCurrentHealthSprite() {
		int healthPercent = (int) (((double) getHealth() / (double) getMaxHealth()) * (double) 100);
		if (healthPercent >= 100) {
			return SpritesData.HEALTH_MAX;
		}
		if (healthPercent >= 90) {
			return SpritesData.HEALTH_90;
		}
		if (healthPercent >= 80) {
			return SpritesData.HEALTH_80;
		}
		if (healthPercent >= 70) {
			return SpritesData.HEALTH_70;
		}
		if (healthPercent >= 60) {
			return SpritesData.HEALTH_60;
		}
		if (healthPercent >= 50) {
			return SpritesData.HEALTH_50;
		}
		if (healthPercent >= 40) {
			return SpritesData.HEALTH_40;
		}
		if (healthPercent >= 30) {
			return SpritesData.HEALTH_30;
		}
		if (healthPercent >= 20) {
			return SpritesData.HEALTH_20;
		}
		if (healthPercent >= 10) {
			return SpritesData.HEALTH_10;
		}
		return SpritesData.HEALTH_DEAD;
	}
}
