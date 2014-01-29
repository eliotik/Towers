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
        HashMap<Integer, Level.TileMap> temporaryTileMap = (HashMap<Integer, Level.TileMap>) Game.instance.getWorld().getLevel().getBlocks().clone();
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
                if (apexY > y) {
                    it.remove();
                }
            }

            for (Iterator<Map.Entry<Integer, Level.TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getTopRight().getX();
                apexY = tileItem.getValue().getGeo().getTopRight().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y) {
                    it.remove();
                }
            }
        }

        if ( (yStart > yFinish && xStart < xFinish) || (xStart > xFinish && yStart < yFinish) ) {
            for (Iterator<Map.Entry<Integer, Level.TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getBottomRight().getX();
                apexY = tileItem.getValue().getGeo().getBottomRight().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
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
                if (apexY > y) {
                    it.remove();
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
        double A = (double)(yStart - yFinish) / (double)(xFinish - xStart);
        double B = (double)yFinish - A * xStart;
        double y = A * (double)x + B;
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
        chanceX = chanceY = 0;
        if (finishY != y && finishX != x){
            chanceX = chance * (finishX - x)/(finishY - y);
            chanceY = chance * (finishY - y)/(finishX - x);
        }
        dx = doShift(x, finishX);
        dy = doShift(y, finishY);
        if (Math.abs(chanceX) > 10) {
            dx = 0;
        }
        if (Math.abs(chanceY) > 10) {
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

        List<Integer> chanceDirection = chanceDirection(x, y, coordinate.getX(), coordinate.getY());
        dx = chanceDirection.get(0);
        dy = chanceDirection.get(1);

//        dx = doShift(x, coordinate.getX());
//        dy = doShift(y, coordinate.getY());


        point.setLocation(dx, dy);
    }



}
