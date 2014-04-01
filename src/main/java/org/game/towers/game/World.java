package org.game.towers.game;

import static java.lang.String.format;

import org.game.towers.game.Game;
import org.game.towers.game.level.Level;

public class World {

	public static String WORLD_DIR = Config.WORLD_DIR;
	public static int VERSION = 1;

	private Level level;
	private Game game;
	private String name;

	public World(String name) {
		this.name = name;
		setLevel(new Level(format("%s%s", Config.DEFAULT_LEVELS_PATH, Config.DEFAULT_LEVEL_FILENAME)));
		Game.getInstance().getInputHandler().addListener(getLevel(), true);
	}

	public void reset() {
		Game.getInstance().getInputHandler().removeListener(getLevel());
		setLevel(null);
	}

	public void tick() {
		if (getLevel() != null) {
			getLevel().tick();
		}
	}

	public void render() {
		if (getLevel() != null) {
			getLevel().render(Game.getInstance().getScreen());

//			getLevel().renderStore(screen);
		}
	}

	public Game getGame() {
		return game;
	}

	public String getName() {
		return name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}
}
