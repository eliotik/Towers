package org.game.towers.workers.Algorithms.JumpPointSearch;

public class Main {

    public static void main(String[] args){
		
		int xMax = 1000;  //size of grid x direction
		int yMax = 1000;  //size of the grid y direction
		int xIsland = 100; //islands along the x direction
		int yIsland = 100; //islands along the y direction
		boolean uniform = false; //true for uniform land generation, false for random land generation
		boolean draw = true;
		Node[][] grid = null;

        JPS jpsg = new JPS(xMax,yMax,xIsland,yIsland,uniform,draw,grid);
//        JPS jpsg = new JPS();
	}
}
