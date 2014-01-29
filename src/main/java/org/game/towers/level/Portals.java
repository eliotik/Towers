package org.game.towers.level;

import org.game.towers.configs.Config;
import org.game.towers.level.tiles.Tile;
import org.game.towers.level.tiles.TileTypes;

public class Portals {

	private static Portal entrance = new Portal();
	private static Portal exit = new Portal();

	public static class Portal {
		private Tile tile;
		private int x;
		private int y;

		public Tile getTile() {
			return tile;
		}

		public void setTile(Tile tile) {
			this.tile = tile;
		}

		public int getX() {
			return x * Config.BOX_SIZE;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y * Config.BOX_SIZE;
		}

		public void setY(int y) {
			this.y = y;
		}
	}

	public static Portal getEntrance() {
		return entrance;
	}

	public static void setEntrance(Tile ent, int x, int y) {
		entrance.setTile(ent);
		entrance.setX(x);
		entrance.setY(y);
	}

	public static Portal getExit() {
		return exit;
	}

	public static void setExit(Tile ex, int x, int y) {
		exit.setTile(ex);
		exit.setX(x);
		exit.setY(y);
	}
}