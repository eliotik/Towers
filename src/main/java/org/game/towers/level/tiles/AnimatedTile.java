package org.game.towers.level.tiles;

import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public class AnimatedTile extends BasicTile{
	private Sprite[] sprites;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private long lastIterationStartTime;
	private int animationSwitchDelay;
	private int animationStartDelay;

	public AnimatedTile(Level level, Sprite[] sprites, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y,
			int animationSwitchDelay) {
		super(level, null, name, levelColor, isSolid, isEmitter, x, y);

		setSprites(sprites);
		setCurrentAnimationIndex(0);
		setAnimationStartDelay(0);
		setLastIterationTime(System.currentTimeMillis());
		setLastIterationStartTime(System.currentTimeMillis());
		setAnimationSwitchDelay(animationSwitchDelay);
	}

	public AnimatedTile(Level level, Sprite[] sprites, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y,
			int animationSwitchDelay, int animationStartDelay) {
		super(level, null, name, levelColor, isSolid, isEmitter, x, y);

		setSprites(sprites);
		setCurrentAnimationIndex(0);
		setAnimationStartDelay(animationStartDelay);
		setLastIterationTime(System.currentTimeMillis());
		setLastIterationStartTime(System.currentTimeMillis());
		setAnimationSwitchDelay(animationSwitchDelay);
	}

	public void tick() {
		if ((System.currentTimeMillis() - getLastIterationStartTime()) >= (getAnimationStartDelay())) {
			if ((System.currentTimeMillis() - getLastIterationTime()) >= (getAnimationSwitchDelay())) {
				setLastIterationTime(System.currentTimeMillis());
				setCurrentAnimationIndex((getCurrentAnimationIndex() + 1) % getSprites().length);
				if (getCurrentAnimationIndex() >= getSprites().length - 1) {
					setLastIterationStartTime(System.currentTimeMillis());
				}
			}
		}
	}

	@Override
	public Sprite getSprite() {
		return getSprites()[getCurrentAnimationIndex()];
	}

	public int getCurrentAnimationIndex() {
		return currentAnimationIndex;
	}

	public void setCurrentAnimationIndex(int currentAnimationIndex) {
		this.currentAnimationIndex = currentAnimationIndex;
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

	public Sprite[] getSprites() {
		return sprites;
	}

	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
}
