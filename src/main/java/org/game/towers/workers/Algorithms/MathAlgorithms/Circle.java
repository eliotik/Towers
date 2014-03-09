package org.game.towers.workers.Algorithms.MathAlgorithms;


import ch.lambdaj.Lambda.*;

import org.game.towers.configs.Config;
import org.game.towers.geo.Coordinates;
import org.hamcrest.Matchers;

import java.lang.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ch.lambdaj.Lambda.filter;
import static ch.lambdaj.Lambda.having;
import static ch.lambdaj.Lambda.on;


class Circle {
    private Coordinates coordinates;
    private int radius;
    private int minX, maxX, minY, maxY;
    private ArrayList<Coordinates> circle = new ArrayList<Coordinates>();
    private Coordinates[][] circleEqual;
    private double epsilon = 0.1;


    public Circle(Coordinates coordinatesCenter, int radius) {
        setCoordinates(coordinatesCenter);
        setRadius(radius);
        setMinX(getCoordinates().getX() - radius);
        setMaxX(getCoordinates().getX() + radius);
        setMinY(getCoordinates().getY() - radius);
        setMaxY(getCoordinates().getY() + radius);
		if (getMinX() < 0) setMinX(0);
		if (getMinY() < 0) setMinY(0);
		if (getMaxX() > Config.SCREEN_WIDTH) setMaxX(Config.SCREEN_WIDTH);
		if (getMaxY() > Config.REAL_SCREEN_HEIGHT) setMaxY(Config.REAL_SCREEN_HEIGHT);
    }

    private void circleEqual(){
        for(int x = getMinX(); x <= getMaxX(); x++) {
            for (int y = getMinY(); y <= getMaxY(); y++) {
                double radiusVector  = java.lang.Math.pow((double)(x - getCoordinates().getX()), 2) +
                                       java.lang.Math.pow((double)(y - getCoordinates().getY()), 2);

                if ((radiusVector / java.lang.Math.pow((double)getRadius(), 2)) < 1 + getEpsilon() &&
                    (radiusVector / java.lang.Math.pow((double)getRadius(), 2)) > 1 - getEpsilon()) {
                    getCircle().add(new Coordinates(x, y));
                }
            }
        }
    }

    public HashMap<Coordinates, Integer> getInscribedCoordinates(){
        circleEqual();
        int fromY = 0;
        int toY = 0;
        HashMap<Coordinates, Integer> inscribedCoordinates = new HashMap<Coordinates, Integer>();
        for (int x = getMinX(); x <= getMaxX(); x++) {
            List<Coordinates> listCoordinates = filter(having(on(Coordinates.class).getX(), Matchers.equalTo(x)), getCircle());

            if (listCoordinates.size() == 0) {
                continue;
            }

            if (listCoordinates.size() > 1){
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
                    inscribedCoordinates.put(new Coordinates(x, y), 2);
                }
            } else {
                inscribedCoordinates.put(new Coordinates(x, listCoordinates.get(0).getY()), 2);
            }

        }

        return inscribedCoordinates;
    }

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public int getMinX() {
		return minX;
	}

	public void setMinX(int minX) {
		this.minX = minX;
	}

	public int getMaxX() {
		return maxX;
	}

	public void setMaxX(int maxX) {
		this.maxX = maxX;
	}

	public int getMinY() {
		return minY;
	}

	public void setMinY(int minY) {
		this.minY = minY;
	}

	public int getMaxY() {
		return maxY;
	}

	public void setMaxY(int maxY) {
		this.maxY = maxY;
	}

	public ArrayList<Coordinates> getCircle() {
		return circle;
	}

	public void setCircle(ArrayList<Coordinates> circle) {
		this.circle = circle;
	}

	public Coordinates[][] getCircleEqual() {
		return circleEqual;
	}

	public void setCircleEqual(Coordinates[][] circleEqual) {
		this.circleEqual = circleEqual;
	}

	public double getEpsilon() {
		return epsilon;
	}

	public void setEpsilon(double epsilon) {
		this.epsilon = epsilon;
	}

}
