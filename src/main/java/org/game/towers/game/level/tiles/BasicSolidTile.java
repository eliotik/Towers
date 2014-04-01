package org.game.towers.game.level.tiles;

import org.game.towers.game.level.Level;
import org.game.towers.gfx.sprites.Sprite;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(Level level, Sprite sprite, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y, double opacity) {
		super(level, sprite, name, levelColor, isSolid, isEmitter, x, y, opacity);
		this.setSolid(true);
	}


}
