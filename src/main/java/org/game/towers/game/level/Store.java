package org.game.towers.game.level;


import java.awt.Point;
import java.awt.Rectangle;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.workers.Utils;

public class Store {

	private int x;
	private int y;
	private boolean visible;
	private Rectangle collisionBox = new Rectangle();
	private int scale = 1;
	private double highlight = 1;
	private Screen screen;

	public Store(Screen screen) {
		setX(0);
		setY(0);
		setScreen(screen);
		setVisible(true);
	}

	public void tick() {
		Point mousePosition = Game.getInstance().getScreen().getMousePosition();
		mousePosition = Utils.transformMousePositionToScreen(mousePosition.getX(), mousePosition.getY());
		int box = Config.BOX_SIZE/4;
		if (getCollisionBox().intersects(new Rectangle((int)mousePosition.getX(), (int)mousePosition.getY(), box, box))) {
			setHighlight(1.15);
		} else {
			setHighlight(1);
		}
	}

	public void render() {
		if (!isVisible()) return;
		Sprite sprite = SpritesData.STORE;
		int xp = (getScreen().getWidth() - (sprite.getWidth() / 2)) - (0 << Config.COORDINATES_SHIFTING) - 10 + getX();
		int yp = getScreen().getHeight() - sprite.getHeight();
		getScreen().renderIcon(sprite, xp, yp, getScale(), getHighlight());
		getCollisionBox().setRect(xp, yp, SpritesData.STORE_BG_LEFT.getWidth(), SpritesData.STORE_BG_LEFT.getHeight());
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

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Rectangle getCollisionBox() {
		return collisionBox;
	}

	public void setCollisionBox(Rectangle collisionBox) {
		this.collisionBox = collisionBox;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public double getHighlight() {
		return highlight;
	}

	public void setHighlight(double highlight) {
		this.highlight = highlight;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
}
