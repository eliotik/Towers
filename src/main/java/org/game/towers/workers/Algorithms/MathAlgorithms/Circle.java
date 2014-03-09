package org.game.towers.workers.Algorithms.MathAlgorithms;


import ch.lambdaj.Lambda.*;
import org.game.towers.geo.Coordinates;
import org.hamcrest.Matchers;

import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;


class Circle {
    private Coordinates coordinates;
    private int radius;
    int minX, maxX, minY, maxY;
    private ArrayList<Coordinates> circle = new ArrayList<Coordinates>();
    private Coordinates[][] circleEqual;
    private double epsilon = 0.1;


    public Circle(Coordinates coordinatesCenter, int radius) {
        this.coordinates = coordinatesCenter;
        this.radius = radius;
        this.minX = coordinates.getX() - radius;
        this.maxX = coordinates.getX() + radius;
        this.minY = coordinates.getY() - radius;
        this.maxY = coordinates.getY() + radius;
    }

    private void circleEqual(){
        for(int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                double radiusVector  = java.lang.Math.pow((double)(x - coordinates.getX()), 2) +
                                       java.lang.Math.pow((double)(y - coordinates.getY()), 2);

                if ((radiusVector / java.lang.Math.pow((double)radius, 2)) < 1 + epsilon &&
                    (radiusVector / java.lang.Math.pow((double)radius, 2)) > 1 - epsilon) {
                    circle.add(new Coordinates(x, y));
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
            List<Coordinates> listCoordinates = filter(having(on(Coordinates.class).getX(), Matchers.equalTo(x)), circle);
            fromY = listCoordinates.get(0).getY();
            toY = listCoordinates.get(1).getY();
            for(Coordinates item : listCoordinates){
                if (fromY >  item.getY()) {
                    fromY = item.getY();
                }
                if (toY < item.getY()){
                    toY = item.getY();
                }
            }
            for (int y = fromY; y <= toY; y++) {
                inscribedCoordinates.put(new Coordinates(x, y), 0);
            }
        }

        return inscribedCoordinates;
    }

}
