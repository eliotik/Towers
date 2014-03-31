package org.game.towers.gui;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.handlers.InputEvent;
import org.game.towers.handlers.MouseInputEvent;
import org.game.towers.gui.elements.ChooseList;
import org.game.towers.gui.elements.FontRenderer;

public class GuiPause extends Gui{

	private ChooseList list;

	public GuiPause(Game game, int width, int height) {
		super(game, width, height);
		setPauseGame(true);
		setList(new ChooseList(0, this));
		getList().addOption(getList().new Option(0, "Resume"));
		getList().addOption(getList().new Option(1, "Restart"));
		getList().addOption(getList().new Option(2, "Exit"));
	}

	public void actionPerformed(InputEvent event) {
		getList().actionPerformed(event);
	}

	public void render() {
		drawDefaultBackground();
		FontRenderer.drawCenteredString("Paused", this, Config.SCREEN_WIDTH / 2 + 2, 5, 222, 2);
		FontRenderer.drawString(getSplash(), this, 2, Config.SCREEN_HEIGHT - 10, 000, 1);
		getList().render(this, 20, 30, 555);
	}

	public void tick(int ticks) {}

	public void guiActionPerformed(int elementId, int action) {
		if(elementId == getList().getId()) {
			switch (action) {
			case 0 :
				close();
				break;
			case 1:
				getGame().getWorld().reset();
				getGame().initWorldLevel();
				close();
				break;
			case 2:
				System.out.println("The game has been quit!");
				close();
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

	@Override
	public void actionPerformed(MouseInputEvent event) {}

}
