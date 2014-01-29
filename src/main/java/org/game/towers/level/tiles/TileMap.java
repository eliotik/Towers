package org.game.towers.level.tiles;

import org.game.towers.geo.Geo;

public class TileMap {
	private Geo geo;
	private byte tileId;

	public TileMap(byte tId, Geo geo) {
		setTileId(tId);
		setGeo(geo);
	}

	public Geo getGeo() {
		return geo;
	}

	public void setGeo(Geo geo) {
		this.geo = geo;
	}

	public byte getTileId() {
		return tileId;
	}

	public void setTileId(byte id) {
		this.tileId = id;
	}

}