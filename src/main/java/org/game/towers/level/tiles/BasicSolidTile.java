package org.game.towers.level.tiles;

public class BasicSolidTile extends BasicTile{
	
	public BasicSolidTile(int id, int x, int y, int tileColor, int LevelColor) {
		super(id, x, y, tileColor, LevelColor);
		this.solid = true;
	}


}
