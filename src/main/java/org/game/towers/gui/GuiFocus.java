package org.game.towers.gui;

import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.gui.elements.FontRenderer;

public class GuiFocus extends Gui {

	public GuiFocus(Game game, int width, int height) {
		super(game, width, height);
		setPauseGame(true);
	}

	@Override
	public void actionPerformed(InputEvent event) {}

	@Override
	public void render() {
		drawDefaultBackground();
		FontRenderer.drawCenteredString("Focus Lost!", this, getWidth() / 2, 50,
				444, 4);
		FontRenderer.drawCenteredString("Click to focus...", this, getWidth() / 2,
				80, 555, 1);
	}

	@Override
	public void tick(int ticks) {

	}

	@Override
	public void guiActionPerformed(int elementId, int action) {

	}
}
