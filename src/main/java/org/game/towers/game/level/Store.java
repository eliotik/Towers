package org.game.towers.game.level;

import java.awt.Color;

import org.game.towers.game.Config;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.gui.elements.FontRenderer;
import org.game.towers.units.collections.TowersCollection;
import org.game.towers.units.towers.Tower;

public class Store {

	private int x;
	private int y;
	private boolean visible;

	public Store() {
		setX(0);
		setY(0);
	}

	public void tick() {}

	public void render(Screen screen) {
		Sprite sprite = SpritesData.STORE;
		int xp = (screen.getWidth() - (sprite.getWidth() / 2)) - (0 << Config.COORDINATES_SHIFTING) - 8 + getX();
		int yp = screen.getHeight() - sprite.getHeight();
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yt = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xt = x + xp;
				if (0 > xt || xt >= screen.getWidth() || 0 > yt || yt >= screen.getHeight()) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}
				if (yt < 0) {
					yt = 0;
				}
				int color = sprite.getPixels()[x + y * sprite.getWidth()];
				if (color == 0xFFFF00FF || color == 0xFF800080) {
					color = SpritesData.STORE_BG_LEFT.getPixels()[x + y * SpritesData.STORE_BG_LEFT.getWidth()];
				}
				if (color != 0xFFFF00FF && color != 0xFF800080) {
					screen.getPixels()[xt + yt * screen.getWidth()] = color;
				}
			}
		}
//		int width = (screen.getWidth() * Config.SCALE) - 5 + getX()*2;
//		int height = screen.getHeight() * Config.SCALE + 7;
		int white = 555;
		int black = 000;
		int fontScale = 2;
		int yPos = Config.SCREEN_HEIGHT - sprite.getHeight();
		int yBPos = yPos + 1;
		int xPos = Config.SCREEN_WIDTH - sprite.getWidth() + 2;
		int xBPos = xPos + 1;
		String iconName = "$";
		FontRenderer.drawString(iconName, xBPos, yBPos, black, fontScale);
		FontRenderer.drawString(iconName, xPos, yPos, white, fontScale);
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
}
