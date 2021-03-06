package org.game.towers.game;

import java.applet.Applet;
import java.awt.BorderLayout;

public class GameLauncher extends Applet {

	private static final long serialVersionUID = 1L;

	public static Game game;

	public void init() {
		Game.setInstance(new Game(true));
		setMinimumSize(Config.DIMENSIONS);
		setMaximumSize(Config.DIMENSIONS);
		setPreferredSize(Config.DIMENSIONS);

		setLayout(new BorderLayout());


		add(Game.getInstance(), BorderLayout.CENTER);
	}

	public void start() {
		game.start();
	}

	public void stop() {
		game.stop();
	}

//	public static void main(String[] args) {
//		Game.instance = new Game();
//		game = Game.instance;
//		game.initLauncher();
//		game.start();
//	}
}
