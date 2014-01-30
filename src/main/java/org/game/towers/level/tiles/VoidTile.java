package org.game.towers.level.tiles;

import org.game.towers.configs.Config;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public class VoidTile extends Tile {

	public VoidTile(Level level, int id, Sprite sprite, int x, int y) {
		super(level, id, sprite, Config.TILE_VOID, 0x000000, true, false, x, y);
	}

	public void render(Screen screen) {
		screen.renderTile(getLevel(), getX() * getSprite().getWidth(), getY() * getSprite().getHeight(), this);
	}

	public void tick() {}
}
