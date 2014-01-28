package org.game.towers.game;

//import static java.lang.String.format;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import javax.swing.JFrame;

import org.game.towers.configs.Config;
//import org.game.towers.gfx.Colors;
//import org.game.towers.gfx.Font;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.SpriteSheet;
import org.game.towers.grid.Grid;
import org.game.towers.gui.Gui;
import org.game.towers.gui.GuiFocus;
import org.game.towers.gui.GuiMainMenu;
import org.game.towers.gui.GuiPause;
import org.game.towers.handlers.InputHandler;
import org.game.towers.handlers.MouseHandler;
//import org.game.towers.level.Level;
import org.game.towers.npcs.NpcTypesCollection;
import org.game.towers.workers.PathWorker;

public class Game extends Canvas implements Runnable, FocusListener {

	private static final long serialVersionUID = 1L;

	public static Game instance;

	private JFrame frame;
	private Thread thread;
	private boolean isApplet = false;

	private boolean running = false;
	private boolean isFocused = true;

	private int ticksCount = 0;

	public boolean debug;
	public static boolean DEBUG = true;

	private BufferedImage image = new BufferedImage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public int[] colors = new int[6 * 6 * 6];//6 different shades of color

	private Screen screen;
//	public static Level level;
	public static Grid grid = new Grid();
	private Gui gui;
	private World world;

	private InputHandler inputHandler;
	private MouseHandler mouseHandler;

//	private int x = 0;
//	private int y = 0;

	private boolean launcherInited = false;

	private PathWorker pathWorker;

	public Game(boolean isApplet) {
		setApplet(isApplet);
		initLauncher();
	}

	public void initSizes() {
		setMinimumSize(Config.DIMENSIONS);
		setMaximumSize(Config.DIMENSIONS);
		setPreferredSize(Config.DIMENSIONS);
	}

	public void initFrame() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
		if (!isApplet()) {
			frame.setTitle(Config.GAME_NAME);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	}

	public void run() {
		if (!launcherInited) {
			debug(DebugLevel.WARNING, "Launcher was not correctly initiated!");
			initLauncher();
		}
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		init();

		while(isRunning()) {
			if (!isVisible()) continue;
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while(delta >= 1) {
				ticks++;
				tick();
				delta -= 1;
				shouldRender = true;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer >= 1000) {
				lastTimer += 1000;
				debug(DebugLevel.INFO, frames + " frames, " + ticks
						+ " ticks");
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void init() {
		initHomeDirectory();
		initWorld();
		initFocusListener();
		initColors();
		initScreen();
		initInput();
//		initLevel();
		initPathWorker();
		initMainMenu();
	}

	private void initPathWorker() {
		setPathWorker(new PathWorker());
	}

	private void initUnits() {
		NpcTypesCollection.load();
	}

	private void initMainMenu() {
		showGui(new GuiMainMenu(this, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
	}

	private void initFocusListener() {
		 addFocusListener(this);
	}

	private void initWorld() {
		File w = new File(World.WORLD_DIR);
		if (!w.exists()) {
			w.mkdir();
			Game.debug(Game.DebugLevel.INFO, "Directory " + w.getAbsolutePath()
					+ " created!");
		}
	}

	private void initHomeDirectory() {
		File f = new File(Config.HOME_DIR);
		Game.debug(Game.DebugLevel.INFO, "Game directory set to " + Config.HOME_DIR);
		if (!f.exists()) {
			f.mkdir();
			Game.debug(Game.DebugLevel.INFO, "Directory " + f.getAbsolutePath()	+ " created!");
		}
	}

	private void tick() {
		ticksCount++;
		getInputHandler().tick();
		if (gui != null) {
			gui.tick(ticksCount);
			if (gui != null && !gui.pausesGame()) {
				if (getWorld() != null) {
					tickLevel();
				}
			}
		} else {
			if (getWorld() != null) {
				tickLevel();
			}
		}
	}

	private void tickLevel() {
		getWorld().tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(Config.BUFFER_STRATEGY_BUFFERS);
			return;
		}

//		int xOffset = x - (getScreen().width / 2);
//		int yOffset = y - (getScreen().height / 2);
//		level.renderTiles(getScreen(), xOffset, yOffset);

		if (gui != null) {
			gui.render();
			for (int y = 0; y < Config.SCREEN_HEIGHT; y++) {
				for (int x = 0; x < Config.SCREEN_WIDTH; x++) {
					int colorCode = gui.pixels[x + y * gui.width];
					pixels[x + y * Config.SCREEN_WIDTH] = colorCode + (0xFF << 24);
				}
			}
		} else {
			if (world != null) {
				world.render(screen);

				for (int y = 0; y < getScreen().height; y++) {
					for (int x = 0; x < getScreen().width; x++) {
						int colorCode = getScreen().pixels[x + y * getScreen().width];
						if (colorCode < 255) getPixels()[x + y * Config.SCREEN_WIDTH] = colors[colorCode];
					}
				}
//				String msg = "Hello World";
//				Font.render(msg, screen, screen.xOffset + screen.width / 2 - (msg.length()*8)/2, screen.yOffset + screen.height / 2, Colors.get(-1, -1, -1, 000));
			}
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, getWidth(), getHeight());

		g.dispose();
		bs.show();
	}

	private void initScreen() {
		setScreen(new Screen(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, new SpriteSheet(Config.SPRITESHEET_FILE)));
	}

//	private void initLevel() {
//		level = new Level(format("%s%s", Config.DEFAULT_LEVELS_PATH, Config.DEFAULT_LEVEL_FILENAME));
//		level.setOffset(getScreen());
//	}
	private void initInput() {
		setInputHandler(new InputHandler(this));
		setMouseHandler(new MouseHandler(this));
	}

	private void initColors() {
		int index = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255/5);
					int gg = (g * 255/5);
					int bb = (b * 255/5);

					colors[index++] = rr << 16 | gg << 8 | bb;
				}
			}
		}
	}

	public synchronized void start() {
		setRunning(true);
		thread = new Thread(this);
		thread.start();
		Game.debug(Game.DebugLevel.INFO, "Game started");
	}

	public synchronized void stop() {
		setRunning(false);

		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Game(false).start();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * Sends a debug message to the console.
	 *
	 * @param level The debug level (what type of message, "INFO", "WARNING", "ERROR")
	 * @param msg The text to output.
	 */
	public static void debug(DebugLevel level, String msg) {
		switch (level) {
			default:
			case INFO:
				if (Game.DEBUG) {
					System.out.println("[" + Config.GAME_NAME + "] " + msg);
				}
				break;
			case WARNING:
				if (Game.DEBUG) {
					System.out.println("[" + Config.GAME_NAME + "] WARNING: " + msg);
				}
				break;
			case ERROR:
				if (Game.DEBUG) {
					System.out.println("[" + Config.GAME_NAME + "] CRITICAL ERROR: " + msg);
					System.out.println("System forced to exit!");
					System.exit(1);
				}
				break;
		}
	}

	/**
	 * A list of debug levels. INFO: Some information useful for programmers
	 * WARNING: If something isn't performing as expected but is not critical
	 * for the runtime. ERROR: Something bad happened and the game can no longer
	 * run.
	 */
	public static enum DebugLevel {
		INFO, WARNING, ERROR;
	}

	@Override
	public void focusGained(FocusEvent focusEvent) {
		setFocused(true);
		if (gui != null && gui instanceof GuiFocus) {
			gui.last();
		}
		Game.debug(DebugLevel.INFO, "Got focus!");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (arg0.getID() == FocusEvent.FOCUS_LOST) {
			if ((gui != null && !gui.pausesGame()) || gui == null) {
				showGui(new GuiPause(this, getWidth(), getHeight()));
			}
			Game.debug(DebugLevel.INFO, "Lost the focus!");
		}
	}

	public boolean isFocused() {
		return isFocused;
	}

	public void setFocused(boolean isFocused) {
		this.isFocused = isFocused;
	}

	public void showGui(Gui gui) {
		if(this.gui != null) {
			getInputHandler().removeListener(this.gui);
		}
		this.gui = null;
		this.gui = gui;
		getInputHandler().addListener(this.gui);
	}

	public void hideGui(Gui gui) {
		getInputHandler().removeListener(gui);
		if (gui.getParentGui() != null) {
			showGui(gui.getParentGui());
		}
		if (this.gui == gui) {
			this.gui = null;
		}
	}

	public void forceGuiClose() {
		if (gui != null) {
			gui.close();
		}
		gui = null;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
		initUnits();
	}

	public void setLaucherInited(boolean isLauncherInited) {
		this.launcherInited  = isLauncherInited;
	}

	public void initLauncher() {
		if (Game.instance == null) {
			Game.instance = this;
		}
		initSizes();
		initFrame();
		requestFocus();
		setLaucherInited(true);
	}

	public boolean isApplet() {
		return isApplet;
	}

	public void setApplet(boolean isApplet) {
		this.isApplet = isApplet;
	}

	public MouseHandler getMouseHandler() {
		return mouseHandler;
	}

	public void setMouseHandler(MouseHandler mouseHandler) {
		this.mouseHandler = mouseHandler;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setInputHandler(InputHandler inputHandler) {
		this.inputHandler = inputHandler;
	}

	public PathWorker getPathWorker() {
		return pathWorker;
	}

	public void setPathWorker(PathWorker pathWorker) {
		this.pathWorker = pathWorker;
	}
}
