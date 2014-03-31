package org.game.towers.gui;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gui.elements.FontRenderer;
import org.game.towers.handlers.MouseInputEvent;

public class GuiStore extends Gui {


	public GuiStore(Game game, int width, int height) {
		super(game, width, height);
		setPauseGame(true);

	}

	public void render() {
		drawDefaultBackground();
		FontRenderer.drawCenteredString("Store", this, Config.SCREEN_WIDTH / 2 + 1, 5, 225, 2);
		FontRenderer.drawCenteredString("Buy towers, buildings", this, Config.SCREEN_WIDTH / 2 + 1, 5, 225, 1);
		FontRenderer.drawString(getSplash(), this, 2, Config.SCREEN_HEIGHT - 10, 000, 1);
	}

	public void tick(int ticks) {}
	public void guiActionPerformed(int elementId, int action) {}

	@Override
	public void actionPerformed(MouseInputEvent event) {}
}
