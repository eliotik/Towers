package org.game.towers.workers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.geo.Geo;
import org.game.towers.level.Portals;
import org.game.towers.level.tiles.TileMap;
import org.game.towers.units.Unit;
import org.game.towers.workers.Algorithms.JumpPointSearch.JPS;
import org.hamcrest.Matchers;

import java.awt.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.List;

public class PathWorker {

    private boolean isAvailable = true;
    private List<Coordinates> wayList = new ArrayList<Coordinates>();

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

    private TileMap getBarrier(int xStart, int xFinish, int yStart, int yFinish){
        HashMap<String, TileMap> temporaryTileMap = (HashMap<String, TileMap>) Game.instance.getWorld().getLevel().getBlocks().clone();
        int apexX, apexY;

        double y;
        Map.Entry<String, TileMap> tileItem;

        if (yStart >= (yFinish - Config.BOX_SIZE) && yStart <= (yFinish + Config.BOX_SIZE)) {
            for (Iterator<Map.Entry<String, TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getTopRight().getX();
                apexY = tileItem.getValue().getGeo().getTopRight().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY > y || apexX < xStart) {
                    it.remove();
                }
            }

            for (Iterator<Map.Entry<String, TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getBottomRight().getX();
                apexY = tileItem.getValue().getGeo().getBottomRight().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y || apexX < xStart) {
                    it.remove();
                }
            }
        }


        if ( (xStart < xFinish && yStart < yFinish) || (xStart > xFinish && yStart > yFinish) ) {
            for (Iterator<Map.Entry<String, TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getBottomLeft().getX();
                apexY = tileItem.getValue().getGeo().getBottomLeft().getY();
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY > y) {
                    it.remove();
                }
            }

            for (Iterator<Map.Entry<String, TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getTopRight().getX();
                apexY = tileItem.getValue().getGeo().getTopRight().getY();
                y = lineEquation(apexX, xFinish, xStart + Config.BOX_SIZE, yFinish + Config.BOX_SIZE, yStart + Config.BOX_SIZE);
                if (apexY < y) {
                    it.remove();
                }
            }
        }

        if ( (yStart > yFinish && xStart < xFinish) || (xStart > xFinish && yStart < yFinish) ) {

            for (Iterator<Map.Entry<String, TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();

                apexX = tileItem.getValue().getGeo().getBottomRight().getX();
                apexY = tileItem.getValue().getGeo().getBottomRight().getY();
//                System.out.println("tileItem =" + tileItem);
//                System.out.println("xStart =" + xStart + "yStart =" + yStart);
//                System.out.println("xFinish =" + xFinish + "yFinish =" + yFinish);
                y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y) {
                    it.remove();
                }
            }
            System.out.println(temporaryTileMap.size());


            for (Iterator<Map.Entry<String, TileMap>> it = temporaryTileMap.entrySet().iterator(); it.hasNext();)
            {
                tileItem = it.next();
                apexX = tileItem.getValue().getGeo().getTopLeft().getX();
                apexY = tileItem.getValue().getGeo().getTopLeft().getY();
                y = lineEquation(apexX, xFinish - Config.BOX_SIZE, xStart + Config.BOX_SIZE, yFinish + Config.BOX_SIZE, yStart + Config.BOX_SIZE);
                if (apexY > y || apexX < xStart) {
                    it.remove();
                }

            }
        }
        if (temporaryTileMap.size() == 0) {
            return null;
        }

        TileMap barrier = getFirstBarrier(temporaryTileMap, xStart, yStart);

        return barrier;
    }

    private double lineEquation(int x, int xFinish, int xStart, int yFinish, int yStart) {
        // other equation
        double A = (double)(yStart - yFinish) / (double)(xFinish - xStart);
        double B = (double)yStart - A * xStart;
        double y = B - A * x;
        return y;
    }

    private TileMap getFirstBarrier(HashMap<String, TileMap> temporaryTileMap, int xStart, int yStart) {
        TileMap firstTile = temporaryTileMap.entrySet().iterator().next().getValue();
        temporaryTileMap.remove(firstTile);
        int minX = firstTile.getGeo().getTopLeft().getX();
        int minY = firstTile.getGeo().getTopLeft().getY();
        double minR = getRadiusVector(minX, minY, xStart, yStart);
        TileMap nearestConstruction = temporaryTileMap.entrySet().iterator().next().getValue();
        for (Map.Entry<String, TileMap> tileItem : temporaryTileMap.entrySet()){

            boolean isSolid = Game.instance.getWorld().getLevel().getTile(tileItem.getValue().getGeo().getTopLeft().getX(),
                                                        tileItem.getValue().getGeo().getTopLeft().getY()).isSolid();
            if (isSolid) {
                double r = getRadiusVector(tileItem.getValue().getGeo().getTopLeft().getX(), tileItem.getValue().getGeo().getTopLeft().getY(), xStart, yStart);
                if (r < minR) {
                    nearestConstruction = tileItem.getValue();
                }
            }
        }
        return nearestConstruction;
    }

    private double getRadiusVector(int x, int y, int xStart, int yStart) {
        double r = Math.sqrt(Math.pow(x - xStart, 2) + Math.pow(y - yStart, 2));
        return r;
    }

    private int getLogicZone(int x, int y, TileMap construction){
        if (x <= construction.getGeo().getTopLeft().getX() && y <= construction.getGeo().getTopLeft().getY() - Config.BOX_SIZE) {
            return 1; // north West
        }

        if (x <= construction.getGeo().getTopLeft().getX() && x >= construction.getGeo().getTopRight().getX() + Config.BOX_SIZE && y <= construction.getGeo().getTopLeft().getY() - Config.BOX_SIZE && y <= construction.getGeo().getTopRight().getY() - Config.BOX_SIZE) {
            return 2; // north
        }

        if (x >= construction.getGeo().getTopRight().getX() && y <= construction.getGeo().getTopRight().getY()) {
            return 3; // north-east
        }

        if (x >= construction.getGeo().getTopRight().getX() && x >= construction.getGeo().getBottomRight().getX() && y >= construction.getGeo().getTopRight().getY() && y <= construction.getGeo().getBottomRight().getY()) {
            return 4; // east
        }

        if (x >= construction.getGeo().getBottomRight().getX() && y >= construction.getGeo().getBottomRight().getY()) {
            return 5; // south east
        }

        if (x >= construction.getGeo().getBottomLeft().getX() && x <= construction.getGeo().getBottomRight().getX() && y >= construction.getGeo().getBottomRight().getY() && y >= construction.getGeo().getBottomLeft().getY()) {
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

    private Coordinates getTransitionalFinish(int x, int y, TileMap barrier) {
        Coordinates coordinate = null;
        Coordinates firstPotentialPoint = null;
        Coordinates secondPotentialPoint = null;

        if (wayList.size() > 0){
            if (Math.abs(x - wayList.get(wayList.size()-1).getX()) > Config.BOX_SIZE / 2 &&
                    Math.abs(y - wayList.get(wayList.size()-1).getY()) > Config.BOX_SIZE / 2) {
                return wayList.get(wayList.size()-1);
            }
        }


//        System.out.println(barrier.getGeo().getTopLeft().getX()+" "+barrier.getGeo().getTopLeft().getX() );
        int logicZone = getLogicZone(x, y, barrier);
//        System.out.println("logicZone="+logicZone);
        switch (logicZone) {
            case 1:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX() + Config.BOX_SIZE, barrier.getGeo().getTopRight().getY() - Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getBottomLeft().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 2:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX() + Config.BOX_SIZE, barrier.getGeo().getTopRight().getY() - Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getTopLeft().getY() - Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 3:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getTopLeft().getY() - Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX() + Config.BOX_SIZE, barrier.getGeo().getBottomRight().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 4:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX() + Config.BOX_SIZE, barrier.getGeo().getTopRight().getY() - Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX() + Config.BOX_SIZE, barrier.getGeo().getBottomRight().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 5:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopRight().getX() + Config.BOX_SIZE, barrier.getGeo().getTopRight().getY() - Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getBottomLeft().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 6:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX() + Config.BOX_SIZE, barrier.getGeo().getBottomRight().getY() + Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getBottomLeft().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 7:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getTopLeft().getY() + Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomRight().getX() + Config.BOX_SIZE, barrier.getGeo().getBottomRight().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
                break;
            case 8:
                firstPotentialPoint = new Coordinates(barrier.getGeo().getTopLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getTopLeft().getY() - Config.BOX_SIZE);
                secondPotentialPoint = new Coordinates(barrier.getGeo().getBottomLeft().getX() - Config.BOX_SIZE, barrier.getGeo().getBottomLeft().getY() + Config.BOX_SIZE);
                coordinate = potentialWayHandler(x, y, firstPotentialPoint, secondPotentialPoint);
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

    private Coordinates checkProbabilisticWay(int x, int y, Coordinates firstPotentialPoint, Coordinates secondPotentialPoint) {
        Coordinates lastPoint = wayList.get(wayList.size() - 1);

        if (Math.abs(lastPoint.getX() - x) < Config.BOX_SIZE /4  && Math.abs(lastPoint.getY() - y) < Config.BOX_SIZE /4){
//            System.out.println("Method 'checkProbabilisticWay()' returns null");

            return null;
        }
//        if (lastPoint.equals(firstPotentialPoint) || wayList.contains(firstPotentialPoint))
//            return firstPotentialPoint;
//
//        if (lastPoint.equals(secondPotentialPoint) || wayList.contains(firstPotentialPoint))
//            return secondPotentialPoint;


        return lastPoint;

    }

    private Coordinates potentialWayHandler(int x, int y, Coordinates firstPotentialPoint, Coordinates secondPotentialPoint) {
        Coordinates coordinate = null;
        if (wayList.isEmpty()) {
            coordinate = getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
            wayList.add(coordinate);
        } else {
            coordinate = checkProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
            if (coordinate == null) {
                coordinate = getProbabilisticWay(x, y, firstPotentialPoint, secondPotentialPoint);
                wayList.add(coordinate);
            }
        }

        return coordinate;
    }

    private Coordinates getNeighbor(int xStart,int yStart, Coordinates coordinates){
		return coordinates;

    }

    private class Cluster {
        private Coordinates topLeft = new Coordinates();
        private Coordinates topRight = new Coordinates();
        private Coordinates bottomLeft = new Coordinates();
        private Coordinates bottomRight = new Coordinates();

        public Coordinates getTopLeft() {
            return topLeft;
        }

        public void setTopLeft(Coordinates topLeft) {
            this.topLeft = topLeft;
        }

        public Coordinates getTopRight() {
            return topRight;
        }

        public void setTopRight(Coordinates topRight) {
            this.topRight = topRight;
        }

        public Coordinates getBottomLeft() {
            return bottomLeft;
        }

        public void setBottomLeft(Coordinates bottomLeft) {
            this.bottomLeft = bottomLeft;
        }

        public Coordinates getBottomRight() {
            return bottomRight;
        }

        public void setBottomRight(Coordinates bottomRight) {
            this.bottomRight = bottomRight;
        }
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

    private int getNextCoordinateByLineEquation(int dx, int expectedY, int x, int y, Coordinates coordinate){
        double resultY = lineEquation(dx, coordinate.getX(), x, coordinate.getY(), y);
        int dimension = doShift(expectedY, (int)resultY + Config.BOX_SIZE);
        return  dimension;
    }

    public void nextCoordinate(int x, int y, Point point) {
//        int finishX = Portals.getExit().getCoordinates().getX();
//        int finishY = Portals.getExit().getCoordinates().getY();
//        System.out.println("wayList.size="+wayList.size());
//        System.out.println("wayList.size="+wayList.size());
//        int dx, dy;
//        TileMap barrier = getBarrier(x, finishX, y, finishY);
//        Coordinates coordinate = new Coordinates(finishX, finishY);
//        if (barrier != null){
//            coordinate = getTransitionalFinish(x, y, barrier);
//        }

//        List<Integer> chanceDirection = chanceDirection(x, y, coordinate.getX(), coordinate.getY());
//        dx = chanceDirection.get(0);
//        dy = chanceDirection.get(1);

        JPS jpsg = new JPS();
//
//        dx = doShift(x, coordinate.getX());
//        int expectedY = doShift(coordinate.getY(), y);
//
//        dy = getNextCoordinateByLineEquation(x+dx, y+expectedY, x, y, coordinate);
//        point.setLocation(dx, dy);
    }

}
