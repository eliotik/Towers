package org.game.towers.handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.game.towers.game.Game;

public class MouseHandler implements MouseListener, MouseMotionListener {

	public MouseHandler(Game game){
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}

	public void mouseClicked(MouseEvent e) {

	}

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
    	Game.instance.getScreen().setMousePosition(new Point(e.getX(), e.getY()));
//    	System.out.println("MS x: "+e.getX()+", y:"+e.getY());
    }

	public void mouseDragged(MouseEvent e) {
		Game.instance.getScreen().setMousePosition(new Point(e.getX(), e.getY()));
	}
}
