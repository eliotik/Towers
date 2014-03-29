package org.game.towers.workers;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.workers.Algorithms.JumpPointSearch.JPS;
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;

import java.awt.*;
import java.util.*;

public class PathWorker {

    private volatile HashMap<Integer, ArrayList<Node>> trailMap = new HashMap<Integer, ArrayList<Node>>();
    private volatile HashMap<Integer, ArrayList<Node>> visitedNodesMap = new HashMap<Integer, ArrayList<Node>>();
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
        if (getJps() == null){
            setJps(new JPS());
        }
    }

    public synchronized void nextCoordinate(int x, int y, Point point, int id) {
        int dx, dy;
        initJPS();
        Node nextNode;
        ArrayList<Node> trailNodes;
        ArrayList<Node> visitedNodes = new ArrayList<Node>();

        if (getTrailMap().get(id) == null) {
            trailNodes = getJps().search(x, y, id);
            getTrailMap().put(id, trailNodes);
        }

        trailNodes = getTrailMap().get(id);

        if (trailNodes == null || trailNodes.size() == 0) {
            point.setLocation(0, 0);
            return;
        }

        if (getVisitedNodesMap().get(id) == null) {
            visitedNodes.add(trailNodes.get(0));
            getVisitedNodesMap().put(id, visitedNodes);
        }

        visitedNodes = getVisitedNodesMap().get(id);

        nextNode = trailNodes.get((trailNodes.size()==visitedNodes.size()) ? visitedNodes.size()-1 : ((visitedNodes.size() > trailNodes.size())?trailNodes.size()-1:visitedNodes.size()-1));
        if (Config.LEVEL_HIGHLIGHT_PATH_TILES) {
        	Game.getInstance().getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).setHighlight(0.8);
        }
        int nextX = Game.getInstance().getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getX()*Config.BOX_SIZE;
        int nextY = Game.getInstance().getWorld().getLevel().getTile(nextNode.getX()>>4, nextNode.getY()>>4).getY()*Config.BOX_SIZE;
        if (nextX == x && nextY == y) {
            visitedNodes.add(nextNode);
            getVisitedNodesMap().put(id, visitedNodes);
        }

        dx = Utils.doShift(x, nextX);
        dy = Utils.doShift(y, nextY);

        point.setLocation(dx, dy);
    }

	public JPS getJps() {
		return jps;
	}

	public void setJps(JPS jps) {
		this.jps = jps;
	}

	public HashMap<Integer, ArrayList<Node>> getTrailMap() {
		return trailMap;
	}

	public void setTrailMap(HashMap<Integer, ArrayList<Node>> trailMap) {
		this.trailMap = trailMap;
	}

	public HashMap<Integer, ArrayList<Node>> getVisitedNodesMap() {
		return visitedNodesMap;
	}

	public void setVisitedNodesMap(HashMap<Integer, ArrayList<Node>> visitedNodesMap) {
		this.visitedNodesMap = visitedNodesMap;
	}

}
