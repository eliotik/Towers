package org.game.towers.level.tiles;

public class AnimatedTile extends BasicTile{

	private int[][] animationTileCoords;
	private int currentAnimationIndex;
	private long lastIterationTime;
	private long lastIterationStartTime;
	private int animationSwitchDelay;
	private int animationStartDelay;
	
	public AnimatedTile(int id, int[][] animationCoords, int tileColor, boolean isSolid,
			boolean isEmitter, int levelColor, int animationSwitchDelay) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, isSolid, isEmitter, levelColor);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.animationStartDelay = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.lastIterationStartTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}
	
	public AnimatedTile(int id, int[][] animationCoords, int tileColor, boolean isSolid,
			int levelColor, int animationSwitchDelay) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, isSolid, false, levelColor);
		this.animationTileCoords = animationCoords;
		this.currentAnimationIndex = 0;
		this.animationStartDelay = 0;
		this.lastIterationTime = System.currentTimeMillis();
		this.lastIterationStartTime = System.currentTimeMillis();
		this.animationSwitchDelay = animationSwitchDelay;
	}
	
	public AnimatedTile(int id, int[][] animationCoords, int tileColor, boolean isSolid,
			int levelColor, int animationSwitchDelay, int animationStartDelay) {
		super(id, animationCoords[0][0], animationCoords[0][1], tileColor, isSolid, false, levelColor);
		this.animationTileCoords = animationCoords;
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
				currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;
				tileId = (animationTileCoords[currentAnimationIndex][0] + (animationTileCoords[currentAnimationIndex][1] * 32));
				if (currentAnimationIndex >= animationTileCoords.length - 1) {
					lastIterationStartTime = System.currentTimeMillis();
				}
			}
		}
	}
}
