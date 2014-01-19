package org.game.towers.gui;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.game.World;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.gui.elements.ChooseList;
import org.game.towers.gui.elements.FontRenderer;

public class GuiMainMenu extends Gui {

	public ChooseList list;
	private String splash = Config.GAME_NAME;

	public GuiMainMenu(Game game, int width, int height) {
		super(game, width, height);
		this.pauseGame = true;
		list = new ChooseList(0, this);
		list.setMaximumDisplayed(10);
		list.addOption(list.new Option(0, "Create"));
		list.addOption(list.new Option(1, "Exit"));
	}

	public void render() {
		this.drawDefaultBackground();
		FontRenderer.drawCenteredString("Main Menu", this, width / 2 + 1, 5, 225, 2);
		list.render(this, 10, 30, 225);
		FontRenderer.drawString(splash, this, 2, height - 10, 000, 1);
	}

	public void tick(int ticks) {

	}

	public void guiActionPerformed(int elementId, int action) {
		if (elementId == list.getId()) {
			switch (action) {
			case 0:
				game.setWorld(new World(Config.DEFAULT_WORLD_NAME));
				close();
				break;
			case 1:
				System.out.println("The game has been quit!");
				System.exit(0);
				break;
			}
		}
	}

	public void actionPerformed(InputEvent event) {
		list.actionPerformed(event);
	}
}
