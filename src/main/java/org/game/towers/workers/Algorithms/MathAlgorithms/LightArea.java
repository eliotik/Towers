package org.game.towers.workers.Algorithms.MathAlgorithms;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.workers.geo.Coordinates;

import java.util.ArrayList;
import java.util.HashMap;

class LightArea
{
    private int radius;
    private Coordinates coordinatesCenter;
    private int minX, maxX, minY, maxY;
    private ArrayList<Coordinates> upperCircleLightEdge = new ArrayList<>();
    private ArrayList<Coordinates> lowerCircleLightEdge = new ArrayList<>();
    private HashMap<Coordinates, Integer> inscribedCoordinates = new HashMap<Coordinates, Integer>();
    private ArrayList<Coordinates> lightEdge = new ArrayList<>();
    private double epsilon = 0.5;
    private double delta = 0.1;

    public LightArea(Coordinates coordinatesCenter, int radius) {
        setCoordinatesCenter(coordinatesCenter);
        setRadius(radius);
        setMinX(getCoordinatesCenter().getX() - radius);
        setMaxX(getCoordinatesCenter().getX() + radius);
        setMinY(getCoordinatesCenter().getY() - radius);
        setMaxY(getCoordinatesCenter().getY() + radius);
    }

    private void generateCircleLightEdge() {
        for(double x = getMinX(); x <= getMaxX(); x+=delta) {
            int y = (int)(java.lang.Math.sqrt( java.lang.Math.pow(radius, 2) - java.lang.Math.pow((x - getCoordinatesCenter().getX()), 2)) + getCoordinatesCenter().getY());

            if (lowerCircleLightEdge.size() == 0) {
                lowerCircleLightEdge.add(new Coordinates(x, y));
                continue;
            }

            if (getRadiusVector(x, y, lowerCircleLightEdge.get(lowerCircleLightEdge.size() - 1)) > epsilon){
                lowerCircleLightEdge.add(new Coordinates(x, y));
            }
        }
        System.out.println("lowerCircleLightEdge size " + lowerCircleLightEdge.size());

        for(double x = getMaxX(); x > getMinX(); x-=delta) {
            int y = (int)(java.lang.Math.sqrt( java.lang.Math.pow(radius, 2) - java.lang.Math.pow((x - getCoordinatesCenter().getX()), 2)) - getCoordinatesCenter().getY());

            if (upperCircleLightEdge.size() == 0) {
                upperCircleLightEdge.add(new Coordinates(x, y));
                continue;
            }

            if (getRadiusVector(x, y, upperCircleLightEdge.get(upperCircleLightEdge.size() - 1)) > epsilon){
                upperCircleLightEdge.add(new Coordinates(x, y));
            }
        }
        System.out.println("upperCircleLightEdge size " + upperCircleLightEdge.size());
    }

    private void generateLightEdge(){
        generateCircleLightEdge();

        for(Coordinates point : upperCircleLightEdge) {
            if (point.getX() < coordinatesCenter.getX()){
                for (int x = coordinatesCenter.getX(); x > point.getX(); x--) {
                    if (addPoints(x, point)) {
                        break;
                    }
                }
            } else {
                for (int x = coordinatesCenter.getX(); x < point.getX(); x++) {
                    if (addPoints(x, point)) {
                        break;
                    }
                }
            }

        }

        for(Coordinates point : lowerCircleLightEdge) {
            if (point.getX() < coordinatesCenter.getX()){
                for (int x = coordinatesCenter.getX(); x > point.getX(); x--) {
                    if (addPoints(x, point)) {
                        break;
                    }
                }
            } else {
                for (int x = coordinatesCenter.getX(); x < point.getX(); x++) {
                    if (addPoints(x, point)) {
                        break;
                    }
                }
            }
        }
    }

    private boolean addPoints(int x, Coordinates point) {
        int y, xa, ya;
        y = (int)lineEquation(x, point);

        if (x < 0 || y < 0 || x > Config.REAL_SCREEN_WIDTH || y > Config.REAL_SCREEN_HEIGHT) {
            return false;
        }
//        if (y < 0) {
//            lightEdge.add(point);
//            stop = true;
//            return stop;
//        }
//        if (x > Config.SCREEN_WIDTH) {
//            lightEdge.add(point);
//            stop = true;
//            return stop;
//        }
//        if (y > Config.REAL_SCREEN_HEIGHT) {
//            lightEdge.add(point);
//            stop = true;
//            return stop;
//        }

        xa = x >> Config.COORDINATES_SHIFTING;
        ya = y >> Config.COORDINATES_SHIFTING;

        if (Game.getInstance().getWorld().getLevel().getTile(xa, ya).isSolid()){
            lightEdge.add(point);
            return true;
        }

        inscribedCoordinates.put(point, 2);

        return false;
    }

    public HashMap<Coordinates, Integer> getInscribedCoordinates(){
        generateLightEdge();

        return inscribedCoordinates;
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
