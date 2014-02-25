package org.game.towers.workers.Algorithms.JumpPointSearch;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Holds a Node[][] 2d array "grid" for path-finding tests, all drawing is done through here.
 * @author Clint Mullins
 *
 */
public class Grid {
	private volatile Node[][] grid;
	private volatile Heap heap = new Heap();
    private int unitId;
    private volatile HashMap<Integer, Heap> heapHashMap = new HashMap<Integer, Heap>();

    public Grid(Node[][] grid){
        this.grid = grid;
        heap = new Heap();
    }

	/**
	 * returns all adjacent nodes that can be traversed
	 *
	 * @param node (Node) finds the neighbors of this node
	 * @return (int[][]) list of neighbors that can be traversed
	 */
	public synchronized int[][] getNeighbors(Node node){
		int[][] neighbors = new int[8][2];
		int x = node.x;
		int y = node.y;
		boolean d0 = false; //These booleans are for speeding up the adding of nodes.
		boolean d1 = false;
		boolean d2 = false;
		boolean d3 = false;

		if (walkable(x,y-1)){
			neighbors[0] = (tmpInt(x,y-1));
			d0 = d1 = true;
		}
		if (walkable(x+1,y)){
			neighbors[1] = (tmpInt(x+1,y));
			d1 = d2 = true;
		}
		if (walkable(x,y+1)){
			neighbors[2] = (tmpInt(x,y+1));
			d2 = d3 = true;
		}
		if (walkable(x-1,y)){
			neighbors[3] = (tmpInt(x-1,y));
			d3 = d0 = true;
		}
		if (d0 && walkable(x-1,y-1)){
			neighbors[4] = (tmpInt(x-1,y-1));
		}
		if (d1 && walkable(x+1,y-1)){
			neighbors[5] = (tmpInt(x+1,y-1));
		}
		if (d2 && walkable(x+1,y+1)){
			neighbors[6] = (tmpInt(x+1,y+1));
		}
		if (d3 && walkable(x-1,y+1)){
			neighbors[7] = (tmpInt(x-1,y+1));
		}
		return neighbors;
	}

//---------------------------Passability------------------------------//
	/**
	 * Tests an x,y node's passability
	 *
	 * @param x (int) node's x coordinate
	 * @param y (int) node's y coordinate
	 * @return (boolean) true if the node is obstacle free and on the map, false otherwise
	 */
	public synchronized boolean walkable(int x, int y){

			try{
				return getNode(x,y).pass;
			}
			catch (Exception e){
				return false;
			}
	}
//--------------------------------------------------------------------//

	public ArrayList<Node> pathCreate(Node node){
		ArrayList<Node> trail = new ArrayList<Node>();
		System.out.println("Tracing Back Path...");
		while (node.parent!=null){
			try{
				trail.add(0,node);
			}catch (Exception e){}
			//drawLine(node.parent.x, node.parent.y, node.x, node.y);
			node = node.parent;
		}
		System.out.println("Path Trace Complete!");
		return trail;
	}

//--------------------------HEAP-----------------------------------//
	/**
	 * Adds a node's (x,y,f) to the heap. The heap is sorted by 'f'.
	 *
	 * @param node (Node) node to be added to the heap
	 */
	public synchronized void heapAdd(Node node, int unitId){
        this.unitId = unitId;
		float[] tmp = {node.x,node.y,node.f};
		heap.add(tmp);
//        int xa = node.x >> Config.COORDINATES_SHIFTING;
//        int ya = node.y >> Config.COORDINATES_SHIFTING;
//        Game.instance.getWorld().getLevel().getTile(xa, ya).setHighlight(1.5);
        heapHashMap.put(unitId, heap);
	}

	/**
	 * @return (int) size of the heap
	 */
	public synchronized int heapSize(){
        Heap currentHeap = heapHashMap.get(unitId);
		return currentHeap.getSize();
	}
	/**
	 * @return (Node) takes data from popped float[] and returns the correct node
	 */
	public synchronized Node heapPopNode(){
		//System.out.println("[heap] size: "+heap.getSize());
        Heap currentHeap = heapHashMap.get(unitId);
		float[] tmp = currentHeap.pop();
		return getNode((int)tmp[0],(int)tmp[1]);
	}
//-----------------------------------------------------------------//

	/**
	 * Encapsulates x,y in an int[] for returning. A helper method for the jump method
	 *
	 * @param x (int) point's x coordinate
	 * @param y (int) point's y coordinate
	 * @return ([]int) bundled x,y
	 */
	public synchronized int[] tmpInt (int x, int y){
		int[] tmpIntsTmpInt = {x,y};  //create the tmpInt's tmpInt[]
		return tmpIntsTmpInt;         //return it
	}

	/**
	 * getFunc - Node at given x, y
	 *
	 * @param x (int) desired node x coordinate
	 * @param y (int) desired node y coordinate
	 * @return (Node) desired node
	 */
	public synchronized Node getNode(int x, int y){
		try{
			return grid[x][y];
		}
		catch(Exception e){
			return null;
		}
	}

	public float toPointApprox(float x, float y, int tx, int ty){
		return (float) Math.sqrt(Math.pow(Math.abs(x-tx),2) + Math.pow(Math.abs(y-ty), 2));
	}
}

