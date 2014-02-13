package org.game.towers.workers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.level.tiles.Tile;
import org.game.towers.workers.Algorithms.JumpPointSearch.JPS;
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathWorker {

    private HashMap<String, ArrayList<Node>> trailMap = new HashMap<String, ArrayList<Node>>();
    private HashMap<String, ArrayList<Node>> visitedNodesMap = new HashMap<String, ArrayList<Node>>();
    private int steps = 0;
    private int doShift(int dimension1, int dimension2) {
        int deltaDim = dimension2 - dimension1;
        if (deltaDim > 0){
            return 1;
        }
        if (deltaDim < 0){
            return -1;
        }

        return 0;
    }

    private void initJPS(int x, int y, String id) {
        ArrayList<Node> trail = trailMap.get(id);
        ArrayList<Node> visitedNodes = new ArrayList<Node>();
        if (trail == null) {
            JPS jps = new JPS(x,y);
            trail = jps.getTrail();
            trailMap.put(id, trail);
            visitedNodes.add(trail.get(0));
            visitedNodesMap.put(id, visitedNodes);
        }
    }

    public synchronized void nextCoordinate(int x, int y, Point point, String id) {
        int dx, dy;
        initJPS(x, y, id);
        Node nextNode;

        ArrayList<Node> trail = trailMap.get(id);

        ArrayList<Node> visitedNodes = visitedNodesMap.get(id);
        nextNode = trail.get(visitedNodes.size());

//        Tile tile = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4);
//        Game.instance.getWorld().getLevel().getTile(nextNode.getX(), nextNode.getY()).setHighlight(0.8);
        Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).setHighlight(0.8);
        int nextX = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getX()*Config.BOX_SIZE;
        int nextY = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getY()*Config.BOX_SIZE;
//        if (Game.instance.getWorld().getLevel().getUnits().get(0).getNumSteps() - steps >= 10) {
//	        System.out.println(">>>>====================");
////	    	System.out.println("tile x: "+tile.getX()+", nextnode x: "+nextNode.getX()+", npc x: "+ x+", tile x + boxsize/2: "+ (tile.getX()-Config.BOX_SIZE_FIXED - Config.BOX_SIZE_FIXED/2));
////	    	System.out.println("tile y: "+tile.getY()+", nextnode y: "+nextNode.getY()+", npc y: "+ y+", tile y + boxsize/2: "+ (tile.getY()-Config.BOX_SIZE_FIXED - Config.BOX_SIZE_FIXED/2));
//            System.out.println("x: "+x+ " nextX: " + (nextX) + " y: " + y + " nextY: " + (nextY));
////            System.out.println("visitedNodes.size() " + visitedNodes.size());
//            System.out.println("<<<<====================");
//	    	steps = Game.instance.getWorld().getLevel().getUnits().get(0).getNumSteps();
//        }
//        if ( ((tile.getX()-Config.BOX_SIZE_FIXED) - Config.BOX_SIZE_FIXED/2) == x && ((tile.getY()-Config.BOX_SIZE_FIXED) - Config.BOX_SIZE_FIXED/2) == y ) {
//    	if ((nextNode.getX() - Config.BOX_SIZE_FIXED) == x && (nextNode.getY() - Config.BOX_SIZE_FIXED) == y) {
        if (nextX == x && nextY == y) {
            visitedNodes.add(nextNode);
            visitedNodesMap.put(id, visitedNodes);
        }
//        nextNode = trail.get(visitedNodes.size());
//        Tile tile = Game.instance.getWorld().getLevel().getTile(nextNode.getX(), nextNode.getY());
//        System.out.println("id: "+ id +" nextNode.getX(): "+ nextNode.getX() + " nextNode.getY(): " + nextNode.getY());
//        dx = doShift(x, tile.getX()-Config.BOX_SIZE_FIXED);
//        dx = doShift(x, tile.getX()-Config.BOX_SIZE_FIXED);
        dx = doShift(x, nextX);
        dy = doShift(y, nextY);

        point.setLocation(dx, dy);
    }

}
