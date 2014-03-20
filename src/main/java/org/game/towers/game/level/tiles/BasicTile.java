package org.game.towers.game.level.tiles;

import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;

public class BasicTile extends Tile {

	public BasicTile(Level level, Sprite sprite, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y) {
		super(level, sprite, name, levelColor, isSolid, isEmitter, x, y);
	}

	public BasicTile(Level level, Sprite sprite, String name, int levelColor,
			boolean isSolid, boolean isEmitter, int x, int y, int mirrorMask) {
		super(level, sprite, name, levelColor, isSolid, isEmitter, x, y);
		setMirrorMask(mirrorMask);
	}

	public void tick() {}

	public void render(Screen screen) {
		screen.renderTile(getLevel(), getX(), getY(), this);
	}
}