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

		this.sprites = sprites;
		this.currentAnimationIndex = 0;
		this.animationStartDelay = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.lastIterationStartTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}

	public AnimatedTile(Level level, Sprite[] sprites, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y,
			int animationSwitchDelay, int animationStartDelay) {
		super(level, null, name, levelColor, isSolid, isEmitter, x, y);

		this.sprites = sprites;
		this.currentAnimationIndex = 0;
		this.animationStartDelay = animationStartDelay;
		this.lastIterationTime = System.currentTimeMillis();
		this.lastIterationStartTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}

	public void tick() {
		if ((System.currentTimeMillis() - lastIterationStartTime) >= (animationStartDelay)) {
			if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
				lastIterationTime = System.currentTimeMillis();
				currentAnimationIndex = (currentAnimationIndex + 1) % sprites.length;
				if (currentAnimationIndex >= sprites.length - 1) {
					lastIterationStartTime = System.currentTimeMillis();
				}
			}
		}
	}

	@Override
	public Sprite getSprite() {
		return sprites[currentAnimationIndex];
	}
}
