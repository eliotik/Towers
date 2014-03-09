package org.game.towers.workers.Algorithms.MathAlgorithms;


import org.game.towers.geo.Coordinates;
import java.util.HashMap;

public class MathAlgorithms {

    public MathAlgorithms() {}

    public static HashMap<Coordinates, Integer> getInscribedCoordinates(double x, double y, int radius) {
        Coordinates coordinatesCenter = new Coordinates(x, y);
        Circle circle = new Circle(coordinatesCenter, radius);

        return circle.getInscribedCoordinates();
    }


    // test
//    public static void main(String[] args) {
//        Coordinates coordinatesCenter = new Coordinates(100, 100);
//        int radius = 12;
//        Circle circle = new Circle(coordinatesCenter, radius);
//        HashMap<Coordinates, Integer> inscribedCoordinates = circle.getInscribedCoordinates();
//        System.out.println(inscribedCoordinates.size());
//    }

}
