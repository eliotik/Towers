package org.game.towers.workers.Algorithms.MathAlgorithms;


import org.game.towers.workers.geo.Coordinates;

import java.util.HashMap;

public class MathAlgorithms {

    public MathAlgorithms() {}

    public static HashMap<Coordinates, Integer> getInscribedCoordinates(double x, double y, int radius) {
        Coordinates coordinatesCenter = new Coordinates(x, y);
        Circle circle = new Circle(coordinatesCenter, radius);

        return circle.getInscribedCoordinates();
    }

    public static HashMap<Coordinates, Integer> getLightCoordinates(double x, double y, int radius) {
        Coordinates coordinatesCenter = new Coordinates(x, y);
        LightArea la = new LightArea(coordinatesCenter, radius);
        System.out.println("getInscribedCoordinates size = " + la.getInscribedCoordinates().size());
        return la.getInscribedCoordinates();
    }

    public static int dependentYFromX(int x, int x1, int x2, int y1, int y2) {
        double aFactor = (x - x1) / (x2 - x1);
        int y = (int)(y2 + ( aFactor * (y2 - y1) ));
        return y;
    }

    public static int dependentXFromY(int y, int x1, int x2, int y1, int y2) {
        double aFactor = (x2 - x1) / (y2 - y1);
        int x = (int)(aFactor * (y - y2) + x1);
        return x;
    }

}
