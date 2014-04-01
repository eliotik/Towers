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
//        System.out.println("getInscribedCoordinates size = " + la.getInscribedCoordinates().size());
        return la.getInscribedCoordinates();
    }



//    public static void main(String[] args) {
//
//    }

}
