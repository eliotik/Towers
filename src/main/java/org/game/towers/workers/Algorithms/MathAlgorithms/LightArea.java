package org.game.towers.workers.Algorithms.MathAlgorithms;

import org.game.towers.game.Config;
import org.game.towers.workers.geo.Coordinates;

import java.util.ArrayList;

class LightArea
{
    private int radius;
    private Coordinates coordinatesCenter;
    private int minX, maxX, minY, maxY;
    private ArrayList<Coordinates> upperCircleLightEdge = new ArrayList<>();
    private ArrayList<Coordinates> lowerCircleLightEdge = new ArrayList<>();
    private ArrayList<Coordinates> lightEdge = new ArrayList<>();
    private double epsilon = 1;

    public LightArea(Coordinates coordinatesCenter, int radius) {
        setCoordinatesCenter(coordinatesCenter);
        setRadius(radius);
        setMinX(getCoordinatesCenter().getX() - radius);
        setMaxX(getCoordinatesCenter().getX() + radius);
        setMinY(getCoordinatesCenter().getY() - radius);
        setMaxY(getCoordinatesCenter().getY() + radius);
    }

    private void generateCircleLightEdge() {
        for(double x = getMinX(); x <= getMaxX(); x++) {
            int y = (int)(java.lang.Math.sqrt( java.lang.Math.pow(radius, 2) - java.lang.Math.pow((x - getCoordinatesCenter().getX()), 2)) + getCoordinatesCenter().getY());

            if (lowerCircleLightEdge.size() == 0) {
                lowerCircleLightEdge.add(new Coordinates(x, y));
                continue;
            }

            if (getRadiusVector(x, y, lowerCircleLightEdge.get(lowerCircleLightEdge.size() - 1)) > epsilon){
                lowerCircleLightEdge.add(new Coordinates(x, y));
            }
        }
        for(double x = getMaxX(); x < getMinX(); x++) {
            int y = (int)(getCoordinatesCenter().getY() - java.lang.Math.sqrt( java.lang.Math.pow(radius, 2) - java.lang.Math.pow((x - getCoordinatesCenter().getX()), 2)));

            if (lowerCircleLightEdge.size() == 0) {
                lowerCircleLightEdge.add(new Coordinates(x, y));
                continue;
            }

            if (getRadiusVector(x, y, upperCircleLightEdge.get(upperCircleLightEdge.size() - 1)) > epsilon){
                upperCircleLightEdge.add(new Coordinates(x, y));
            }
        }
    }

    private void generateLightEdge(){
        generateCircleLightEdge();

        for(Coordinates item : upperCircleLightEdge) {

        }
    }

    private double lineEquation(int x, Coordinates item){
        double aFactor = (x - item.getX()) / (coordinatesCenter.getX() - item.getX());
        return coordinatesCenter.getY() + ( aFactor * (coordinatesCenter.getY() - item.getY()) );
    }

    private double getRadiusVector(double x, double y, Coordinates coordinate) {
        return java.lang.Math.sqrt( java.lang.Math.pow(x - coordinate.getX(), 2) + java.lang.Math.pow(y - coordinate.getY(), 2) );
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

    public Coordinates getCoordinatesCenter() {
        return coordinatesCenter;
    }

    public void setCoordinatesCenter(Coordinates coordinatesCenter) {
        this.coordinatesCenter = coordinatesCenter;
    }
}
