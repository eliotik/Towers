package org.game.towers.grid;


import org.game.towers.units.Unit;

public class Cell {
    private int x;
    private int y;
    private int gridX;
    private int gridY;
    private int width;
    private int height;
    private Unit type;

    public Cell(int x, int xSize, int y, int ySize, int gridX, int gridY) {
        this.x = x;
        this.y = y;
        this.width = x * xSize;
        this.height = y * ySize;
        this.gridX = gridX;
        this.gridY = gridY;
    }

    public Cell(int x, int y){
        this.gridX = x;
        this.gridY = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public Unit getType() {
        return type;
    }

    public void setType(Unit type) {
        this.type = type;
    }
}
