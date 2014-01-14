package org.game.towers.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import org.game.towers.configs.Config;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Font;
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.SpriteSheet;
import org.game.towers.grid.Grid;
import org.game.towers.level.Level;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private boolean running = false;
	private int ticksCount = 0;
	
	private BufferedImage image = new BufferedImage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	public int[] colors = new int[6 * 6 * 6];//6 different shades of color
	
	private Screen screen;
	public Level level;
	public static Grid grid = new Grid();

	private int x = 0; 
	private int y = 0;
	
	public Game() {
		System.out.println(Config.SCREEN_WIDTH * Config.SCALE);
		System.out.println(Config.SCREEN_HEIGHT * Config.SCALE);
		setMinimumSize(new Dimension(Config.SCREEN_WIDTH * Config.SCALE, Config.SCREEN_HEIGHT * Config.SCALE));
		setMaximumSize(new Dimension(Config.SCREEN_WIDTH * Config.SCALE, Config.SCREEN_HEIGHT * Config.SCALE));
		setPreferredSize(new Dimension(Config.SCREEN_WIDTH * Config.SCALE, Config.SCREEN_HEIGHT * Config.SCALE));
		
		initFrame();
	}
	
	private void initFrame() {
		frame = new JFrame(Config.GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		initColors();
		initScreen();
		initLevel();
		
		while(isRunning()) {
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
				System.out.println(ticks + " ticks, " + frames + " frames");
				frames = 0;
				ticks = 0;
			}
		}
	}

	private void tick() {
		ticksCount++;
		level.tick();
		
//		screen.xOffset = 10;
//		screen.yOffset = 10;
//		
//		screen.xOffset++;
//		screen.yOffset++;
		
//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = i + ticksCount;
//		}
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(Config.BUFFER_STRATEGY_BUFFERS);
			return;
		}
		
		int xOffset = x - (screen.width / 2);
		int yOffset = y - (screen.height / 2);
		level.renderTiles(screen, xOffset, yOffset);
		
		for (int x = 0; x < level.width; x++) {
			int color = Colors.get(-1, -1, -1, 000);
			if(x % 10 == 0 && x != 0) {
				color = Colors.get(-1, -1, -1, 500);
			}
			Font.render((x%10)+"", screen, 0 + (x * 8), 0, color);
		}
		for (int y = 0; y < level.height; y++) {
			int color = Colors.get(-1, -1, -1, 000);
			if(y % 10 == 0 && y != 0) {
				color = Colors.get(-1, -1, -1, 500);
			}
			Font.render((y%10)+"", screen, 0, 0 + (y * 8), color);
		}
		
//		String msg = "Hello World";
//		Font.render(msg, screen, screen.xOffset + screen.width / 2 - (msg.length()*8)/2, screen.yOffset + screen.height / 2, Colors.get(-1, -1, -1, 000));
		
		for (int y = 0; y < screen.height; y++) {
			for (int x = 0; x < screen.width; x++) {
				int colorCode = screen.pixels[x + y * screen.width];
				if (colorCode < 255) pixels[x + y * Config.SCREEN_WIDTH] = colors[colorCode];
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
		screen = new Screen(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, new SpriteSheet(Config.SPRITESHEET_FILE));
	}

	private void initLevel() {
		level = new Level(10, 10);
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
	
	private synchronized void start() {
		setRunning(true);
		new Thread(this).start();
	}
	
	private synchronized void stop() {
		setRunning(false);
	}
	
	public static void main(String[] args) {
		new Game().start();
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
}
