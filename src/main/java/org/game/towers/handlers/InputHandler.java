package org.game.towers.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import org.game.towers.game.Game;

public class InputHandler implements KeyListener {

	private Key[] keys = new Key[6];
	private Key up;
	private Key down;
	private Key left;
	private Key right;
	private Key action;
	private Key esc;

	private List<GameActionListener> listeners = new ArrayList<GameActionListener>();
	private List<GameActionListener> newListeners = new ArrayList<GameActionListener>();

	private boolean actionPerformed;

	public InputHandler(Game game) {
		setUp(new Key(this));
		setDown(new Key(this));
		setLeft(new Key(this));
		setRight(new Key(this));
		setAction(new Key(this));
		setEsc(new Key(this));
		game.addKeyListener(this);
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
						getListeners().get(i).actionPerformed(new InputEvent(getKeys()[j], InputEventType.PRESSED));
					} else {
						getNewListeners().remove(getListeners().get(i));
					}
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	public void toggleKey(int keyCode, boolean pressed) {
		setActionPerformed(true);
		if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
			getUp().toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
			getDown().toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_A || keyCode == KeyEvent.VK_LEFT) {
			getLeft().toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_RIGHT) {
			getRight().toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_E || keyCode == KeyEvent.VK_ENTER) {
			getAction().toggle(pressed);
		}
		if (keyCode == KeyEvent.VK_ESCAPE) {
			getEsc().toggle(pressed);
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void addListener(GameActionListener listener) {
		addListener(listener, false);
//		newListeners.add(listener);
//		listeners.add(listener);
	}

	public void removeListener(GameActionListener listener) {
		getListeners().remove(listener);
	}

	public boolean isKeyPressed(Key key) {
		return key.isPressed();
	}

	public void addListener(GameActionListener listener, boolean onlyListeners) {
		if (!onlyListeners) {
			getNewListeners().add(listener);
		}
		getListeners().add(listener);
	}

	public Key[] getKeys() {
		return keys;
	}

	public void setKeys(Key[] keys) {
		this.keys = keys;
	}

	public Key getUp() {
		return up;
	}

	public void setUp(Key up) {
		this.up = up;
	}

	public Key getDown() {
		return down;
	}

	public void setDown(Key down) {
		this.down = down;
	}

	public Key getLeft() {
		return left;
	}

	public void setLeft(Key left) {
		this.left = left;
	}

	public Key getRight() {
		return right;
	}

	public void setRight(Key right) {
		this.right = right;
	}

	public Key getAction() {
		return action;
	}

	public void setAction(Key action) {
		this.action = action;
	}

	public Key getEsc() {
		return esc;
	}

	public void setEsc(Key esc) {
		this.esc = esc;
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
