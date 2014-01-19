package org.game.towers.gui;

import org.game.towers.configs.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.gui.elements.ChooseList;
import org.game.towers.gui.elements.FontRenderer;

public class GuiPause extends Gui{

	private ChooseList list;
	private String splash = Config.GAME_NAME;
	
	public GuiPause(Game game, int width, int height) {
		super(game, width, height);
		this.pauseGame = true;
		list = new ChooseList(0, this);
		list.addOption(list.new Option(0, "Resume"));
		list.addOption(list.new Option(1, "Exit"));
	}

	public void actionPerformed(InputEvent event) {
		list.actionPerformed(event);
	}

	public void render() {
		this.drawDefaultBackground();
		FontRenderer.drawCenteredString("Paused", this, width / 2 + 2, 5, 222, 2);
		FontRenderer.drawString(splash, this, 2, height - 10, 000, 1);
		list.render(this, 20, 30, 555);
	}

	public void tick(int ticks) {
		
	}

	public void guiActionPerformed(int elementId, int action) {
		if(elementId == list.getId()) {
			switch (action) {
			case 0 :
				close();
				break;
			case 1:
				System.out.println("The game has been quit!");
				close();
				System.exit(0);
				break;
			}
		}
	}

}
