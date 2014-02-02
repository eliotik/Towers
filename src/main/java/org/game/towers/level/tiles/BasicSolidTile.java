package org.game.towers.level.tiles;

import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.level.Level;

public class BasicSolidTile extends BasicTile{

	public BasicSolidTile(Level level, Sprite sprite, String name,
			int levelColor, boolean isSolid, boolean isEmitter, int x, int y) {
		super(level, sprite, name, levelColor, isSolid, isEmitter, x, y);
		this.setSolid(true);
	}


}
