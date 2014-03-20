package org.game.towers.game.level;

import org.game.towers.workers.geo.Coordinates;

public class Portals {

	private static Portal entrance = new Portal();
	private static Portal exit = new Portal();

	public static class Portal {
		private Coordinates coordinates = new Coordinates();

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

	public static void setEntrance(Coordinates coord) {
		entrance.setCoordinates(coord);
	}

	public static Portal getExit() {
		return exit;
	}

	public static void setExit(Coordinates coord) {
		exit.setCoordinates(coord);
	}
}