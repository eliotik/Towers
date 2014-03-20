package org.game.towers.game.level.tiles;

import org.game.towers.workers.geo.Geo;

public class TileMap {
	private Geo geo;
	private Tile tile;

	public TileMap(Tile tile, Geo geo) {
		setTile(tile);
		setGeo(geo);
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}

}