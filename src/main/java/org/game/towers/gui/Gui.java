package org.game.towers.gui;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler;
import org.game.towers.handlers.GameActionListener;
import org.game.towers.handlers.InputEvent;
import org.game.towers.handlers.InputEventType;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.sprites.SpriteSheet;

public abstract class Gui implements GameActionListener {

	private double red;
	private double green;
	private double blue;

	protected Gui parentGui;
	private Game game;
	private InputHandler input;

	private SpriteSheet font;

	private int[] pixels;
	private int[] bkgPixels;
	private int width;
	private int height;

	private boolean pauseGame;
	protected boolean closeOnEscape = true;

	private String splash = Config.GAME_NAME;

	public Gui(Game game, int width, int height) {
		this.setGame(game);
		this.setInput(game.getInputHandler());
		this.setFont(new SpriteSheet(Config.SPRITESHEET_FILE));
		this.setWidth(width);
		this.setHeight(height);
		this.setPixels(new int[width * height]);
		this.setTint(0.3D, 0.3D, 0.3D);
		if (game.getWorld() != null && game.getScreen().getPixels().length == Game.getInstance().getScreen().getPixels().length) {
			setBkgPixels(game.getPixels().clone());
		}
	}

	public abstract void render();

	public abstract void tick(int ticks);

	public abstract void guiActionPerformed(int elementId, int action);

	public void setParentGui(Gui gui) {
		this.parentGui = gui;
	}


	public boolean pausesGame() {
		return isPauseGame();
	}

	public void drawDefaultBackground() {
		if (getBkgPixels() != null) {
			for (int i = 0; i < getBkgPixels().length; i++) {
				Game.getInstance().getScreen().getPixels()[i] = Colors.tint(getBkgPixels()[i], red, green, blue);
			}
		} else {
			drawDefaultSolidBackground();
		}
	}

	public void drawDefaultSolidBackground() {
		for (int i = 0; i < Game.getInstance().getScreen().getPixels().length; i++) {
			Game.getInstance().getScreen().getPixels()[i] = Config.DEFAULT_BKG_COLOR;
		}
	}

	public void drawRect(int xPos, int yPos, int width, int height, int color) {
		if (xPos > getWidth())
			xPos = getWidth() - 1;
		if (yPos > getHeight())
			yPos = getHeight() - 1;
		if (xPos + width > getWidth())
			width = getWidth() - xPos;
		if (yPos + height > getHeight())
			height = getHeight() - yPos;
		width -= 1;
		height -= 1;
		for (int x = xPos; x < xPos + width; x++) {
			getPixels()[x + yPos * getWidth()] = color;
		}
		for (int y = yPos; y < yPos + height; y++) {
			getPixels()[xPos + y * getWidth()] = color;
		}
		for (int x = xPos; x < xPos + width; x++) {
			getPixels()[x + (yPos + height) * getWidth()] = color;
		}
		for (int y = yPos; y < yPos + height; y++) {
			getPixels()[(xPos + width) + y * getWidth()] = color;
		}
	}
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
		if (event.getKey().getId() == getInput().getEsc().getId()
				&& event.getType() == InputEventType.PRESSED && closeOnEscape) {
			last();
		}
	}

	public void setTint(double r, double g, double b) {
		red = r;
		green = g;
		blue = b;
	}

//	public Gui setParent(Gui gui) {
//		this.parentGui = gui;
//		return this;
//	}

	public void last() {
		getGame().hideGui(this);
	}

	public void close() {
		this.parentGui = null;
		getGame().hideGui(this);
	}

	public Gui getParentGui() {
		return parentGui;
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

	public InputHandler getInput() {
		return input;
	}

	public void setInput(InputHandler input) {
		this.input = input;
	}

	public SpriteSheet getFont() {
		return font;
	}

	public void setFont(SpriteSheet font) {
		this.font = font;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public int[] getBkgPixels() {
		return bkgPixels;
	}

	public void setBkgPixels(int[] bkgPixels) {
		this.bkgPixels = bkgPixels;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public boolean isPauseGame() {
		return pauseGame;
	}

	public void setPauseGame(boolean pauseGame) {
		this.pauseGame = pauseGame;
	}

	public String getSplash() {
		return splash;
	}

	public void setSplash(String splash) {
		this.splash = splash;
	}
}
