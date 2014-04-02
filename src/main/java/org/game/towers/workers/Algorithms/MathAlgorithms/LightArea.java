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
    private HashMap<Coordinates, Integer> inscribedCoordinates = new HashMap<Coordinates, Integer>();
    private HashMap<String, Integer> checkOnUnique = new HashMap<>();
    private ArrayList<Coordinates> lightExtremePoints = new ArrayList<>();
    private int uniqueSize = 0;
//    private double epsilon = 0.1;
    private double deltaPenetration = 0.5;

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

    private void generateLightMap(double coordinatesCenterX, double coordinatesCenterY, int status) {
        double deltaDegree = LinearAlgorithms.angleByCoordinate(0, radius, 0, 0.5);
        double degreeMultiplier = (2 * Math.PI)/ deltaDegree;
        double deltaX, deltaY;
        int xa, ya;
        double radius = (double)getRadius();
        double epsilon = 1;
        double transparency, tileWidth, tileDiagonal, maxPenetration;
        double currentX;
        double currentY;
        double centerX = coordinatesCenterX;
        double centerY = coordinatesCenterY;
        for (double degree = 0; degree < degreeMultiplier*deltaDegree/*2 * Math.PI*/; degree+=deltaDegree) {
            double penetrationDepth = 0;
            for (double r  = 0; r < radius; r += epsilon) {
                deltaX = r * Math.cos(degree);
                deltaY = r * Math.sin(degree);
                currentX = centerX + deltaX;
                currentY = centerY + deltaY;

                xa = (int)currentX >> Config.COORDINATES_SHIFTING;
                ya = (int)currentY >> Config.COORDINATES_SHIFTING;

                if (Game.getInstance().getWorld().getLevel().getTile(xa, ya).isSolid()){
                    penetrationDepth+=epsilon;
                    transparency = 1 - Game.getInstance().getWorld().getLevel().getTile(xa, ya).getOpacity() / 100;
                    tileWidth = (double)Game.getInstance().getWorld().getLevel().getTile(xa, ya).getSprite().getWidth();
                    tileDiagonal = tileWidth * Math.sqrt(2);
                    maxPenetration = tileDiagonal * transparency;
                    if (penetrationDepth > maxPenetration) {
                        putUniqueCoordinate(new Coordinates((int)currentX, (int)currentY), 1);
                        break;
                    }
                }

                putUniqueCoordinate(new Coordinates((int)currentX, (int)currentY), status);

            }
        }
    }

    private void generateLightExtremePoints() {
        lightExtremePoints.add(new Coordinates(getCoordinatesCenter().getX() - Config.BOX_SIZE/2, getCoordinatesCenter().getY()));
        lightExtremePoints.add(new Coordinates(getCoordinatesCenter().getX(), getCoordinatesCenter().getY() - Config.BOX_SIZE/2));
        lightExtremePoints.add(new Coordinates(getCoordinatesCenter().getX() + Config.BOX_SIZE/2, getCoordinatesCenter().getY()));
        lightExtremePoints.add(new Coordinates(getCoordinatesCenter().getX(), getCoordinatesCenter().getY() + Config.BOX_SIZE/2));
    }

    private void generateLightDiffraction() {
        generateLightExtremePoints();

        for (Coordinates point : lightExtremePoints) {
            generateLightMap((double)point.getX(), (double)point.getY(), 1);
        }
    }

    public HashMap<Coordinates, Integer> getInscribedCoordinates(){
        generateLightMap((double)getCoordinatesCenter().getX(), (double)getCoordinatesCenter().getY(), 2);

        if (Config.USE_LIGHT_DIFFRACTION) {
            generateLightDiffraction();
        }

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
