package org.game.towers.handlers;

public class MouseKey {

	private int id;
	private InputEventType state;
	private InputEventType lastState;
	private MouseHandler handler;

	public MouseKey(MouseHandler handler) {
		setHandler(handler);
		for (int i = 0; i < handler.getKeys().length; ++i) {
			if (handler.getKeys()[i] == null) {
				setId(i);
				handler.getKeys()[i] = this;
				break;
			}
		}
	}

	public void toggle(InputEventType state) {
		switch(state) {
		case CLICKED:
			System.out.println("CLICKED");
			setState(state);
			break;
		case PRESSED:
			System.out.println("PRESSED");
			setState(state);
			break;
		case RELEASED:
			System.out.println("RELEASED");
			setState(state);
			break;
		default:
		case IDLE:
			setState(InputEventType.IDLE);
			break;
		}
	}

	public void update() {
		if (getLastState() == getState()) {
			setState(InputEventType.IDLE);
			setLastState(InputEventType.IDLE);
		} else if (getLastState() != getState()) {
			setLastState(getState());
		}
	}

	public MouseHandler getHandler() {
		return handler;
	}

	public void setHandler(MouseHandler handler) {
		this.handler = handler;
	}
	public InputEventType getLastState() {
		return lastState;
	}

	public void setLastState(InputEventType state) {
		this.lastState = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public InputEventType getState() {
		return state;
	}

	public void setState(InputEventType state) {
		this.state = state;
	}

	public boolean gotPressed() {
		if (getState() == InputEventType.CLICKED || getState() == InputEventType.RELEASED) {
			return true;
		}
		return false;
	}
}