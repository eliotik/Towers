package org.game.towers.game.level.tiles;

import org.game.towers.game.Config;
import org.game.towers.game.level.Level;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;

public class VoidTile extends Tile {

	public VoidTile(Level level, Sprite sprite, int x, int y) {
		super(level, sprite, Config.TILE_VOID, 0x000000, true, false, x, y);
	}

	public void render(Screen screen) {
		screen.renderTile(getLevel(), getX() * getSprite().getWidth(), getY() * getSprite().getHeight(), this);
	}

	public void tick() {}
}
