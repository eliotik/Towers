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
        boolean stop = false;
        y = (int)lineEquation(x, point);

        if (x < 0) { x = 0; }
        if (y < 0) { y = 0; }
        if (x > Config.SCREEN_WIDTH) { x = Config.SCREEN_WIDTH; }
        if (y > Config.REAL_SCREEN_HEIGHT) { y = Config.REAL_SCREEN_HEIGHT; }

        xa = x >> Config.COORDINATES_SHIFTING;
        ya = y >> Config.COORDINATES_SHIFTING;

        if (Game.getInstance().getWorld().getLevel().getTile(xa, ya).isSolid()){
            lightEdge.add(point);
            stop = true;
            return stop;
        }

        inscribedCoordinates.put(point, 2);

        return stop;
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
