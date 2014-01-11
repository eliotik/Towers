package org.game.towers.workers;

import org.game.towers.game.Game;
import org.game.towers.grid.Cell;
import org.hamcrest.Matchers;

import java.util.ArrayList;
import java.util.List;

import static ch.lambdaj.Lambda.*;

public class PathWorker {
    private boolean isAvailable = true;
    private Cell finishCell = new Cell(799, 400);

    public boolean pathCheck() {
        return isAvailable;
    }

    public Cell getNextCell(Cell currentCell) {

        List<Cell> neighborList = getEmptyNeighbor(currentCell);
        for(Cell neighbor : neighborList) {

        }
        return cell;
    }

    private double directionMultiplier(Cell neighbor) {
        double directionMultiplier = 0.5;

        return directionMultiplier;
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
