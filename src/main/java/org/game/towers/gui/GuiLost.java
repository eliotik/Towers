package org.game.towers.gui;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.gui.elements.ChooseList;
import org.game.towers.gui.elements.FontRenderer;
import org.game.towers.handlers.InputHandler.InputEvent;

public class GuiLost extends Gui {

	int selectedEntry = 0;
	private ChooseList list;

	public GuiLost(Game game, int width, int height) {
		super(game, width, height);
		setTint(0.5D, 0.2D, 0.2D);
		setPauseGame(true);
		setList(new ChooseList(0, this));
		getList().addOption(getList().new Option(0, "Main Menu"));
		getList().addOption(getList().new Option(1, "Exit"));
	}

	public void render() {
		drawDefaultBackground();
		drawRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_WIDTH, 0xFF0000);

		FontRenderer.drawCenteredString("YOU LOST!", this, Config.SCREEN_WIDTH / 2 + 2, 20, 500, 2);
		FontRenderer.drawString(getSplash(), this, 2, Config.SCREEN_HEIGHT - 10, 000, 1);
		getList().renderCentered(this, Config.SCREEN_WIDTH / 2, Config.SCREEN_HEIGHT / 2, 444);

//		FontRenderer.drawCenteredString("Paused", this, Config.SCREEN_WIDTH / 2 + 2, 5, 222, 2);
//		FontRenderer.drawString(splash, this, 2, Config.SCREEN_HEIGHT - 10, 000, 1);
//		list.render(this, 20, 30, 555);
	}

	public void tick(int ticks) {

	}

	public void actionPerformed(InputEvent event) {
		getList().actionPerformed(event);
	}

	public void guiActionPerformed(int elementId, int action) {
		if (elementId == getList().getId()) {
			switch (action) {
			case 0:
				System.out.println("Main Menu");
				last();
				getGame().showGui(new GuiMainMenu(getGame(), getGame().getWidth(), getGame().getHeight()));
				break;
			case 1:
				System.out.println("The game has been quit!");
				System.exit(0);
				break;
			}
		}
	}

	public ChooseList getList() {
		return list;
	}

	public void setList(ChooseList list) {
		this.list = list;
	}
}