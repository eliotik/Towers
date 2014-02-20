package org.game.towers.workers;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.level.Portals;
import org.game.towers.level.tiles.Tile;
import org.game.towers.workers.Algorithms.JumpPointSearch.JPS;
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathWorker {

    private HashMap<Integer, ArrayList<Node>> trailMap = new HashMap<Integer, ArrayList<Node>>();
    private HashMap<Integer, ArrayList<Node>> visitedNodesMap = new HashMap<Integer, ArrayList<Node>>();
    private int steps = 0;
    private JPS jps;
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

    private void initJPS() {
        if (jps == null){
            this.jps = new JPS();
        }
    }


//        ArrayList<Node> trail = trailMap.get(id);
//        if (trail == null) {
//            JPS jps = new JPS(x, y, id);
//            trail = jps.getTrail();
//            if (trail != null && trail.size() > 0) {
//	            trailMap.put(id, trail);
//	            ArrayList<Node> visitedNodes = new ArrayList<Node>();
//	            visitedNodes.add(trail.get(0));
//	            visitedNodesMap.put(id, visitedNodes);
//            }
//        }


    public synchronized void nextCoordinate(int x, int y, Point point, int id) {
        int dx, dy;
        initJPS();
//        jps.search();
        ArrayList<Node> temporaryTrail = null;
        Node nextNode;
        ArrayList<Node> trailNodes;
        ArrayList<Node> visitedNodes = new ArrayList<Node>();

        if (trailMap.size() == 0) {
            trailNodes = jps.search(x, y, id);
            trailMap.put(id, trailNodes);
        }

        trailNodes = trailMap.get(id);

        if (visitedNodesMap.size() == 0) {
            visitedNodes.add(trailNodes.get(0));
            visitedNodesMap.put(id, visitedNodes);
        }

        visitedNodes = visitedNodesMap.get(id);

//        try {
//            visitedNodes = visitedNodesMap.get(id);
//        } catch (NullPointerException e) {
//            visitedNodes.add(temporaryTrail.get(0));
//            visitedNodesMap.put(id, visitedNodes);
//        }


//        ArrayList<Node> trail = trailMap.get(id);
//        if (trail == null || trail.size() == 0) {
//        	point.setLocation(0, 0);
//        	return;
//        }
//
//        ArrayList<Node> visitedNodes = visitedNodesMap.get(id);
        nextNode = trailNodes.get(visitedNodes.size());

        Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).setHighlight(0.8);
        int nextX = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getX()*Config.BOX_SIZE;
        int nextY = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getY()*Config.BOX_SIZE;
        if (nextX == x && nextY == y) {
            visitedNodes.add(nextNode);
            visitedNodesMap.put(id, visitedNodes);
        }

        dx = doShift(x, nextX);
        dy = doShift(y, nextY);

        point.setLocation(dx, dy);
    }

}
