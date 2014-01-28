package org.game.towers.workers;

import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.level.Level;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;

import static ch.lambdaj.Lambda.*;

public class PathWorker {

    private boolean isAvailable = true;
//    private Level level = Game.instance.getWorld().getLevel();

    public boolean pathCheck() {
        return isAvailable;
    }

    private int roundDownScale(double aValue)
    {
        BigDecimal decimal = new BigDecimal(aValue);
        decimal = decimal.setScale(2,BigDecimal.ROUND_DOWN);
        int result = decimal.intValue();
        return result;
    }

    private Level.TileMap getBarrier(int xStart, int xFinish, int yStart, int yFinish){
        HashMap<Integer, Level.TileMap> temporaryTileMap = Game.instance.getWorld().getLevel().getTiles();
//        if ( (yFinish > yStart  && xFinish > xStart) || (yFinish < yStart  && xFinish < xStart) ) {
//        if ( (xStart < xFinish && yStart < yFinish) || (xStart > xFinish && yStart > yFinish) ) {
        if ( (xStart < xFinish && yStart < yFinish) || (xStart > xFinish && yStart > yFinish) ) {
            for(Map.Entry<Integer, Level.TileMap> tileItem : temporaryTileMap.entrySet()) {
                int apexX = tileItem.getValue().getGeo().getBottomLeft().getX();
                int apexY = tileItem.getValue().getGeo().getBottomLeft().getY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY > y) {
                    temporaryTileMap.remove(tileItem.getKey());
                }
            }
            for(Map.Entry<Integer, Level.TileMap> tileItem : temporaryTileMap.entrySet()) {
                int apexX = tileItem.getValue().getGeo().getTopRight().getX();
                int apexY = tileItem.getValue().getGeo().getTopRight().getY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y) {
                    temporaryTileMap.remove(tileItem.getKey());
                }
            }
        }

//        if ( (yFinish > yStart  && xFinish < xStart) || (yFinish < yStart  && xFinish > xStart) ) {
//        if ( (xStart < xFinish && yStart < yFinish) || (xStart > xFinish && yStart > yFinish) ) {
        if ( (xStart < xFinish && yStart > yFinish) || (xStart > xFinish && yStart < yFinish) ) {
            for(Map.Entry<Integer, Level.TileMap> tileItem : temporaryTileMap.entrySet()) {
                int apexX = tileItem.getValue().getGeo().getBottomRight().getX();
                int apexY = tileItem.getValue().getGeo().getBottomRight().getY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y) {
//                    System.out.println("removed");
//                    System.out.println("key "+tileItem.getKey() + " val" +tileItem.getValue());
                    temporaryTileMap.remove(tileItem);
                }
            }

            for(Map.Entry<Integer, Level.TileMap> tileItem : temporaryTileMap.entrySet()) {
                int apexX = tileItem.getValue().getGeo().getTopLeft().getX();
                int apexY = tileItem.getValue().getGeo().getTopLeft().getY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY > y) {
                    temporaryTileMap.remove(tileItem.getKey());
                }
            }
        }

        if (temporaryTileMap.isEmpty()) {
            return null;
        }
//        for (Map.Entry<Integer, Level.TileMap> item : temporaryTileMap.entrySet()){
//            System.out.println("x =" + item.getValue().getGeo().getTopLeft().getX() + " y =" + item.getValue().getGeo().getTopLeft().getY());
//        }
//        System.out.println("-----------------------");
        Level.TileMap barrier = getFirstBarrier(temporaryTileMap, xStart, yStart);
        return barrier;
    }

    private double lineEquation(int x, int xFinish, int xStart, int yFinish, int yStart) {
        double b = (yFinish - yStart) / (xFinish - xStart);
        double c = yStart - xStart * b;
        double y = b * x + c;

        return y;
    }

    private Level.TileMap getFirstBarrier(HashMap<Integer, Level.TileMap> temporaryTileMap, int xStart, int yStart) {
        Level.TileMap firstTile = temporaryTileMap.entrySet().iterator().next().getValue();
        temporaryTileMap.remove(firstTile);
        int minX = firstTile.getGeo().getTopLeft().getX();
        int minY = firstTile.getGeo().getTopLeft().getY();
        double minR = getRadiusVector(minX, minY, xStart, yStart);
//        Construction nearestConstruction = temporaryTileMap.get(0);
        Level.TileMap nearestConstruction = temporaryTileMap.entrySet().iterator().next().getValue();
        for (Map.Entry<Integer, Level.TileMap> tileItem : temporaryTileMap.entrySet()){
            double r = getRadiusVector(tileItem.getValue().getGeo().getTopLeft().getX(), tileItem.getValue().getGeo().getTopLeft().getY(), xStart, yStart);
            if (r < minR) {
                nearestConstruction = tileItem.getValue();
            }
        }
        return nearestConstruction;
    }

    private double getRadiusVector(int x, int y, int xStart, int yStart) {
        double r = Math.sqrt(Math.pow(x - xStart, 2) + Math.pow(y - yStart, 2));
        return r;
    }

//    public Dimension getNextCoordinates(int x, int y){
//        int finishX = Level.Portals.getExit().getX();
//        int finishY = Level.Portals.getExit().getY();
//        Level.TileMap barrier = getBarrier(x, finishX, y, finishY);
//        double deltaX = (finishX - x);
//        double deltaY = (finishY - y);
//        Dimension dimension = new Dimension();
//
//        return dimension;
//    }

    private int getLogicZone(int x, int y, Level.TileMap construction){
//        System.out.println("x"+x);
//        System.out.println("y"+y);
//        System.out.println("construction.getGeo().getTopLeft().getX"+construction.getGeo().getTopLeft().getX());
//        System.out.println("construction.getGeo().getTopLeft().getY"+construction.getGeo().getTopLeft().getY());
        if (x <= construction.getGeo().getTopLeft().getX() && y <= construction.getGeo().getTopLeft().getY()) {
            return 1; // north West
        }

        if (x <= construction.getGeo().getTopLeft().getX() && x >= construction.getGeo().getTopRight().getX() && y <= construction.getGeo().getTopRight().getY()) {
            return 2; // north
        }

        if (x >= construction.getGeo().getTopRight().getX() && y >= construction.getGeo().getTopRight().getY()) {
            return 3; // north-east
        }

        if (x >= construction.getGeo().getTopRight().getX() && y <= construction.getGeo().getTopRight().getY() && y >= construction.getGeo().getBottomRight().getY()) {
            return 4; // east
        }

        if (x >= construction.getGeo().getBottomRight().getX() && y >= construction.getGeo().getBottomRight().getY()) {
            return 5; // south east
        }

        if (x >= construction.getGeo().getBottomLeft().getX() && x <= construction.getGeo().getBottomRight().getX() && y >= construction.getGeo().getBottomRight().getY()) {
            return 6; // south
        }

        if (x <= construction.getGeo().getBottomLeft().getX() && y >= construction.getGeo().getBottomLeft().getY()) {
            return 7; // south west
        }

        if (x <= construction.getGeo().getTopLeft().getX() && y >= construction.getGeo().getTopLeft().getY() && y <= construction.getGeo().getBottomLeft().getY()) {
            return 8; // west
        }
        return 0;
    }

    private Coordinates getTransitionalFinish(int x, int y, Level.TileMap barrier) {
        Coordinates coordinate = null;
        Coordinates firstPotentialPoint = null;
        Coordinates secondPotentialPoint = null;
        int logicZone = getLogicZone(x, y, barrier);

        switch (logicZone) {
            case 1:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX(), barrier.getGeo().getTopRight().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX(), barrier.getGeo().getBottomLeft().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 2:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX(), barrier.getGeo().getTopRight().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX(), barrier.getGeo().getTopLeft().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 3:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX(), barrier.getGeo().getTopLeft().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX(), barrier.getGeo().getBottomRight().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 4:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX(), barrier.getGeo().getTopRight().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX(), barrier.getGeo().getBottomRight().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 5:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX(), barrier.getGeo().getTopRight().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX(), barrier.getGeo().getBottomLeft().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 6:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX(), barrier.getGeo().getBottomRight().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX(), barrier.getGeo().getBottomLeft().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 7:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX(), barrier.getGeo().getTopLeft().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX(), barrier.getGeo().getBottomRight().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 8:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX(), barrier.getGeo().getTopLeft().getY());
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX(), barrier.getGeo().getBottomLeft().getY());
                coordinate =  getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
        }
        return coordinate;
    }

    private Coordinates getProbabilisticWay(int x, int y, Coordinates firstPotentialPoint, Coordinates secondPotentialPoint){
        double r1 = getRadiusVector(x, firstPotentialPoint.getX(), y, firstPotentialPoint.getY()); // radius vector
        double r2 = getRadiusVector(x, secondPotentialPoint.getX(), y, secondPotentialPoint.getY()); // radius vector
        Random rand = new Random();
        int n = (int)(rand.nextInt(10)*(r1/r2));

        if (n < 5) {
            return firstPotentialPoint;
        }
        return secondPotentialPoint;
    }

    private int doShift(int dimension1, int dimension2) {
        int deltaDim = dimension2 - dimension1;

        if (deltaDim > 0){
            return 1;
        }
        if (deltaDim < 0){
            return -1;
        }

        return 0;
    }

    public void nextCoordinate(int x, int y, Point point) {
        int finishX = Level.Portals.getExit().getX();
        int finishY = Level.Portals.getExit().getY();
//        System.out.println("x="+x);
//        System.out.println("y="+y);
//        System.out.println("finishX="+finishX);
//        System.out.println("finishY="+finishY);
        Level.TileMap barrier = getBarrier(x, finishX, y, finishY);
//        System.out.println("barrier.getGeo().getTopLeft().getX()="+barrier.getGeo().getTopLeft().getX());
//        System.out.println("barrier.getGeo().getTopLeft().getX()="+barrier.getGeo().getTopLeft().getX());
        Coordinates coordinate = getTransitionalFinish(x, y, barrier);
//        System.out.println("coordinate.getX()="+coordinate.getX());
//        System.out.println("coordinate.getY()="+coordinate.getY());
        int dx = doShift(x, coordinate.getX());
        int dy = doShift(y, coordinate.getY());

        dx = doShift(x, finishX);
        dy = doShift(y, finishY);

        point.setLocation(dx, dy);
    }



}
