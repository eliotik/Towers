package org.game.towers.handlers;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import org.game.towers.game.Game;

public class InputHandler implements KeyListener {

	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public class Key {
		private int numTimesPressed = 0;
		public boolean pressed = false;
		
		public int getNumTimesPressed() {
			return numTimesPressed;
		}
		
		public void toggle(boolean isPressed) {
			pressed = isPressed;
			if (isPressed) {
				numTimesPressed++;
			}
		}
		
		public boolean isPressed() {
			return pressed;
		}
	}
	
	public List<Key> keys = new ArrayList<Key>();
	
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	
	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {
		toggleKey(e.getKeyCode(), true);
	}

	public void keyReleased(KeyEvent e) {
		toggleKey(e.getKeyCode(), false);
	}

	private void toggleKey(int keyCode, boolean isPressed) {
		switch (keyCode) {
			case KeyEvent.VK_W: case KeyEvent.VK_UP:
				up.toggle(isPressed);
				break;
			case KeyEvent.VK_S: case KeyEvent.VK_DOWN:
				down.toggle(isPressed);
				break;
			case KeyEvent.VK_A: case KeyEvent.VK_LEFT:
				left.toggle(isPressed);
				break;
			case KeyEvent.VK_D: case KeyEvent.VK_RIGHT:
				right.toggle(isPressed);
				break;
		}
	}
	

}
