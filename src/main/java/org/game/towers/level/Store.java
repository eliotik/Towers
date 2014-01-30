package org.game.towers.level;

import java.awt.Rectangle;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Screen;

public class Store {

	private static final int STORE_SLOTS = 5;

	private StoreSlot[] slots = new StoreSlot[STORE_SLOTS];

	public Store() {
		init();
	}

	public class StoreSlot {
		private int id;
		private boolean mouseOver = false;
		private int scale = 1;
		private int x = 0;
		private int y = 0;
		private int currentColor;
		private int mouseOutColor;
		private int mouseOverColor;
		private int width = Config.BOX_SIZE * 2;
		private int height = Config.BOX_SIZE * 2;

		public StoreSlot(int id) {
			setId(id);
			mouseOutColor = Colors.get(500, 111, 200, 543);
			mouseOverColor = Colors.get(500, 300, 200, 100);
			setX((id+7) * Config.BOX_SIZE*2 + (id*2));
			setY(Config.SCREEN_HEIGHT - Config.BOX_SIZE);
		}

		public void tick() {
			Rectangle slot = new Rectangle(getX()*Config.SCALE, getY()*Config.SCALE, (getWidth()-(id*2) - 3)*Config.SCALE, getHeight()*Config.SCALE);
			setMouseOver(slot.contains(Game.instance.getScreen().getMousePosition()));
			currentColor = mouseOutColor;
			if (isMouseOver()) {
				currentColor = mouseOverColor;
			}
		}

		public void render(Screen screen) {
			int xTile = 0;
			int yTile = 12;

			int modifier = 8 * scale;
			int xOffset = getX() - modifier / 2;
			int yOffset = getY() - modifier / 2 - 4;

//			screen.render(xOffset, yOffset, xTile + yTile * 32, currentColor);
//			screen.render(xOffset + modifier, yOffset, (xTile + 1) + yTile * 32, currentColor);
//			screen.render(xOffset, yOffset + modifier, xTile + (yTile + 1) * 32, currentColor);
//			screen.render(xOffset + modifier, yOffset + modifier, (xTile + 1) + (yTile + 1) * 32, currentColor);
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public boolean isMouseOver() {
			return mouseOver;
		}

		public void setMouseOver(boolean mouseOver) {
			this.mouseOver = mouseOver;
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

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
	}


	private void init() {
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new StoreSlot(i);
		}
	}

	public void tick() {
		for (int i = 0; i < slots.length; i++) {
			slots[i].tick();
		}
	}

	public void render(Screen screen) {
		for (int i = 0; i < slots.length; i++) {
			slots[i].render(screen);
		}
	}
}
