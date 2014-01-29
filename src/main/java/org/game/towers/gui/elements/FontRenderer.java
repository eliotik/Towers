package org.game.towers.gui.elements;

import org.game.towers.configs.Config;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.sprites.SpriteSheet;
import org.game.towers.gui.Gui;

public class FontRenderer {
	
	public static SpriteSheet FONT = new SpriteSheet(Config.FONT_FILE);

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ><    "
			+ "abcdefghijklmnopqrstuvwxyz{}    "
			+ "1234567890+-*/,.()[]<>!?=\"':;^  ";
	private static String width5 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdeghkmnopqrsuvwxyz1234567890+-=?{}^";
	private static String width4 = "ft\"";
	private static String width3 = "><ij*/ ";
	private static String width2 = "l,.()[]';";
	private static String width1 = "!:";

	public static void drawString(String msg, Gui gui, int x, int y,
			int color, int scale) {
		int textWidth = 0;

		for (int i = 0; i < msg.length(); i++) {
			int charIndex = chars.indexOf(msg.charAt(i));
			int charWidth = getCharWidth(msg.charAt(i));
			if (charIndex < 0)
				charIndex = 29;
			GuiRenderer.render(gui, FONT, 
					x + textWidth + scale * i, 
					y, 
					charIndex, 
					Colors.get(-1, -1, -1, color),
					0x00, 
					scale);
			textWidth = textWidth + charWidth * scale;
		}
	}

	public static void drawCenteredString(String msg, Gui gui, int x,
			int y, int color, int scale) {
		FontRenderer.drawString(msg, gui, x - FontRenderer.getStringWidth(msg, scale) / 2, y,
				color, scale);
	}
	
	public static int getStringWidth(String msg, int scale) {
		int textWidth = 0;
		for (int i = 0; i < msg.length(); i++) {
			int charWidth = getCharWidth(msg.charAt(i));
			textWidth = textWidth + charWidth * scale + 1;
		}
		return textWidth;
	}
	
	private static int getCharWidth(char c) {
		int charWidth = 5;
		if(width5.indexOf(c) >= 0) {
			charWidth = 5;
		}
		if(width4.indexOf(c) >= 0) {
			charWidth = 4;
		}
		if(width3.indexOf(c) >= 0) {
			charWidth = 3;
		}
		if(width2.indexOf(c) >= 0) {
			charWidth = 2;
		}
		if(width1.indexOf(c) >= 0) {
			charWidth = 1;
		}
		return charWidth;
	}
}
