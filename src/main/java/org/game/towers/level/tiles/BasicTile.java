package org.game.towers.level.tiles;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;

public class BasicTile extends Tile {

	protected int tileId;
	protected int tileColor;
	
	public BasicTile(int id, int x, int y, int tileColor) {
		super(id, false, false);
		this.tileId = x + y;
		this.tileColor = tileColor;
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor);
	}
	
	public void render(Screen screen, Level level, int x, int y, int mirrorDir) {
		screen.render(x, y, tileId, tileColor, mirrorDir);
	}

}
