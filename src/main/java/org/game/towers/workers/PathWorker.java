package org.game.towers.workers;

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

        Tile tile = Game.instance.getWorld().getLevel().getTile(nextNode.getX(), nextNode.getY());
        Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).setHighlight(0.8);
        if (x == tile.getX() && y == tile.getY()) {
            visitedNodes.add(nextNode);
            visitedNodesMap.put(id, visitedNodes);
        }
//        nextNode = trail.get(visitedNodes.size());
//        Tile tile = Game.instance.getWorld().getLevel().getTile(nextNode.getX(), nextNode.getY());
//        System.out.println("id: "+ id +" nextNode.getX(): "+ nextNode.getX() + " nextNode.getY(): " + nextNode.getY());
        dx = doShift(x, tile.getX());
        dy = doShift(y, tile.getY());

        point.setLocation(dx, dy);
    }

}
