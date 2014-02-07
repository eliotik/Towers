package org.game.towers.level;

import java.awt.Color;
import java.awt.Rectangle;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.towers.TowerType;
import org.game.towers.towers.TowerTypesCollection;

public class Store {

	private int x;
	private int y;
	private boolean visible;

	public Store() {
		setX(TowerTypesCollection.getItems().size() * Config.BOX_SIZE - 3);
		setY(0);
	}

	public void tick() {
		setX(0);
	}

	public void render(Screen screen) {
		for (int i = 0; i < TowerTypesCollection.getItems().size(); i++) {
			TowerType tower = TowerTypesCollection.getItems().get(i);
			Sprite sprite = tower.getCurrentSprite();
			int xp = (screen.getWidth() - (sprite.getWidth() / 2)) - (i << 4) - 8 + getX();
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
						if (i == TowerTypesCollection.getItems().size() - 1) {
							color = SpritesData.STORE_BG_LEFT.getPixels()[x + y * SpritesData.STORE_BG_LEFT.getWidth()];
						} else {
							color = SpritesData.STORE_BG_MIDDLE.getPixels()[x + y * SpritesData.STORE_BG_MIDDLE.getWidth()];
						}
					}
					if (color != 0xFFFF00FF && color != 0xFF800080) {
						screen.getPixels()[xt + yt * screen.getWidth()] = color;
					}
				}
			}
			int width = (screen.getWidth() * Config.SCALE - i * 33) - 5 + getX()*2;
			int height = screen.getHeight() * Config.SCALE + 7;
			screen.drawCenteredString(tower.getPrice() + "", width - 1, height - 1, 12, 1, Color.BLACK);
			screen.drawCenteredString(tower.getPrice() + "", width, height, 12,	1, Color.WHITE);
		}
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
