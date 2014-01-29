package org.game.towers.gfx.sprites;

import static java.lang.String.format;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.game.towers.configs.Config;

public class SpriteSheet {
	private String file;
	private int width;
	private int height;

	private int[] pixels;

	public SpriteSheet(String initFile) {
		BufferedImage image = null;
		String spriteFilePath = format("%s%s", Config.IMAGES_FILE_PATH, initFile);
		try {
			image = ImageIO.read(Config.class.getResourceAsStream(spriteFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (image == null) {
			return;
		}

		setFile(initFile);
		setWidth(image.getWidth());
		setHeight(image.getHeight());

		pixels = image.getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());

//		for (int i = 0; i < pixels.length; i++) {
//			pixels[i] = (pixels[i] & 0xff) / 64;
//		}
	}

	public int getWidth() {
		return width;
	}

	private void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	private void setHeight(int height) {
		this.height = height;
	}

	public String getFile() {
		return file;
	}

	private void setFile(String file) {
		this.file = file;
	}

	public int[] getPixels() {
		return pixels;
	}
}
