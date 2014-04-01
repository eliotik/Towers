package org.game.towers.game;

//import static java.lang.String.format;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;

import javax.swing.JFrame;

import org.game.towers.gfx.Screen;
import org.game.towers.gui.Gui;
import org.game.towers.gui.GuiFocus;
import org.game.towers.gui.GuiMainMenu;
import org.game.towers.gui.GuiPause;
import org.game.towers.handlers.InputHandler;
import org.game.towers.handlers.MouseHandler;
import org.game.towers.units.collections.BulletsCollection;
import org.game.towers.units.collections.ModificatorsCollection;
import org.game.towers.units.collections.NpcsCollection;
import org.game.towers.units.collections.TowersCollection;
import org.game.towers.workers.PathWorker;

public class Game extends Canvas implements Runnable, FocusListener {

	private static final long serialVersionUID = 1L;

	private static Game instance;

	private JFrame frame;
	private Thread thread;
	private boolean isApplet = false;

	private boolean running = false;
	private boolean isFocused = true;
	private boolean launcherInited = false;

	private int ticksCount = 0;

	public boolean debug;
	public static boolean DEBUG = true;

	private BufferedImage image = new BufferedImage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) getImage().getRaster().getDataBuffer()).getData();
//	public int[] colors = new int[6 * 6 * 6];//6 different shades of color

	private Screen screen;
	private Gui gui;
	private World world;
	private PathWorker pathWorker;

	private InputHandler inputHandler;
	private MouseHandler mouseHandler;

	private int lastFrames = 0;
	private int lastTicks = 0;

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
		setFrame(new JFrame());
		getFrame().setLayout(new BorderLayout());
		getFrame().add(this, BorderLayout.CENTER);
		getFrame().pack();
		getFrame().setResizable(false);
		getFrame().setLocationRelativeTo(null);
		getFrame().setVisible(true);
		getFrame().requestFocus();
		if (!isApplet()) {
			getFrame().setTitle(Config.GAME_NAME);
			getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	}

	public void run() {
		if (!isLauncherInited()) {
			debug(DebugLevel.WARNING, "Launcher was not correctly initiated! Will try to initiate laucher manually.");
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
				setLastFrames(frames);
				setLastTicks(0);
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void init() {
		initHomeDirectory();
		initWorld();
		initUnits();
		initFocusListener();
		initScreen();
		initInput();
		initPathWorker();
		initMainMenu();
	}

	private void initPathWorker() {
		setPathWorker(new PathWorker());
	}

	private void initUnits() {
		TowersCollection.load();
		ModificatorsCollection.load();
		BulletsCollection.load();
		NpcsCollection.load();
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
		setTicksCount(getTicksCount() + 1);
		getInputHandler().tick();
		getMouseHandler().tick();
		if (getGui() != null) {
			getGui().tick(getTicksCount());
			if (!getGui().pausesGame()) {
				updateWorld();
			}
		} else {
			updateWorld();
		}
	}

	private void updateWorld() {
		if (getWorld() != null) {
			getWorld().tick();
		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(Config.BUFFER_STRATEGY_BUFFERS);
			requestFocus();
			return;
		}

		getScreen().clear();
		Graphics g = bs.getDrawGraphics();
		getScreen().setGraphics(g);

		if (getGui() != null) {
			getGui().render();
		} else {
			if (getWorld() != null) {
				getWorld().render();
			}
		}
		for (int i = 0; i < Config.SCREEN_WIDTH * Config.SCREEN_HEIGHT; i++) {
			getPixels()[i] = getScreen().getPixels()[i];
		}
		g.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);

		getScreen().renderText();

		g.dispose();
		bs.show();
	}

	private void initScreen() {
		setScreen(new Screen(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));
	}

	private void initInput() {
		setInputHandler(new InputHandler(this));
		setMouseHandler(new MouseHandler(this));
	}

	public synchronized void start() {
		setRunning(true);
		setThread(new Thread(this, "GameThread"));
		getThread().start();
		Game.debug(Game.DebugLevel.INFO, "Game started");
	}

	public synchronized void stop() {
		setRunning(false);

		try {
			getThread().join();
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
		if (getGui() != null && getGui() instanceof GuiFocus) {
			getGui().last();
		}
		Game.debug(DebugLevel.INFO, "Got focus!");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		if (arg0.getID() == FocusEvent.FOCUS_LOST && Config.DEFAULT_PAUSE_ON_FOCUS_LOST) {
			if ((getGui() != null && !getGui().pausesGame()) || getGui() == null) {
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
		if (getGui() != null && getGui().pausesGame()) {
			return;
		}
		if(getGui() != null) {
			getInputHandler().removeListener(getGui());
		}
		setGui(null);
		setGui(gui);
		getInputHandler().addListener(getGui(), true);
	}

	public void hideGui(Gui gui) {
		getInputHandler().removeListener(gui);
		if (gui.getParentGui() != null) {
			showGui(gui.getParentGui());
		}
		if (getGui() == gui) {
			setGui(null);
		}
	}

	public void forceGuiClose() {
		if (getGui() != null) {
			getGui().close();
		}
		setGui(null);
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
	}

	public void initLauncher() {
		if (Game.getInstance() == null) {
			Game.setInstance(this);
		}
		initSizes();
		initFrame();
		requestFocus();
		setLauncherInited(true);
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

	public int getLastFrames() {
		return lastFrames;
	}

	public void setLastFrames(int lastFrames) {
		this.lastFrames = lastFrames;
	}

	public int getLastTicks() {
		return lastTicks;
	}

	public void setLastTicks(int lastTicks) {
		this.lastTicks = lastTicks;
	}

	public Gui getGui() {
		return gui;
	}

	public void setGui(Gui gui) {
		this.gui = gui;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public boolean isLauncherInited() {
		return launcherInited;
	}

	public void setLauncherInited(boolean launcherInited) {
		this.launcherInited = launcherInited;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public Thread getThread() {
		return thread;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public int getTicksCount() {
		return ticksCount;
	}

	public void setTicksCount(int ticksCount) {
		this.ticksCount = ticksCount;
	}

	public static Game getInstance() {
		return instance;
	}

	public static void setInstance(Game instance) {
		Game.instance = instance;
	}

	public void initWorldLevel() {
		setWorld(new World(Config.DEFAULT_WORLD_NAME));
		getWorld().getLevel().initComponents();
		if (Config.DEFAULT_LEVEL_USE_WAVES) {
			getWorld().getLevel().generateNpcs();
		}
		getWorld().getLevel().addTowers();
	}
}
