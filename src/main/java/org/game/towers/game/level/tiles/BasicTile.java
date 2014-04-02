package org.game.towers.game.level.tiles;

import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;

public class BasicTile extends Tile {

	public BasicTile(Level level, Sprite sprite, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y, double opacity,
			boolean radiant, int radiantRadius, boolean radiantRepeater) {
		super(level, sprite, name, levelColor, isSolid, isEmitter, x, y, opacity, radiant, radiantRadius, radiantRepeater);
	}

	public BasicTile(Level level, Sprite sprite, String name, int levelColor,
			boolean isSolid, boolean isEmitter, int x, int y, int mirrorMask, double opacity,
			boolean radiant, int radiantRadius, boolean radiantRepeater) {
		super(level, sprite, name, levelColor, isSolid, isEmitter, x, y, opacity, radiant, radiantRadius, radiantRepeater);
		setMirrorMask(mirrorMask);
	}

	public void tick() {}

	public void render(Screen screen) {
		screen.renderTile(getLevel(), getX(), getY(), this);
	}
}
