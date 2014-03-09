package org.game.towers.workers.Algorithms.JumpPointSearch;

import java.util.ArrayList;
import java.util.HashMap;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.level.Portals;

/**
 * @author Clint Mullins
 * @referenced Javascript version of JPS by aniero / https://github.com/aniero
 */
public class JPS {

    private int endX,endY;
    private volatile int[] tmpXY;
    private float ng;
    private HashMap<Integer, Grid> gridHashMap = new HashMap<Integer, Grid>();

    public JPS(){
        this.endX = Portals.getExit().getCoordinates().getX();
        this.endY = Portals.getExit().getCoordinates().getY();
    }

    public synchronized ArrayList<Node> search(int xStart, int yStart, int unitId){
        Node[][] tiles = Game.instance.getWorld().getLevel().generateGridForJSP(unitId);
        Node[] possibleSuccessUnitId;
        Grid newGrid = new Grid(tiles);
        if (gridHashMap.get(unitId) == null) {
            gridHashMap.put(unitId, newGrid);
        }
        Grid unitGrid = gridHashMap.get(unitId);
        Node cur;
        ArrayList<Node> trailNodes;
        unitGrid.getNode(xStart,yStart).updateGHFP(0, 0, null);
        unitGrid.heapAdd(unitGrid.getNode(xStart, yStart), unitId);
        while (true){
            cur = unitGrid.heapPopNode();

            if (cur.x == endX && cur.y == endY){
                trailNodes = unitGrid.pathCreate(cur);
                for (Node item : trailNodes){
                    int xa = item.x >> Config.COORDINATES_SHIFTING;
                    int ya = item.y >> Config.COORDINATES_SHIFTING;
                    if (Config.LEVEL_HIGHLIGHT_PATH_TILES) {
                    	Game.instance.getWorld().getLevel().getTile(xa, ya).setHighlight(1.2);
                    }
                }
                break;
            }
            possibleSuccessUnitId = identifySuccessors(cur, unitId);
            for (int i=0;i<possibleSuccessUnitId.length;i++){
                if (possibleSuccessUnitId[i]!=null){
                    unitGrid.heapAdd(possibleSuccessUnitId[i], unitId);
                }
            }
            if (unitGrid.heapSize()==0){
                return null;
            }
        }
        return trailNodes;
    }

	/**
	 * @param node
	 * @return all nodes jumped from given node
	 */
    public synchronized Node[] identifySuccessors(Node node, int unitId){
        Grid unitGrid = gridHashMap.get(unitId);
        Node[] successorsUnit = new Node[8];				//empty successors list to be returned
        int[][] neighborsUnit = getNeighborsPrune(node, unitId);    //all neighbors after pruned
        for (int i=0; i<neighborsUnit.length; i++){ //for each of these neighbors
            tmpXY = jump(neighborsUnit[i][0],neighborsUnit[i][1],node.x,node.y, unitId); //get next jump point
            if (tmpXY[0]!=-1){								//if that point is not null( {-1,-1} )
                int x = tmpXY[0];
                int y = tmpXY[1];
                ng = (unitGrid.toPointApprox(x,y,node.x,node.y) + node.g);   //get the distance from start
                if (unitGrid.getNode(x,y).f<=0 || unitGrid.getNode(x,y).g>ng){  //if this node is not already found, or we have a shorter distance from the current node
                    unitGrid.getNode(x,y).updateGHFP(unitGrid.toPointApprox(x,y,node.x,node.y)+node.g,unitGrid.toPointApprox(x,y,endX,endY),node); //then update the rest of it
                    successorsUnit[i] = unitGrid.getNode(x,y);  //add this node to the successors list to be returned
                }
            }
        }
        return successorsUnit;  //finally, successors is returned
    }

	/**
	 * jump method recursively searches in the direction of parent (px,py) to child, the current node (x,y).
	 * It will stop and return its current position in three situations:
	 *
	 * 1) The current node is the end node. (endX, endY)
	 * 2) The current node is a forced neighbor.
	 * 3) The current node is an intermediate step to a node that satisfies either 1) or 2)
	 *
	 * @param x (int) current node's x
	 * @param y (int) current node's y
	 * @param px (int) current.parent's x
	 * @param py (int) current.parent's y
	 * @return (int[]={x,y}) node which satisfies one of the conditions above, or null if no such node is found.
	 */

    public synchronized int[] jump(int x, int y, int px, int py, int unitId){
        Grid unitGrid = gridHashMap.get(unitId);
        int[] jx = {-1,-1}; //used to later check if full or null
        int[] jy = {-1,-1}; //used to later check if full or null
        int dx = (x-px)/Math.max(Math.abs(x-px), 1); //because parents aren't always adjacent, this is used to find parent -> child direction (for x)
        int dy = (y-py)/Math.max(Math.abs(y-py), 1); //because parents aren't always adjacent, this is used to find parent -> child direction (for y)

        if (!unitGrid.walkable(x,y)){ //if this space is not grid.walkable, return a null.
            return tmpInt(-1,-1); //in this system, returning a {-1,-1} equates to a null and is ignored.
        }
        if (x==this.endX && y==this.endY){   //if end point, return that point. The search is over! Have a beer.
            return tmpInt(x,y);
        }
        if (dx!=0 && dy!=0){  //if x and y both changed, we are on a diagonally adjacent square: here we check for forced neighbors on diagonals
            if ((unitGrid.walkable(x-dx,y+dy) && !unitGrid.walkable(x-dx,y)) || //we are moving diagonally, we don't check the parent, or our next diagonal step, but the other diagonals
                    (unitGrid.walkable(x+dx,y-dy) && !unitGrid.walkable(x,y-dy))){  //if we find a forced neighbor here, we are on a jump point, and we return the current position
                return tmpInt(x,y);
            }
        }
        else{ //check for horizontal/vertical
            if (dx!=0){ //moving along x
                if ((unitGrid.walkable(x+dx,y+1) && !unitGrid.walkable(x,y+1)) || //we are moving along the x axis
                        (unitGrid.walkable(x+dx,y-1) && !unitGrid.walkable(x,y-1))){  //we check our side nodes to see if they are forced neighbors
                    return tmpInt(x,y);
                }
            }
            else{
                if ((unitGrid.walkable(x+1,y+dy) && !unitGrid.walkable(x+1,y)) ||  //we are moving along the y axis
                        (unitGrid.walkable(x-1,y+dy) && !unitGrid.walkable(x-1,y))){	 //we check our side nodes to see if they are forced neighbors
                    return tmpInt(x,y);
                }
            }
        }

        if (dx!=0 && dy!=0){ //when moving diagonally, must check for vertical/horizontal jump points
            jx = jump(x+dx,y,x,y,unitId);
            jy = jump(x,y+dy,x,y,unitId);
            if (jx[0]!=-1 || jy[0]!=-1){
                return tmpInt(x,y);
            }
        }
        if (unitGrid.walkable(x+dx,y) || unitGrid.walkable(x,y+dy)){ //moving diagonally, must make sure one of the vertical/horizontal neighbors is open to allow the path
            return jump(x+dx,y+dy,x,y,unitId);
        }
        else { //if we are trying to move diagonally but we are blocked by two touching corners of adjacent nodes, we return a null
            return tmpInt(-1,-1);
        }
    }

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

    public synchronized int[][] getNeighborsPrune(Node node, int unitId){
        Node parent = node.parent;    //the parent node is retrieved for x,y values
        int x = node.x;
        int y = node.y;
        int px, py, dx, dy;
        int[][] neighbors = new int[5][2];
        Grid unitGrid = gridHashMap.get(unitId);
        //directed pruning: can ignore most neighbors, unless forced
        if (parent!=null){
            px = parent.x;
            py = parent.y;
            //get the normalized direction of travel
            dx = (x-px)/Math.max(Math.abs(x-px), 1);
            dy = (y-py)/Math.max(Math.abs(y-py), 1);
            //search diagonally
            if (dx!=0 && dy!=0){
                if (unitGrid.walkable(x, y+dy)){
                    neighbors[0] = (tmpInt(x,y+dy));
                }
                if (unitGrid.walkable(x+dx,y)){
                    neighbors[1] = (tmpInt(x+dx,y));
                }
                if (unitGrid.walkable(x,y+dy) || unitGrid.walkable(x+dx,y)){
                    neighbors[2] = (tmpInt(x+dx,y+dy));
                }
                if (!unitGrid.walkable(x-dx,y) && unitGrid.walkable(x,y+dy)){
                    neighbors[3] = (tmpInt(x-dx,y+dy));
                }
                if (!unitGrid.walkable(x,y-dy) && unitGrid.walkable(x+dx,y)){
                    neighbors[4] = (tmpInt(x+dx,y-dy));
                }
            }
            else{
                if (dx==0){
                    if (unitGrid.walkable(x,y+dy)){
                        if (unitGrid.walkable(x,y+dy)){
                            neighbors[0] = (tmpInt(x,y+dy));
                        }
                        if (!unitGrid.walkable(x+1,y)){
                            neighbors[1] = (tmpInt(x+1,y+dy));
                        }
                        if (!unitGrid.walkable(x-1,y)){
                            neighbors[2] = (tmpInt(x-1,y+dy));
                        }
                    }
                }
                else{
                    if (unitGrid.walkable(x+dx,y)){
                        if (unitGrid.walkable(x+dx,y)){
                            neighbors[0] = (tmpInt(x+dx,y));
                        }
                        if (!unitGrid.walkable(x, y+1)){
                            neighbors[1] = (tmpInt(x+dx,y+1));
                        }
                        if (!unitGrid.walkable(x, y-1)){
                            neighbors[2] = (tmpInt(x+dx,y-1));
                        }
                    }
                }
            }
        }
        else {//return all neighbors
            return unitGrid.getNeighbors(node); //adds initial nodes to be jumped from!
        }
        return neighbors; //this returns the neighbors, you know
    }
}
