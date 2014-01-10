package org.game.towers.grid;

public class Cell {
    private int x;
    private int y;
    private int width;
    private int height;
    private int type;

    public Cell(int x, int xSize, int y, int ySize) {
        this.x = x;
        this.y = y;
        this.width = x * xSize;
        this.height = y * ySize;
    }
}
