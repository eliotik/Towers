package org.game.towers.game;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JFrame;

import org.game.towers.configs.Config;

public class GameLauncher extends Applet {

	private static final long serialVersionUID = 1L;
	
	public static Game game;

//	@Override
//	public void start() {
////		System.out.println("GL start");
////		game.start();
//	}

//	@Override
//	public void init() {
//		System.out.println("GL init");
//		setMinimumSize(Config.DIMENSIONS);
//		setMaximumSize(Config.DIMENSIONS);
//		setPreferredSize(Config.DIMENSIONS);
//		
//		setLayout(new BorderLayout());
//		Game.instance = new Game();
//		
//		add(Game.instance, BorderLayout.CENTER);
//	}

//	@Override
//	public void stop() {
//		System.out.println("GL stop");
//		game.stop();
//	}

	public static void main(String[] args) {
		Game.instance = new Game();
		game = Game.instance;
		game.initLauncher();
		game.start();
	}
}
