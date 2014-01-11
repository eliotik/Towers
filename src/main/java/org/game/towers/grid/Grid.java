package org.game.towers.grid;

import org.game.towers.configs.Config;

public class Grid {
    private int xPixelSize = Config.SCREEN_WIDTH / Config.AMOUNT_HORIZONTAL_PIX;
    private int yPixelSize = Config.SCREEN_HEIGHT / Config.AMOUNT_VERTICAL_PIX;
    public Cell[][] cells = new Cell[Config.AMOUNT_HORIZONTAL_PIX][Config.AMOUNT_VERTICAL_PIX];

    public Grid() {
        for(int x = 0; x < Config.AMOUNT_HORIZONTAL_PIX; x++) {
            for(int y = 0; y < Config.AMOUNT_VERTICAL_PIX; y++) {
                int X = x * xPixelSize;
                int Y = y * yPixelSize;
                cells[x][y] = new Cell(X, xPixelSize, Y, yPixelSize, x, y);
            }
        }
    }
}
