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
    private int radiusWithEdge;
    private int minX, maxX, minY, maxY;
    private ArrayList<Coordinates> circle = new ArrayList<Coordinates>();
    private ArrayList<Coordinates> externalCircle = new ArrayList<Coordinates>();
    private ArrayList<Coordinates> internalCircle = new ArrayList<Coordinates>();
    HashMap<Coordinates, Integer> inscribedCoordinates = new HashMap<Coordinates, Integer>();
    private Coordinates[][] circleEqual;
    private double epsilon = 0.5;


    public Circle(Coordinates coordinatesCenter, int radius) {
        setCoordinates(coordinatesCenter);
        setRadius(radius);
        setRadiusWithEdge(radius + Config.EDGE_SIZE);
        setMinX(getCoordinates().getX() - radius);
        setMaxX(getCoordinates().getX() + radius);
        setMinY(getCoordinates().getY() - radius);
        setMaxY(getCoordinates().getY() + radius);
		if (getMinX() < 0) setMinX(0);
		if (getMinY() < 0) setMinY(0);
		if (getMaxX() > Config.SCREEN_WIDTH) setMaxX(Config.SCREEN_WIDTH);
		if (getMaxY() > Config.REAL_SCREEN_HEIGHT) setMaxY(Config.REAL_SCREEN_HEIGHT);
    }

    private void generateExternalCircle(){
        circleEqual(externalCircle, getRadiusWithEdge(), Config.EDGE_SIZE);
    }

    private void generateInternalCircle(){
        circleEqual(internalCircle, getRadius(), 0);
    }

    private void circleEqual(ArrayList<Coordinates> circle, int radius, int delta){
        for(double x = getMinX() - delta; x <= getMaxX() + delta; x+=epsilon) {
            int y = (int)(java.lang.Math.sqrt( java.lang.Math.pow(radius, 2) - java.lang.Math.pow((x - getCoordinates().getX()), 2)) + getCoordinates().getY());

            if (y < 0) {
                y = 0;
            }
            circle.add(new Coordinates(x, y));
        }
        for(double x = getMinX() - delta; x <= getMaxX() + delta; x+=epsilon) {
            int y = (int)(getCoordinates().getY() - java.lang.Math.sqrt( java.lang.Math.pow(radius, 2) - java.lang.Math.pow((x - getCoordinates().getX()), 2)));

            if (y < 0) {
                y = 0;
            }
            circle.add(new Coordinates(x, y));
        }
    }

    private void generateExternalCoordinates() {
        getCircleCoordinates(externalCircle, 1);
    }

    private void generateInternalCoordinates() {
        getCircleCoordinates(internalCircle, 2);
    }

    private HashMap<Coordinates, Integer> getCircleCoordinates(ArrayList<Coordinates> circle, int status){
        int fromY = 0;
        int toY = 0;
        int offsetedMinX = getMinX();
        int offsetedMaxX = getMaxX();
        switch(status) {
            case 1:
                generateExternalCircle();
                offsetedMinX -= Config.EDGE_SIZE;
                offsetedMaxX += Config.EDGE_SIZE;
                if (offsetedMinX < 0) { offsetedMinX = 0; }
                if (offsetedMaxX > Config.SCREEN_WIDTH) { offsetedMaxX = Config.SCREEN_WIDTH; }
                break;
            case 2:
                generateInternalCircle();
                break;
            default:
                return null;
        }

        for (int x = offsetedMinX; x <= offsetedMaxX; x++) {
            List<Coordinates> listCoordinates = filter(having(on(Coordinates.class).getX(), Matchers.equalTo(x)), circle);
            if (listCoordinates.size() == 0) {
                continue;
            }

            if (x == offsetedMinX || x == offsetedMaxX) {
                inscribedCoordinates.put(new Coordinates(x, listCoordinates.get(0).getY()), status);
                continue;
            }

            if (listCoordinates.size() > 1) {

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
                    inscribedCoordinates.put(new Coordinates(x, y), status);
                }
            }
        }

        return inscribedCoordinates;
    }

    public HashMap<Coordinates, Integer> getInscribedCoordinates(){
        generateExternalCoordinates();
        generateInternalCoordinates();

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

    public int getRadiusWithEdge() {
        return radiusWithEdge;
    }

    public void setRadiusWithEdge(int radiusWithEdge) {
        this.radiusWithEdge = radiusWithEdge;
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
