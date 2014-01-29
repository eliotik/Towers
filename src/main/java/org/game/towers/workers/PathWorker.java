package org.game.towers.workers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
//import org.game.towers.grid.Cell;
//import org.game.towers.level.Construction;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;
import org.hamcrest.Matchers;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

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
        HashMap<Integer, Level.TileMap> temporaryTileMap = (HashMap<Integer, Level.TileMap>) Game.instance.getWorld().getLevel().getTiles().clone();
        int apexX, apexY;
        double y;
        Map.Entry<Integer, Level.TileMap> tileItem;
        if ( (xStart < xFinish && yStart < yFinish) || (xStart > xFinish && yStart > yFinish) ) {
            for (Iterator<Map.Entry<Integer, Level.TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getBottomLeft().getX();
                apexY = tileItem.getValue().getGeo().getBottomLeft().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
//                System.out.println(apexY);
//                System.out.println(y);
                if (apexY > y) {
                    it.remove();
//                    System.out.println(temporaryTileMap.size());
                }
            }

            for (Iterator<Map.Entry<Integer, Level.TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getTopRight().getX();
                apexY = tileItem.getValue().getGeo().getTopRight().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
//                System.out.println(apexY);
//                System.out.println(y);
                if (apexY < y) {
                    it.remove();
                }
            }
        }

        if ( (xStart < xFinish && yStart > yFinish) || (xStart > xFinish && yStart < yFinish) ) {
            for (Iterator<Map.Entry<Integer, Level.TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getBottomRight().getX();
                apexY = tileItem.getValue().getGeo().getBottomRight().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                System.out.println("apexY ="+apexY);
                System.out.println("y ="+y);
                if (apexY < y) {
                    it.remove();
                }
            }

            for (Iterator<Map.Entry<Integer, Level.TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getTopLeft().getX();
                apexY = tileItem.getValue().getGeo().getTopLeft().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                System.out.println(apexY);
                System.out.println(y);
                if (apexY > y) {
                    it.remove();
//                    System.out.println(temporaryTileMap.size());
                }
            }
        }

        if (temporaryTileMap.size() == 0) {
            return null;
        }

        Level.TileMap barrier = getFirstBarrier(temporaryTileMap, xStart, yStart);
        return barrier;
    }

    private double lineEquation(int x, int xFinish, int xStart, int yFinish, int yStart) {
//        System.out.println("x "+ x + " xFinish "+xFinish+" xStart "+xStart+" yFinish "+yFinish+" yStart "+yStart);
        double b = (double)(yFinish - yStart) / (double)(xFinish - xStart);
//        System.out.println(yFinish - yStart);
//        System.out.println(xFinish - xStart);
//        System.out.println(b);
        double c = (double)yFinish - xStart * b;
//        System.out.println(c);
        double y = b * x + c;

        return y;
    }

    private Level.TileMap getFirstBarrier(HashMap<Integer, Level.TileMap> temporaryTileMap, int xStart, int yStart) {
        Level.TileMap firstTile = temporaryTileMap.entrySet().iterator().next().getValue();
        temporaryTileMap.remove(firstTile);
        int minX = firstTile.getGeo().getTopLeft().getX();
        int minY = firstTile.getGeo().getTopLeft().getY();
        double minR = getRadiusVector(minX, minY, xStart, yStart);
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



    private int getLogicZone(int x, int y, Level.TileMap construction){

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

    private List<Integer> chanceDirection(int x, int y, int finishX, int finishY) {
        int dx, dy, chance;
        double chanceX, chanceY;
        List<Integer> list = new ArrayList<Integer>();
        Random rand = new Random();
        chance = rand.nextInt(10);
        chanceX = chance * (finishX - x)/(finishY - y);
        chanceY = chance * (finishY - y)/(finishX - x);
        dx = doShift(x, finishX);
        dy = doShift(y, finishY);
        if (Math.abs(chanceX) < 5) {
            dx = 0;
        }
        if (Math.abs(chanceY) < 5) {
            dy = 0;
        }
        list.add(dx);
        list.add(dy);

        return list;
    }

    public void nextCoordinate(int x, int y, Point point) {
        int finishX = Level.Portals.getExit().getX();
        int finishY = Level.Portals.getExit().getY();
        int dx, dy;
        Level.TileMap barrier = getBarrier(x, finishX, y, finishY);
        Coordinates coordinate = new Coordinates(finishX, finishY);
        if (barrier != null){
            coordinate = getTransitionalFinish(x, y, barrier);
        }
//        System.out.println(barrier);
//        System.out.println(x +", "+ y);
//        System.out.println(coordinate.getX() +", "+ coordinate.getY());
        List<Integer> chanceDirection = chanceDirection(x, y, coordinate.getX(), coordinate.getY());
        dx = chanceDirection.get(0);
        dy = chanceDirection.get(1);

        point.setLocation(dx, dy);
    }



}
