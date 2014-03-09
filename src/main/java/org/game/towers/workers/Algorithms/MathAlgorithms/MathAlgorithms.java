package org.game.towers.workers.Algorithms.MathAlgorithms;


import org.game.towers.geo.Coordinates;
import java.util.HashMap;

public class MathAlgorithms {

    public MathAlgorithms() {}

    public HashMap<Coordinates, Integer> getInscribedCoordinates(Coordinates coordinatesCenter, int radius) {
        Circle circle = new Circle(coordinatesCenter, radius);
        HashMap<Coordinates, Integer> inscribedCoordinates = circle.getInscribedCoordinates();

        return inscribedCoordinates;
    }

}
