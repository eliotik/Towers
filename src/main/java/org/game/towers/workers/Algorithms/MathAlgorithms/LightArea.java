package org.game.towers.workers.Algorithms.MathAlgorithms;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.workers.geo.Coordinates;

import java.util.HashMap;

class LightArea
{
    private int radius;
    private Coordinates coordinatesCenter;
    private int minX, maxX, minY, maxY;
    private HashMap<Coordinates, Integer> inscribedCoordinates = new HashMap<Coordinates, Integer>();
    private HashMap<String, Integer> checkOnUnique = new HashMap<>();
    private int uniqueSize = 0;
    private double epsilon = 0.001;

    public LightArea(Coordinates coordinatesCenter, int radius) {
        setCoordinatesCenter(coordinatesCenter);
        setRadius(radius);

        setMinX(getCoordinatesCenter().getX() - getRadius());
        if (getCoordinatesCenter().getX() - getRadius() < 0) {
            setMinX(0);
        }

        setMaxX(getCoordinatesCenter().getX() + getRadius());
        if (getCoordinatesCenter().getX() + getRadius() > Config.REAL_SCREEN_WIDTH) {
            setMaxX(Config.REAL_SCREEN_WIDTH);
        }

        setMinY(getCoordinatesCenter().getY() - getRadius());
        if (getCoordinatesCenter().getY() - getRadius() < 0) {
            setMinY(0);
        }
        setMaxY(getCoordinatesCenter().getY() + getRadius());
        if (getCoordinatesCenter().getY() + getRadius() > Config.REAL_SCREEN_HEIGHT) {
            setMaxY(Config.REAL_SCREEN_HEIGHT);
        }
    }

    private void putUniqueCoordinate(Coordinates coordinates, int status) {
        boolean unique = false;
        if (checkOnUnique.size() == 0) {
            checkOnUnique.put(coordinates.getX() + "-" + coordinates.getY(), 0);
            uniqueSize = checkOnUnique.size();
            unique = true;
        }

        checkOnUnique.put(coordinates.getX() + "-" + coordinates.getY(), 0);
        if (uniqueSize != checkOnUnique.size())   {
            uniqueSize = checkOnUnique.size();
            unique = true;
        }

        if (unique) {
            inscribedCoordinates.put(coordinates, status);
        }
    }

    private void generateLightMap() {
        double deltaDegree = Math.PI /( 8 * 360 );
        double deltaX, deltaY;
        int xa, ya;
        double radius = (double)getRadius();

        for (double degree = 0; degree < 2 * Math.PI; degree+=deltaDegree) {
            double currentX = (double)getCoordinatesCenter().getX();
            double currentY = (double)getCoordinatesCenter().getY();
            for (double r  = 0; r < radius; r+=epsilon) {
                deltaX = r * Math.cos(degree);
                deltaY = r * Math.sin(degree);
                currentX += deltaX;
                currentY += deltaY;
                xa = (int)currentX >> Config.COORDINATES_SHIFTING;
                ya = (int)currentY >> Config.COORDINATES_SHIFTING;

                if (Game.getInstance().getWorld().getLevel().getTile(xa, ya).isSolid()){
                    putUniqueCoordinate(new Coordinates((int)currentX, (int)currentY), 2);
                    break;
                }
                putUniqueCoordinate(new Coordinates((int)currentX, (int)currentY), 2);

            }
        }
    }

    public HashMap<Coordinates, Integer> getInscribedCoordinates(){
        generateLightMap();

        return inscribedCoordinates;
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
