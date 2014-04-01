package org.game.towers.handlers;

public class Key {

	private int id;
	private boolean isPressed;
	private boolean lastState;
	private boolean gotPressed;
	private InputHandler handler;

	public Key(InputHandler handler) {
		setHandler(handler);
		for (int i = 0; i < handler.getKeys().length; i++) {
			if (handler.getKeys()[i] == null) {
				setId(i);
				handler.getKeys()[i] = this;
				break;
			}
		}
	}

	public void toggle(boolean pressed) {
		setPressed(pressed);
	}

	public void update() {
		if (isLastState() != isPressed() && isPressed() == true) {
			setGotPressed(true);
			setLastState(isPressed());
		} else {
			setGotPressed(false);
			setLastState(isPressed());
		}
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean gotPressed() {
		return isGotPressed();
	}

	public InputHandler getHandler() {
		return handler;
	}

	public void setHandler(InputHandler handler) {
		this.handler = handler;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

	public boolean isLastState() {
		return lastState;
	}

	public void setLastState(boolean lastState) {
		this.lastState = lastState;
	}

	public boolean isGotPressed() {
		return gotPressed;
	}

	public void setGotPressed(boolean gotPressed) {
		this.gotPressed = gotPressed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}