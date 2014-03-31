package org.game.towers.handlers;

public class InputEvent {
	private InputEventType type;
	private Key key;

	public InputEvent(Key key, InputEventType type) {
		this.setKey(key);
		this.setType(type);
	}

	public InputEventType getType() {
		return type;
	}

	public void setType(InputEventType type) {
		this.type = type;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}
}