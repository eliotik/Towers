package org.game.towers.workers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.grid.Cell;
import org.game.towers.level.Construction;
import org.game.towers.level.Level;
import org.game.towers.units.Unit;
import org.hamcrest.Matchers;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.*;

public class PathWorker {

    private boolean isAvailable = true;
    private Level level = Game.instance.getWorld().getLevel();

    public boolean pathCheck() {
        return isAvailable;
    }

//    public Cell getNextCell(Cell currentCell) {
//
////        List<Cell> neighborList = getEmptyNeighbor(currentCell);
////        for(Cell neighbor : neighborList) {
////
////        }
//        Cell cell = new Cell(0,0);
//        return cell;
//    }

    private int roundDownScale(double aValue)
    {
        BigDecimal decimal = new BigDecimal(aValue);
        decimal = decimal.setScale(2,BigDecimal.ROUND_DOWN);
        int result = decimal.intValue();
        return result;
    }

//    private Cell hasBarrier(Cell startCell, Cell finishCell){
//        int xStart = startCell.getGridX();
//        int yStart = startCell.getGridY();
//        int xFinish = finishCell.getGridX();
//        int yFinish = finishCell.getGridY();
//        int checkX, checkY;
//        double deltaX = (xFinish - xStart) / (2 * Config.AMOUNT_HORIZONTAL_PIX);
//        double deltaY = (yFinish - yStart) / (2 * Config.AMOUNT_VERTICAL_PIX);
//
//        for (double x = xStart; x < xFinish; x = x + deltaX) {
//            for (double y = yStart; y < yFinish; y = y + deltaY) {
//                checkX = roundDownScale(x);
//                checkY = roundDownScale(y);
//                if (Game.grid.cells[checkX][checkY].getType() == null){
//                    Cell barrierCell = Game.grid.cells[checkX][checkY];
//                    return barrierCell;
//                }
//            }
//        }
//        return null;
//    }

    private Construction getBarrier(int xStart, int xFinish, int yStart, int yFinish){
        List<Construction> temporaryConstructions = Game.instance.getWorld().getLevel().getConstructions();
        if ( (yFinish > yStart  && xFinish > xStart) || (yFinish < yStart  && xFinish < xStart) ) {
            for(Construction construction : temporaryConstructions) {
                int apexX = construction.getApexCX();
                int apexY = construction.getApexCY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY > y) {
                    temporaryConstructions.remove(construction);
                }
            }
            for(Construction construction : temporaryConstructions) {
                int apexX = construction.getApexAX();
                int apexY = construction.getApexAY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y) {
                    temporaryConstructions.remove(construction);
                }
            }
        }

        if ( (yFinish > yStart  && xFinish < xStart) || (yFinish < yStart  && xFinish > xStart) ) {
            for(Construction construction : temporaryConstructions) {
                int apexX = construction.getApexDX();
                int apexY = construction.getApexDY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY > y) {
                    temporaryConstructions.remove(construction);
                }
            }
            for(Construction construction : temporaryConstructions) {
                int apexX = construction.getApexBX();
                int apexY = construction.getApexBY();
                double y = lineEquation(apexX, xFinish, xStart, yFinish, yStart);
                if (apexY < y) {
                    temporaryConstructions.remove(construction);
                }
            }
        }
        if (temporaryConstructions.isEmpty()) {
            return null;
        }

        Construction barrier = getFirstBarrier(temporaryConstructions, xStart, yStart);
        return barrier;
    }

    private double lineEquation(int x, int xFinish, int xStart, int yFinish, int yStart) {
        double b = (yFinish - yStart) / (xFinish - xStart);
        double c = yStart - xStart * b;
        double y = b * x + c;

        return y;
    }

    private Construction getFirstBarrier(List<Construction> constructions, int xStart, int yStart) {
        int minX = constructions.get(0).getApexAX();
        int minY = constructions.get(0).getApexAY();
        double minR = getRadiusVector(minX, minY, xStart, yStart);
        Construction nearestConstruction = constructions.get(0);
        for (Construction construction : constructions){
            double r = getRadiusVector(construction.getApexAX(), construction.getApexAY(), xStart, yStart);
            if (r < minR) {
                nearestConstruction = construction;
            }
        }
        return nearestConstruction;
    }

    private double getRadiusVector(int x, int y, int xStart, int yStart) {
        double r = Math.sqrt(Math.pow(x - xStart, 2) + Math.pow(y - yStart, 2));
        return r;
    }

    private static void getNeighbor(Construction construction){
        List<Construction> temporaryConstructions = Game.instance.getWorld().getLevel().getConstructions();
        temporaryConstructions.remove(construction);

        for(Construction constrItem : temporaryConstructions) {

        }

//        return null;
    }

    public Dimension getNextCoordinates(int x, int y){
        int finishX = Level.Portals.getExit().getX();
        int finishY = Level.Portals.getExit().getY();
        Construction barrier = getBarrier(x, finishX, y, finishY);
        double deltaX = (finishX - x);
        double deltaY = (finishY - y);
        Dimension dimension = new Dimension();

        return dimension;
    }

    private int getLogicZone(int x, int y, Construction construction){
        if (x < construction.getApexAX() && y < construction.getApexAY()) {
            return 1; // north West
        }

        if (x < construction.getApexAX() && x > construction.getApexBX() && y < construction.getApexBY()) {
            return 2; // north
        }

        if (x > construction.getApexBX() && y > construction.getApexBY()) {
            return 3; // north-east
        }

        if (x > construction.getApexBX() && y < construction.getApexBY() && y > construction.getApexCY()) {
            return 4; // east
        }

        if (x > construction.getApexCX() && y > construction.getApexCY()) {
            return 5; // south east
        }

        if (x > construction.getApexDX() && x < construction.getApexCX() && y > construction.getApexCY()) {
            return 6; // south
        }

        if (x < construction.getApexDX() && y > construction.getApexDY()) {
            return 7; // south west
        }

        if (x < construction.getApexAX() && y > construction.getApexAY() && y < construction.getApexDY()) {
            return 8; // west
        }
        return 0;
    }

    private int[] getIntermediateFinish(int x, int y, Construction construction) {
        int logicZone = getLogicZone(x, y, construction);
        switch (logicZone) {
            case 1:

                break;
        }

    }




//    private List<Cell> getRectangleApexCell(Cell cell) {
//        List<Cell> apex = new ArrayList<Cell>();
//        Rectangle size = cell.getType().getGeo();
//        double unitX = size.getX();
//        double unitY = size.getY();
//        double unitWidth = size.getWidth();
//        double unitHeight = size.getHeight();
//
//        Cell apexCellA = Game.grid.cells[(int)unitX - 1][(int)unitY - 1];
//        Cell apexCellB = Game.grid.cells[(int)unitX + (int)unitWidth + 1][(int)unitY - 1];
//        Cell apexCellC = Game.grid.cells[(int)unitX + (int)unitWidth + 1][(int)unitY + (int)unitHeight + 1];
//        Cell apexCellD = Game.grid.cells[(int)unitX + (int)unitWidth - 1][(int)unitY + (int)unitHeight + 1];
//
//        apex.add(apexCellA);
//        apex.add(apexCellB);
//        apex.add(apexCellC);
//        apex.add(apexCellD);
//
//        return apex;
//    }

//    private List<Cell> checkNeighborUnitCell(Cell cell){
//        List<Cell> listNeighborUnitCell = new ArrayList<Cell>();
//        int towerWidth = (int)cell.getType().getGeo().getWidth();
//        int towerHeight = (int)cell.getType().getGeo().getHeight();
//        int towerX = cell.getType().getTileX(); // если возвращает положение строения, то правильно
//        int towerY = cell.getType().getTileY(); // если возвращает положение строения, то правильно
//
//        if (towerX > 5 && towerY > 5) {
//            for (int x = towerX - 6; x < towerX + towerWidth + 6; x++) {
//                for (int y = towerY - 6; y < towerY + towerHeight + 6; y++) {
//                    if (Game.grid.cells[x][y] != null) {
//                        listNeighborUnitCell.add(Game.grid.cells[x][y]);
//                    }
//                }
//            }
//            return listNeighborUnitCell;
//        }
//        return null;
//    }

//    private List<Cell> getMainRectangleApexCell(List<Cell> apex, Cell startCell) {
//        List<Cell> mainApex = new ArrayList<Cell>();
//        for(Cell apexItem : apex) {
//            if (hasBarrier(startCell, apexItem) == null){
//                mainApex.add(apexItem);
//            }
//        }
//        return mainApex;
//    }
//
//    private List<Cell> getBorderCells(Cell cell) {
//        List<Cell> borders = new ArrayList<Cell>();
//
//        return borders;
//    }
//
//    private List<Cell> getEmptyNeighbor(Cell currentCell) {
//        List<Cell> neighborList = new ArrayList<Cell>();
//
//        for(int x = currentCell.getGridX() - 1; x <= currentCell.getGridX() + 1; x++ ) {
//            for(int y = currentCell.getGridY() - 1; y <= currentCell.getGridY() + 1; y++ ) {
//                if (currentCell.getType() == null) {
//                    neighborList.add(Game.grid.cells[x][y]);
//                }
//            }
//        }
//        return neighborList;
//    }

    private Unit getCollision() {
        List<Unit> units = Game.grid.units;


        return null;
    }
}
