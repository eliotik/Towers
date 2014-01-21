package org.game.towers.level.tiles;

import org.game.towers.gfx.Screen;
import org.game.towers.level.Level;

public class BasicTile extends Tile {

	protected int tileId;
	protected int tileColor;
	
	public BasicTile(int id, int x, int y, int tileColor, int levelColor) {
		super(id, false, false, levelColor);
		init(x + y * 32, tileColor);
	}
	
	private void init(int tileId, int tileColor) {
		this.tileId = tileId;
		this.tileColor = tileColor;		
	}

	public BasicTile(int id, int x, int y, int tileColor, boolean isSolid, int levelColor) {
		super(id, isSolid, false, levelColor);
		init(x + y * 32, tileColor);
	}
	
	public BasicTile(int id, int x, int y, int tileColor, boolean isSolid, boolean isEmitter, int levelColor) {
		super(id, isSolid, isEmitter, levelColor);
		init(x + y * 32, tileColor);
	}

	public void render(Screen screen, Level level, int x, int y) {
		screen.render(x, y, tileId, tileColor, 0x00, 1);
	}
	
	public void render(Screen screen, Level level, int x, int y, int mirrorDir) {
		screen.render(x, y, tileId, tileColor, mirrorDir, 1);
	}

	public void tick() {}
	
}
