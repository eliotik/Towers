package org.game.towers.gui;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler;
import org.game.towers.handlers.InputHandler.GameActionListener;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.handlers.InputHandler.InputEventType;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.sprites.SpriteSheet;

public abstract class Gui implements GameActionListener {

	private static int DEFAULT_BKG_COLOR = 0x555555;
	private double red;
	private double green;
	private double blue;

	protected Gui parentGui;
	protected Game game;
	public InputHandler input;

	public SpriteSheet font;

	public int[] pixels;
	public int[] bkgPixels;
	public int width;
	public int height;

	protected boolean pauseGame;
	protected boolean closeOnEscape = true;

	public Gui(Game game, int width, int height) {
		this.game = game;
		this.input = game.getInputHandler();
		this.font = new SpriteSheet(Config.SPRITESHEET_FILE);
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		this.setTint(0.3D, 0.3D, 0.3D);
		if (game.getWorld() != null && game.getScreen().getPixels().length == Game.instance.getScreen().getPixels().length) {
			bkgPixels = game.getPixels().clone();
		}
	}

	public abstract void render();

	public abstract void tick(int ticks);

	public abstract void guiActionPerformed(int elementId, int action);

	public void setParentGui(Gui gui) {
		this.parentGui = gui;
	}


	public boolean pausesGame() {
		return pauseGame;
	}

	public void drawDefaultBackground() {
		if (bkgPixels != null) {
			for (int i = 0; i < bkgPixels.length; i++) {
				Game.instance.getScreen().getPixels()[i] = Colors.tint(bkgPixels[i], red, green, blue);
			}
		} else {
			for (int i = 0; i < Game.instance.getScreen().getPixels().length; i++) {
				Game.instance.getScreen().getPixels()[i] = DEFAULT_BKG_COLOR;
			}
		}
	}

//	public void drawRect(int xPos, int yPos, int width, int height, int color) {
//		if (xPos > this.width)
//			xPos = this.width - 1;
//		if (yPos > this.height)
//			yPos = this.height - 1;
//		if (xPos + width > this.width)
//			width = this.width - xPos;
//		if (yPos + height > this.height)
//			height = this.height - yPos;
//		width -= 1;
//		height -= 1;
//		for (int x = xPos; x < xPos + width; x++) {
//			pixels[x + yPos * this.width] = color;
//		}
//		for (int y = yPos; y < yPos + height; y++) {
//			pixels[xPos + y * this.width] = color;
//		}
//		for (int x = xPos; x < xPos + width; x++) {
//			pixels[x + (yPos + height) * this.width] = color;
//		}
//		for (int y = yPos; y < yPos + height; y++) {
//			pixels[(xPos + width) + y * this.width] = color;
//		}
//	}
//
//	public void fillRect(int xPos, int yPos, int width, int height, int color) {
//		if (xPos > this.width)
//			xPos = this.width;
//		if (yPos > this.height)
//			yPos = this.height;
//		if (xPos + width > this.width)
//			width = this.width - xPos;
//		if (yPos + height > this.height)
//			height = this.height - yPos;
//		for (int y = yPos; y < yPos + height; y++) {
//			for (int x = xPos; x < xPos + width; x++) {
//				pixels[x + y * this.width] = color;
//			}
//		}
//	}

	@Override
	public void actionPerformed(InputEvent event) {
		if (event.key.id == input.esc.id
				&& event.type == InputEventType.PRESSED && closeOnEscape) {
			last();
		}
	}

	public void setTint(double r, double g, double b) {
		red = r;
		green = g;
		blue = b;
	}

	public Gui setParent(Gui gui) {
		this.parentGui = gui;
		return this;
	}

	public void last() {
		game.hideGui(this);
	}

	public void close() {
		this.parentGui = null;
		game.hideGui(this);
	}

	public Gui getParentGui() {
		return parentGui;
	}
}
