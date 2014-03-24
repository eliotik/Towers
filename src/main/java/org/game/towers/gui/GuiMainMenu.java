package org.game.towers.gui;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.gui.elements.ChooseList;
import org.game.towers.gui.elements.FontRenderer;

public class GuiMainMenu extends Gui {

	private ChooseList list;

	public GuiMainMenu(Game game, int width, int height) {
		super(game, width, height);
		setPauseGame(true);
		setList(new ChooseList(0, this));
		getList().setMaximumDisplayed(10);
		getList().addOption(getList().new Option(0, "Create"));
		getList().addOption(getList().new Option(1, "Exit"));
	}

	public void render() {
		drawDefaultSolidBackground();
		FontRenderer.drawCenteredString("Main Menu", this, Config.SCREEN_WIDTH / 2 + 1, 5, 225, 2);
		getList().render(this, 10, 30, 225);
		FontRenderer.drawString(getSplash(), this, 2, Config.SCREEN_HEIGHT - 10, 000, 1);
	}

	public void tick(int ticks) {

	}

	public void guiActionPerformed(int elementId, int action) {
		if (elementId == getList().getId()) {
			switch (action) {
			case 0:
				getGame().initWorldLevel();
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
		getList().actionPerformed(event);
	}

	public ChooseList getList() {
		return list;
	}

	public void setList(ChooseList list) {
		this.list = list;
	}
}
