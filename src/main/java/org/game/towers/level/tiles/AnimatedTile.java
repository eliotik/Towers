package org.game.towers.level.tiles;

import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public class AnimatedTile extends BasicTile{
	protected Sprite[] sprites;
	protected int currentAnimationIndex = 0;
	protected int currentAnimationIndexDir = +1;
	protected long lastTime;
	protected double animationLength = 0.1;

	public AnimatedTile(Level level, int id, Sprite[] sprites, String name,
			int colour, boolean isSolid, boolean isEmitter, int x, int y) {
		super(level, id, null, name, colour, isSolid, isEmitter, x, y);
		this.sprites = sprites;
		lastTime = System.currentTimeMillis();
	}

	public AnimatedTile(Level level, int id, Sprite[] sprites, String name,
			int colour, boolean isSolid, boolean isEmitter, int x, int y,
			double animationLength) {
		super(level, id, null, name, colour, isSolid, isEmitter, x, y);
		this.sprites = sprites;
		lastTime = System.currentTimeMillis();
		this.animationLength = animationLength;
	}

	public void tick() {
		super.tick();
		if (System.currentTimeMillis() - lastTime >= (animationLength * 1000D)) {
			switchRenderTile();
			lastTime = System.currentTimeMillis();
		}
	}

	public void switchRenderTile() {
		currentAnimationIndex = (currentAnimationIndex + currentAnimationIndexDir)
				% sprites.length;
		if (currentAnimationIndex == 0) {
			currentAnimationIndexDir = +1;
		} else if (currentAnimationIndex == sprites.length - 1) {
			currentAnimationIndexDir = -1;
		}
	}

	@Override
	public Sprite getSprite() {
		return sprites[currentAnimationIndex];
	}
//	private int[][] animationTileCoords;
//	private int currentAnimationIndex;
//	private long lastIterationTime;
//	private long lastIterationStartTime;
//	private int animationSwitchDelay;
//	private int animationStartDelay;
//
//	public AnimatedTile(int id, int[][] animationCoords, int tileColor, boolean isSolid,
//			boolean isEmitter, int levelColor, int animationSwitchDelay) {
//		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, isSolid, isEmitter, levelColor);
//		this.animationTileCoords = animationCoords;
//		this.currentAnimationIndex = 0;
//		this.animationStartDelay = 0;
//		this.lastIterationTime = System.currentTimeMillis();
//		this.lastIterationStartTime = System.currentTimeMillis();
//		this.animationSwitchDelay = animationSwitchDelay;
//	}
//
//	public AnimatedTile(int id, int[][] animationCoords, int tileColor, boolean isSolid,
//			int levelColor, int animationSwitchDelay) {
//		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, isSolid, false, levelColor);
//		this.animationTileCoords = animationCoords;
//		this.currentAnimationIndex = 0;
//		this.animationStartDelay = 0;
//		this.lastIterationTime = System.currentTimeMillis();
//		this.lastIterationStartTime = System.currentTimeMillis();
//		this.animationSwitchDelay = animationSwitchDelay;
//	}
//
//	public AnimatedTile(int id, int[][] animationCoords, int tileColor, boolean isSolid,
//			int levelColor, int animationSwitchDelay, int animationStartDelay) {
//		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, isSolid, false, levelColor);
//		this.animationTileCoords = animationCoords;
//		this.currentAnimationIndex = 0;
//		this.animationStartDelay = animationStartDelay;
//		this.lastIterationTime = System.currentTimeMillis();
//		this.lastIterationStartTime = System.currentTimeMillis();
//		this.animationSwitchDelay = animationSwitchDelay;
//	}
//
//	public void tick() {
//		if ((System.currentTimeMillis() - lastIterationStartTime) >= (animationStartDelay)) {
//			if ((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)) {
//				lastIterationTime = System.currentTimeMillis();
//				currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
//				tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
//				if (currentAnimationIndex >= animationTileCoords.length - 1) {
//					lastIterationStartTime = System.currentTimeMillis();
//				}
//			}
//		}
//	}
}
