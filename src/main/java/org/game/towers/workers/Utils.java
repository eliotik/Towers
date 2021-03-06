package org.game.towers.workers;

import java.awt.Point;
import java.util.HashMap;
import java.util.Random;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.workers.Algorithms.MathAlgorithms.MathAlgorithms;
import org.game.towers.workers.geo.Coordinates;

public class Utils {
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	public static int randInt(int min, int max) {

	    // Usually this can be a field rather than a method variable
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

	public static Point transformMousePositionToScreen(double x, double y) {
		return new Point((int)(x/Config.SCALE), (int)(y/Config.SCALE));
	}

	public static Point transformMousePositionToLevel(double x, double y) {
		Point screenPosition = transformMousePositionToScreen(x, y);
		Screen screen = Game.getInstance().getScreen();
		return new Point((int)(screenPosition.getX()+screen.getxOffset()), (int)(screenPosition.getY() + screen.getyOffset()));
	}

//	public static HashMap<Coordinates, Integer> getCirclePixels(int radius, double x, double y) {
//		double minX = x - radius;
//		if (minX < 0) minX = 0;
//		double maxX = x + radius;
//		if (maxX > Config.SCREEN_WIDTH) maxX = Config.SCREEN_WIDTH;
//
//		double minY = y - radius;
//		if (minY < 0) minY = 0;
//
//		double maxY = y + radius;
//		if (maxY > Config.REAL_SCREEN_HEIGHT) maxY = Config.REAL_SCREEN_HEIGHT;
//
//		HashMap<Coordinates, Integer> circle = new HashMap<Coordinates, Integer>();
//		for(double i = minX; i < maxX; i++) {
//			Coordinates coordinates = new Coordinates(i, y);
//			circle.put(coordinates, 2);
//		}
//		for(double i = minY; i < maxY; i++) {
//			Coordinates coordinates = new Coordinates(x, i);
//			circle.put(coordinates, 2);
//		}
//
//		return circle;
//	}

    public static int doShift(int dimension1, int dimension2) {
        int deltaDim = dimension2 - dimension1;
        if (deltaDim > 0){
            return 1;
        }
        if (deltaDim < 0){
            return -1;
        }

        return 0;
    }

	public static HashMap<Coordinates, Integer> getVisiblePixels(String type, double x, double y, int radarSize) {
		switch(type) {
		case Config.VIEW_TYPE_LIGHT: return MathAlgorithms.getLightCoordinates(x, y, radarSize);
		default:
		case Config.VIEW_TYPE_CIRCLE: return MathAlgorithms.getInscribedCoordinates(x, y, radarSize);
		}
	}

	public static int getLineLength(double fromX, double fromY, int toX, int toY) {
		return (int) Math.abs(Math.sqrt(Math.pow(toX - fromX, 2) + Math.pow(toY - fromY, 2)));
	}
}
