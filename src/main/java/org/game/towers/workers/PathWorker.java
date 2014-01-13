package org.game.towers.workers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.grid.Cell;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PathWorker {
    private boolean isAvailable = true;
    private Cell finishCell = new Cell(799, 400);

    public boolean pathCheck() {
        return isAvailable;
    }

    public Cell getNextCell(Cell currentCell) {

//        List<Cell> neighborList = getEmptyNeighbor(currentCell);
//        for(Cell neighbor : neighborList) {
//
//        }
        Cell cell = new Cell(0,0);
        return cell;
    }

    private int roundDownScale(double aValue)
    {
        BigDecimal decimal = new BigDecimal(aValue);
        decimal = decimal.setScale(2,BigDecimal.ROUND_DOWN);
        int result = decimal.intValue();
        return result;
    }

    private Cell hasBarrier(Cell startCell, Cell finishCell){
        int xStart = startCell.getGridX();
        int yStart = startCell.getGridY();
        int xFinish = finishCell.getGridX();
        int yFinish = finishCell.getGridY();
        int checkX, checkY;
        double deltaX = (xFinish - xStart) / (2 * Config.AMOUNT_HORIZONTAL_PIX);
        double deltaY = (yFinish - yStart) / (2 * Config.AMOUNT_VERTICAL_PIX);

        for (double x = xStart; x < xFinish; x = x + deltaX) {
            for (double y = yStart; y < yFinish; y = y + deltaY) {
                checkX = roundDownScale(x);
                checkY = roundDownScale(y);
                if (Game.grid.cells[checkX][checkY].getType() == null){
                    Cell barrierCell = Game.grid.cells[checkX][checkY];
                    return barrierCell;
                }
            }
        }
        return null;
    }

    private List<Cell> getRectangleApexCell(Cell cell) {
        List<Cell> apex = new ArrayList<Cell>();
        Rectangle size = cell.getType().getGeo();
        double unitX = size.getX();
        double unitY = size.getY();
        double unitWidth = size.getWidth();
        double unitHeight = size.getHeight();

        Cell apexCellA = Game.grid.cells[(int)unitX - 1][(int)unitY - 1];
        Cell apexCellB = Game.grid.cells[(int)unitX + (int)unitWidth + 1][(int)unitY - 1];
        Cell apexCellC = Game.grid.cells[(int)unitX + (int)unitWidth + 1][(int)unitY + (int)unitHeight + 1];
        Cell apexCellD = Game.grid.cells[(int)unitX + (int)unitWidth - 1][(int)unitY + (int)unitHeight + 1];

        apex.add(apexCellA);
        apex.add(apexCellB);
        apex.add(apexCellC);
        apex.add(apexCellD);

        return apex;
    }

    private List<Cell> checkNeighborUnitCell(Cell cell){
        List<Cell> listNeighborUnitCell = new ArrayList<Cell>();
        int towerWidth = (int)cell.getType().getGeo().getWidth();
        int towerHeight = (int)cell.getType().getGeo().getHeight();
        int towerX = cell.getType().getTileX(); // если возвращает положение строения, то правильно
        int towerY = cell.getType().getTileY(); // если возвращает положение строения, то правильно

        if (towerX > 5 && towerY > 5) {
            for (int x = towerX - 6; x < towerX + towerWidth + 6; x++) {
                for (int y = towerY - 6; y < towerY + towerHeight + 6; y++) {
                    if (Game.grid.cells[x][y] != null) {
                        listNeighborUnitCell.add(Game.grid.cells[x][y]);
                    }
                }
            }
            return listNeighborUnitCell;
        }
        return null;
    }

    private List<Cell> getMainRectangleApexCell(List<Cell> apex, Cell startCell) {
        List<Cell> mainApex = new ArrayList<Cell>();
        for(Cell apexItem : apex) {
            if (hasBarrier(startCell, apexItem) == null){
                mainApex.add(apexItem);
            }
        }
        return mainApex;
    }

    private List<Cell> getBorderCells(Cell cell) {
        List<Cell> borders = new ArrayList<Cell>();

        return borders;
    }

    private List<Cell> getEmptyNeighbor(Cell currentCell) {
        List<Cell> neighborList = new ArrayList<Cell>();

        for(int x = currentCell.getGridX() - 1; x <= currentCell.getGridX() + 1; x++ ) {
            for(int y = currentCell.getGridY() - 1; y <= currentCell.getGridY() + 1; y++ ) {
                if (currentCell.getType() == null) {
                    neighborList.add(Game.grid.cells[x][y]);
                }
            }
        }
        return neighborList;
    }
}
