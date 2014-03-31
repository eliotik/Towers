package org.game.towers.handlers;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import org.game.towers.game.Game;

public class MouseHandler implements MouseListener, MouseMotionListener {

	private boolean mouseBtnHolded = false;

	private MouseKey[] keys = new MouseKey[3];
	private MouseKey left;
	private MouseKey right;
	private MouseKey center;

	private List<GameActionListener> listeners = new ArrayList<GameActionListener>();
	private List<GameActionListener> newListeners = new ArrayList<GameActionListener>();

	private boolean actionPerformed;

	public MouseHandler(Game game){
		setLeft(new MouseKey(this));
		setRight(new MouseKey(this));
		setCenter(new MouseKey(this));
		game.addMouseListener(this);
		game.addMouseMotionListener(this);
	}

	public void tick() {
		for (int i = 0; i < getKeys().length; i++) {
			if (getKeys()[i] != null) {
				getKeys()[i].update();
			}
		}
		if (isActionPerformed()) {
			notifyListeners();
			setActionPerformed(false);
		}
	}

	private void notifyListeners() {
		for (int j = 0; j < getKeys().length; j++) {
			if (getKeys()[j].gotPressed()) {
				for (int i = 0; i < getListeners().size(); i++) {
					if(!getNewListeners().contains(getListeners().get(i))) {
						getListeners().get(i).actionPerformed(new MouseInputEvent(getKeys()[j], InputEventType.PRESSED));
					} else {
						getNewListeners().remove(getListeners().get(i));
					}
				}
			}
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

    public void mousePressed(MouseEvent e) {
    	setMouseBtnHolded(true);
    }

    public void mouseReleased(MouseEvent e) {
    	setMouseBtnHolded(false);
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
    	//System.out.println((e.getX()/Config.SCALE) +" : "+ (e.getY()/Config.SCALE) + " | " + Game.getInstance().getScreen().getxOffset()+" : "+Game.getInstance().getScreen().getyOffset());
    	Game.getInstance().getScreen().setMousePosition(new Point(e.getX(), e.getY()));
    }

	public void mouseDragged(MouseEvent e) {
		Game.getInstance().getScreen().setMousePosition(new Point(e.getX(), e.getY()));
	}

	public boolean isMouseBtnHolded() {
		return mouseBtnHolded;
	}

	public void setMouseBtnHolded(boolean mouseBtnHolded) {
		this.mouseBtnHolded = mouseBtnHolded;
	}

	public MouseKey[] getKeys() {
		return keys;
	}

	public void setKeys(MouseKey[] keys) {
		this.keys = keys;
	}

	public MouseKey getLeft() {
		return left;
	}

	public void setLeft(MouseKey left) {
		this.left = left;
	}

	public MouseKey getRight() {
		return right;
	}

	public void setRight(MouseKey right) {
		this.right = right;
	}

	public MouseKey getCenter() {
		return center;
	}

	public void setCenter(MouseKey center) {
		this.center = center;
	}

	public List<GameActionListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<GameActionListener> listeners) {
		this.listeners = listeners;
	}

	public List<GameActionListener> getNewListeners() {
		return newListeners;
	}

	public void setNewListeners(List<GameActionListener> newListeners) {
		this.newListeners = newListeners;
	}

	public boolean isActionPerformed() {
		return actionPerformed;
	}

	public void setActionPerformed(boolean actionPerformed) {
		this.actionPerformed = actionPerformed;
	}
}
