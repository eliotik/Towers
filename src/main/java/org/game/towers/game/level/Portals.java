package org.game.towers.game.level;

import java.util.ArrayList;
import java.util.List;

import org.game.towers.game.Config;
import org.game.towers.workers.Utils;
import org.game.towers.workers.geo.Coordinates;

public class Portals {

	private static List<Portal> entrances = new ArrayList<Portal>();
	private static List<Portal> exits = new ArrayList<Portal>();

	private static Portal entrance = new Portal();
	private static Portal exit = new Portal();

	public static class Portal {
		private Coordinates coordinates = new Coordinates();

		public Portal() {}

		public Portal(Coordinates coordinates) {
			this.coordinates = coordinates;
		}

		public Coordinates getCoordinates() {
			return coordinates;
		}

		public void setCoordinates(Coordinates coordinates) {
			this.coordinates = coordinates;
		}
	}

	public static Portal getEntrance() {
		return entrance;
	}

	public static void setEntrance(Portal portal) {
		entrance = portal;
	}

	public static Portal getExit() {
		return exit;
	}

	public static void setExit(Portal portal) {
		exit = portal;
	}

	public static List<Portal> getEntrances() {
		return entrances;
	}

	public static void setEntrances(List<Portal> entrances) {
		Portals.entrances = entrances;
	}

	public static List<Portal> getExits() {
		return exits;
	}

	public static void setExits(List<Portal> exits) {
		Portals.exits = exits;
	}

	public static void addEntrance(Portal entrance) {
		getEntrances().add(entrance);
	}

	public static void addExit(Portal exit) {
		getExits().add(exit);
	}

	public static Portal createPortal(int x, int y) {
		Coordinates coordinates  = new Coordinates();
		coordinates.setX(x << Config.COORDINATES_SHIFTING);
		coordinates.setY(y << Config.COORDINATES_SHIFTING);
		return new Portal(coordinates);
	}

	public static void assign() {
		setEntrance(getEntrances().get(Utils.randInt(0, getEntrances().size()-1)));
		setExit(getExits().get(Utils.randInt(0, getExits().size()-1)));
	}
}