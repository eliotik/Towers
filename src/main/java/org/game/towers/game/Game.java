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
import org.game.towers.gfx.Screen;
import org.game.towers.gfx.SpriteSheet;
import org.game.towers.grid.Grid;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	private JFrame frame;
	
	private boolean running = false;
	private int ticksCount = 0;
	
	private BufferedImage image = new BufferedImage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private Screen screen;
	public static Grid grid = new Grid();

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
		
		initScreen();
		
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
		screen.xOffset = 0;
		screen.yOffset = 0;
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
		
		screen.render(pixels, 0, Config.SCREEN_WIDTH);
		
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
