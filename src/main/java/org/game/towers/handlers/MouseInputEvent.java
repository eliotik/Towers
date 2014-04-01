package org.game.towers.handlers;

public class MouseInputEvent {
	private InputEventType type;
	private MouseKey key;

	public MouseInputEvent(MouseKey key, InputEventType type) {
		this.setKey(key);
		this.setType(type);
	}

	public InputEventType getType() {
		return type;
	}

	public void setType(InputEventType type) {
		this.type = type;
	}

	public MouseKey getKey() {
		return key;
	}

	public void setKey(MouseKey key) {
		this.key = key;
	}
}