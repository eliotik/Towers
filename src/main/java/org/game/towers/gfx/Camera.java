package org.game.towers.gfx;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler;
import org.game.towers.level.Level;

public class Camera {

	private Level level;
	private int x;
	private int y;
	private InputHandler input;
	private int speed;

	public Camera(Level level, int x, int y, InputHandler input, int speed) {
		setLevel(level);
		setX(x);
		setY(y);
		setInput(input);
		setSpeed(speed);
	}

	public void tick() {
		int xa = 0;
		int ya = 0;
		if (getInput().left.isPressed()) {
			xa--;
		}
		if (getInput().right.isPressed()) {
			xa++;
		}
		if (getInput().up.isPressed()) {
			ya--;
		}
		if (getInput().down.isPressed()) {
			ya++;
		}

		setX(getX() + xa * getSpeed());
		setY(getY() + ya * getSpeed());

		if (getX() < 0) {
			setX(0);
		}
		if (getX() > (getLevel().width << 3) - Game.instance.getScreen().width) {
			setX((getLevel().width << 3) - Game.instance.getScreen().width);
		}
		if (getY() < 0) {
			setY(0);
		}
		if (getY() > (getLevel().height << 3) - Game.instance.getScreen().height) {
			setY((getLevel().height << 3) - Game.instance.getScreen().height);
		}
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public InputHandler getInput() {
		return input;
	}

	public void setInput(InputHandler input) {
		this.input = input;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
