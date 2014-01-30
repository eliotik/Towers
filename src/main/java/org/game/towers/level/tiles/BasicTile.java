package org.game.towers.level.tiles;

import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public class BasicTile extends Tile {

	public BasicTile(Level level, int id, Sprite sprite, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y) {
		super(level, id, sprite, name, levelColor, isSolid, isEmitter, x, y);
	}

	public void tick() {}

	public void render(Screen screen) {
		screen.renderTile(getLevel(), getX(), getY(), this);
	}
}
