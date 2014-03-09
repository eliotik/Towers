package org.game.towers.workers.Algorithms.MathAlgorithms;


import org.game.towers.geo.Coordinates;

import java.lang.*;
import java.util.HashMap;

class Circle {
    private Coordinates coordinates;
    private int radius;
    private Coordinates[][] circleEqual;
    int minX, maxX, minY, maxY;

    public Circle(Coordinates coordinatesCenter, int radius) {
        this.coordinates = coordinatesCenter;
        this.radius = radius;
        minX = coordinates.getX() - radius;
        maxX = coordinates.getX() + radius;
        minY = coordinates.getY() - radius;
        maxY = coordinates.getY() + radius;
    }

    private void circleEqual(){
        for(int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (java.lang.Math.pow((double)(x - coordinates.getX()), 2) +
                    java.lang.Math.pow((double)(y - coordinates.getX()), 2) == java.lang.Math.pow((double)radius, 2)) {
                    circleEqual[x][y] = new Coordinates(x, y);
                }
            }
        }
    }

    public HashMap<Coordinates, Integer> getInscribedCoordinates(){
        circleEqual();
        int fromY = 0;
        int toY = 0;
        HashMap<Coordinates, Integer> inscribedCoordinates = new HashMap<Coordinates, Integer>();
        for (int x = minX; x <= maxX; x++) {
            if (circleEqual[x].length > 1) {
                for(Coordinates coordinate : circleEqual[x]) {
                    if (toY < coordinate.getY()) {
                        toY = coordinate.getY();
                    } else {
                        fromY = coordinate.getY();
                    }
                }
                for (int coordY = fromY; coordY <= toY; coordY++) {
                    inscribedCoordinates.put(new Coordinates(x, coordY), 0);
                }
            }

        }
        return inscribedCoordinates;
    }

}
