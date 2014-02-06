package org.game.towers.workers.Algorithms.JumpPointSearch;

import org.game.towers.level.tiles.TileMap;

public class Node {
	int x,y;
	float g,h,f;  //g = from start; h = to end, f = both together
	boolean pass;
	Node parent;
	
	public Node(int x,int y){
		this.x = x;
		this.y = y; 
		this.pass = true;
	}

    public Node(TileMap tileMap) {
        this.x = tileMap.getGeo().getTopLeft().getX();
        this.y = tileMap.getGeo().getTopLeft().getY();
        this.pass = tileMap.getTile().isSolid();
    }
	
	public void updateGHFP(float g, float h, Node parent){
		this.parent = parent;
		this.g = g;
		this.h = h;
		f = g+h;
	}
	
	public boolean setPass(boolean pass){
		this.pass = pass;
		return pass;
	}
}