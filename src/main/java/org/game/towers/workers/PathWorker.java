package org.game.towers.workers;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Portals;
import org.game.towers.game.level.tiles.Tile;
import org.game.towers.workers.Algorithms.JumpPointSearch.JPS;
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;
import org.game.towers.workers.geo.Coordinates;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PathWorker {

    private volatile HashMap<Integer, ArrayList<Node>> trailMap = new HashMap<Integer, ArrayList<Node>>();
    private volatile HashMap<Integer, ArrayList<Node>> visitedNodesMap = new HashMap<Integer, ArrayList<Node>>();
    private int steps = 0;
    private volatile JPS jps;


    /*private int doShift(int dimension1, int dimension2) {
        int deltaDim = dimension2 - dimension1;
        if (deltaDim > 0){
            return 1;
        }
        if (deltaDim < 0){
            return -1;
        }

        return 0;
    }*/

    private void initJPS() {
        if (jps == null){
            this.jps = new JPS();
        }
    }

    public synchronized void nextCoordinate(int x, int y, Point point, int id) {
        int dx, dy;
        initJPS();
        ArrayList<Node> temporaryTrail = null;
        Node nextNode;
        ArrayList<Node> trailNodes;
        ArrayList<Node> visitedNodes = new ArrayList<Node>();

        if (trailMap.get(id) == null) {
            trailNodes = jps.search(x, y, id);
            trailMap.put(id, trailNodes);
        }

        trailNodes = trailMap.get(id);

        if (trailNodes == null || trailNodes.size() == 0) {
            point.setLocation(0, 0);
            return;
        }

        if (visitedNodesMap.get(id) == null) {
            visitedNodes.add(trailNodes.get(0));
            visitedNodesMap.put(id, visitedNodes);
        }

        visitedNodes = visitedNodesMap.get(id);

        nextNode = trailNodes.get(visitedNodes.size());
        if (Config.LEVEL_HIGHLIGHT_PATH_TILES) {
        	Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).setHighlight(0.8);
        }
        int nextX = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getX()*Config.BOX_SIZE;
        int nextY = Game.instance.getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getY()*Config.BOX_SIZE;
        if (nextX == x && nextY == y) {
            visitedNodes.add(nextNode);
            visitedNodesMap.put(id, visitedNodes);
        }

        dx = Utils.doShift(x, nextX);
        dy = Utils.doShift(y, nextY);

        point.setLocation(dx, dy);
    }

}
