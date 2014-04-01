package org.game.towers.game.level;


import java.awt.Point;
import java.awt.Rectangle;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.handlers.GameActionListener;
import org.game.towers.handlers.InputEvent;
import org.game.towers.handlers.MouseInputEvent;
import org.game.towers.workers.Utils;

public class Store implements GameActionListener{

	private int x;
	private int y;
	private boolean visible;
	private Rectangle collisionBox = new Rectangle();
	private int scale = 1;
	private double highlight = 1;
	private Screen screen;

	public Store(Screen screen) {
		setScreen(screen);
		setVisible(true);
		setX((getScreen().getWidth() - (SpritesData.STORE.getWidth() / 2)) - (0 << Config.COORDINATES_SHIFTING) - 10);
		setY(getScreen().getHeight() - SpritesData.STORE.getHeight());
		getCollisionBox().setRect(getX(), getY(), SpritesData.STORE.getWidth(), SpritesData.STORE.getHeight());
		Game.getInstance().getMouseHandler().addListener(this, true);
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
		getScreen().renderIcon(SpritesData.STORE, getX(), getY(), getScale(), getHighlight());
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

	@Override
	public void actionPerformed(InputEvent event) {}

	@Override
	public void actionPerformed(MouseInputEvent event) {
		switch(event.getType()) {
		case CLICKED:
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getLeft().getId()) {
				System.out.println("mouse left clicked: "+event.getType());
				break;
			}
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getRight().getId()) {
				System.out.println("mouse right clicked: "+event.getType());
				break;
			}
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getCenter().getId()) {
				System.out.println("mouse center clicked: "+event.getType());
				break;
			}
			break;
		case PRESSED:
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getLeft().getId()) {
				System.out.println("mouse left pressed: "+event.getType());
				break;
			}
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getRight().getId()) {
				System.out.println("mouse right pressed: "+event.getType());
				break;
			}
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getCenter().getId()) {
				System.out.println("mouse center pressed: "+event.getType());
				break;
			}
			break;
		case RELEASED:
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getLeft().getId()) {
				System.out.println("mouse left released: "+event.getType());
				break;
			}
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getRight().getId()) {
				System.out.println("mouse right released: "+event.getType());
				break;
			}
			if (event.getKey().getId() == Game.getInstance().getMouseHandler().getCenter().getId()) {
				System.out.println("mouse center released: "+event.getType());
				break;
			}
			break;
		default:
			break;
		}
	}
}
