package org.game.towers.units;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.List;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Level;
import org.game.towers.game.level.tiles.Tile;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.units.npcs.Npc;
import org.game.towers.units.towers.Tower;
import org.game.towers.workers.Utils;

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
	private Level level;
	private int numSteps = 0;
	private boolean isMoving;
	private int movingDirection = 1;
	private int scale = 1;
	private int mirrorMask = 0x00;
	private List<Sprite> sprites;
	private int spriteIndex = 0;
	private boolean pauseAnimation = false;
	private boolean isDead = false;
	private boolean isFinished = false;
	private long lastIterationTime;
	private long lastIterationStartTime;
	private int animationSwitchDelay;
	private int animationStartDelay;
	private Point maxCollisionBox = new Point();
	private Point minCollisionBox = new Point();
	private boolean canBeRemoved = false;
	private long deathTime;
	private String modificator;
	private long lastTimeShooted;
	private double highlight = 1;

	public Unit() {
		setSpriteIndex(0);
		setAnimationStartDelay(0);
		setLastIterationTime(System.currentTimeMillis());
		setLastIterationStartTime(System.currentTimeMillis());
	}

	public void tick() {
		if ( (System.currentTimeMillis() - getLastIterationStartTime()) >= (getAnimationStartDelay()) &&
			(!isPauseAnimation()) ) {
			if ((System.currentTimeMillis() - getLastIterationTime()) >= (getAnimationSwitchDelay())) {
				setLastIterationTime(System.currentTimeMillis());
				setSpriteIndex((getSpriteIndex() + 1) % getSprites().size());
				if (getSpriteIndex() >= getSprites().size() - 1) {
					setLastIterationStartTime(System.currentTimeMillis());
				}
			}
		}
	}

	public abstract void render(Screen screen);

	public abstract boolean hasCollided(int xa, int ya);

	public Sprite getCurrentSprite() {
		return getSprite(getSpriteIndex());
	}

	protected boolean isSolidTile(double xa, double ya, double x, double y) {
		if (getLevel() == null) {
			return false;
		}
		Tile lastTile = getLevel().getTile((int)(getX() + x) >> Config.COORDINATES_SHIFTING, (int)(getY() + y) >> Config.COORDINATES_SHIFTING);
		Tile newTile = getLevel().getTile((int)(getX() + x + xa) >> Config.COORDINATES_SHIFTING, (int)(getY() + y + ya) >> Config.COORDINATES_SHIFTING);
		if (!lastTile.equals(newTile) && newTile.isSolid()) {
			return true;
		}
		return false;
	}

	public void setArmour(int armour) {
		this.armour = armour;
	}

	public int getArmour() {
		return armour;
	}

	public void setHealth(int health) {
		if (health < getHealth()) {
			setLastTimeShooted(System.currentTimeMillis());
		}
		this.health = health;
		if (health <= 0) {
			setDead(true);
			setSpriteIndex(0);
			setAnimationStartDelay(0);
			setAnimationSwitchDelay(800);
			setPauseAnimation(false);
			switch (Utils.randInt(0, 3)) {
			case 1:
				setMirrorMask(0x01);
				break;
			case 2:
				setMirrorMask(0x02);
				break;
			case 0:
			default:
				setMirrorMask(0x00);
				break;
			}

		}
	}

	public int getHealth() {
		return health;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getSpeed() {
		return speed;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setGeo(Rectangle cell) {
		this.geo = cell;
		this.x = (int) cell.getX();
		this.y = (int) cell.getY();
	}

	public Rectangle getGeo() {
		return geo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
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
		if (!isMoving() && !isDead() && this instanceof Npc) {
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

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished){
        this.isFinished = isFinished;
        if (isFinished) {
        	setCanBeRemoved(true);
        }
    }

    public void setDead(boolean isDead) {
		this.isDead = isDead;
		if (isDead) {
			setDeathTime(System.currentTimeMillis());
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

	public int getAnimationSwitchDelay() {
		return animationSwitchDelay;
	}

	public void setAnimationSwitchDelay(int animationSwitchDelay) {
		this.animationSwitchDelay = animationSwitchDelay;
	}

	public int getAnimationStartDelay() {
		return animationStartDelay;
	}

	public void setAnimationStartDelay(int animationStartDelay) {
		this.animationStartDelay = animationStartDelay;
	}

	public long getLastIterationTime() {
		return lastIterationTime;
	}

	public void setLastIterationTime(long lastIterationTime) {
		this.lastIterationTime = lastIterationTime;
	}

	public long getLastIterationStartTime() {
		return lastIterationStartTime;
	}

	public void setLastIterationStartTime(long lastIterationStartTime) {
		this.lastIterationStartTime = lastIterationStartTime;
	}

	public int getMirrorMask() {
		return mirrorMask;
	}

	public void setMirrorMask(int mirrorMask) {
		this.mirrorMask = mirrorMask;
	}

	public Point getMaxCollisionBox() {
		return maxCollisionBox;
	}

	public void setMaxCollisionBox(Point maxCollisionBox) {
		this.maxCollisionBox = maxCollisionBox;
	}

	public Point getMinCollisionBox() {
		return minCollisionBox;
	}

	public void setMinCollisionBox(Point minCollisionBox) {
		this.minCollisionBox = minCollisionBox;
	}

	public Sprite getSprite(int spriteIndex) {
		return getSprites().get(spriteIndex);
	}

	public int getTileX() {
		return (int) (Math.floor((double) getX() + 4) / Config.BOX_SIZE);
	}

	public int getTileY() {
		return (int) (Math.floor((double) getY() + 2) / Config.BOX_SIZE);
	}

	public boolean isCanBeRemoved() {
		if (isDead() && System.currentTimeMillis() - getDeathTime() >= Config.NPC_DEATH_MARK_VISIBILITY) {
			setCanBeRemoved(true);
		}
		return canBeRemoved;
	}

	public void setCanBeRemoved(boolean canBeRemoved) {
		this.canBeRemoved = canBeRemoved;
	}

	public long getDeathTime() {
		return deathTime;
	}

	public void setDeathTime(long deathTime) {
		this.deathTime = deathTime;
	}

	public String getModificator() {
		return modificator;
	}

	public void setModificator(String modificator) {
		this.modificator = modificator;
	}

	public long getLastTimeShooted() {
		return lastTimeShooted;
	}

	public void setLastTimeShooted(long lastTimeShooted) {
		this.lastTimeShooted = lastTimeShooted;
	}

	public double getHighlight() {
		return highlight;
	}

	public void setHighlight(double highlight) {
		this.highlight = highlight;
	}

	public int getPosition(int x, int y) {
		int position = 0;
		//0
//		if ( getX() >= x && getY() > y ) return position;
//		//1
//		if ( getX() < x && getY() > y ) return 1;
//		//2
//		if ( getX() < x  && getY() >= y ) return 1;
		//3
		//4
		//5
		//6
		//7



		return position;
	}
}
