package org.game.towers.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.Level;
import org.game.towers.game.level.tiles.Tile;
import org.game.towers.gfx.sprites.Sprite;
import org.game.towers.gfx.sprites.SpritesData;
import org.game.towers.gui.elements.FontRenderer;
import org.game.towers.units.Unit;
import org.game.towers.units.npcs.Npc;
import org.game.towers.workers.Utils;
import org.game.towers.workers.geo.Coordinates;

public class Screen {

	public static final byte BIT_MIRROR_X = 0x01;
	public static final byte BIT_MIRROR_Y = 0x02;

	private int[] pixels;
	private HashMap<String, Integer> fog;
	private Graphics graphics;

	private int xOffset = 0;
	private int yOffset = 0;

	private int width;
	private int height;

	private Point mousePosition = new Point(0, 0);

	private List<RenderedString> renderText = new ArrayList<RenderedString>();

	public Screen(int width, int height) {
		setWidth(width);
		setHeight(height);

		setPixels(new int[width * height]);
	}

	public void setGraphics(Graphics g) {
		graphics = g;
	}

	public void clear() {
		for (int i = 0; i < getPixels().length; i++) {
			getPixels()[i] = 0;
		}
	}

	public void renderString(String msg, int x, int y, int size, int style,
			Color color) {
		synchronized (renderText) {
			renderText.add(new RenderedString(msg, x, y, size, style, color));
		}
	}

	private void setFont(int size, int style) {
		graphics.setFont(new Font("Helvetica", style, size));
	}

	public void drawCenteredString(String msg, int x, int y, int size,
			int style, Color color) {
		setFont(size, style);
		renderString(msg, x - getStringWidth(msg) / 2, y, size, style, color);
	}

	public void drawRightJustifiedString(String msg, int xOffset, int y,
			int size, int style, Color color) {
		setFont(size, style);
		renderString(msg, Config.REAL_SCREEN_WIDTH - getStringWidth(msg)
				- xOffset, y, size, style, color);
	}

	public int getStringWidth(String msg) {
		if (graphics == null) {
			return 0;
		}
		FontMetrics metrics = graphics.getFontMetrics();
		if (metrics == null) {
			metrics = new FontMetrics(graphics.getFont()) {
				private static final long serialVersionUID = 1L;
			};
		}
		return metrics.stringWidth(msg);
	}

	public void renderText() {
		synchronized (renderText) {
			for (RenderedString s : renderText) {
				if (graphics == null) {
					return;
				}
				graphics.setColor(s.getColor());
				setFont(s.getSize(), s.getStyle());
				graphics.drawString(s.getMsg(), s.getX(), s.getY());
			}
		}
		renderText.clear();
	}

	public void renderTile(Level level, int xOrig, int yOrig, Tile tile, int mirrorDir, int scale) {
		int xp = (xOrig * tile.getSprite().getWidth()) - getxOffset();
		int yp = (yOrig * tile.getSprite().getHeight()) - getyOffset();

		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;

		int scaleMap = scale - 1;

		for (int y = 0; y < tile.getSprite().getHeight(); y++) {
			int yt = y + yp;

			int ySheet = y;
			if (mirrorY) ySheet = Config.BOX_SIZE_FIXED - y;

			int yPixel = y + yp + (y * scaleMap) - ((scaleMap << Config.COORDINATES_SHIFTING) / 2);

			for (int x = 0; x < tile.getSprite().getWidth(); x++) {
				int xt = x + xp;
				int xSheet = x;
				if (mirrorX) xSheet = Config.BOX_SIZE_FIXED - x;

				int xPixel = x + xp + (x * scaleMap) - ((scaleMap << Config.COORDINATES_SHIFTING) / 2);

				if (0 - tile.getSprite().getWidth() > xt || xt >= getWidth()
						|| 0 - tile.getSprite().getHeight() > yt || yt >= getHeight()) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}

				if (yt < 0) {
					yt = 0;
				}
				int color = tile.getSprite().getPixels()[xSheet + ySheet * tile.getSprite().getWidth()];
				if ((color == 0xFFFF00FF || color == 0xFF800080) && level != null) {
					Tile bgTile = level.getBackgroundTile(xOrig, yOrig);
					color = bgTile.getSprite().getPixels()[x	+ y * bgTile.getSprite().getWidth()];
				}
				for (int yScale = 0; yScale < scale; yScale++) {
					if (yPixel + yScale < 0 || yPixel + yScale >= getHeight())
						continue;
					for (int xScale = 0; xScale < scale; xScale++) {
						if (xPixel + xScale < 0 || xPixel + xScale >= getWidth())
							continue;
						int pixelIndex = (xPixel + xScale) + (yPixel + yScale) * getWidth();
						if (Config.DEFAULT_LEVEL_USE_FOG) {
							String key = "x"+(xt+getxOffset())+"y"+(yt+getyOffset());
							if (getFog().containsKey(key)) {
								switch(getFog().get(key)) {
								default:
								getPixels()[pixelIndex] =
										(tile.getHighlight() != 1)
										? Colors.brightness(color, tile.getHighlight())
										: color;
										break;
								case 0:
									if (getPixels()[pixelIndex] != 0) System.out.println(getPixels()[pixelIndex]);
									getPixels()[pixelIndex] = Config.DEFAULT_BKG_COLOR_DARK;
									break;
								case 1:
									getPixels()[pixelIndex] = Colors.tint(color, 0.5D, 0.5D, 0.5D);
									break;
								}
							} else {
								getPixels()[pixelIndex] = Config.DEFAULT_BKG_COLOR_DARK;
							}
						} else {
							getPixels()[pixelIndex] =
									(tile.getHighlight() != 1)
									? Colors.brightness(color, tile.getHighlight())
									: color;
						}
					}
				}
			}
		}
	}

	public void fill(int color) {
		for (int i = 0; i < getPixels().length; i++) {
			getPixels()[i] = color;
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		setxOffset(xOffset);
		setyOffset(yOffset);
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}

	public int getxOffset() {
		return xOffset;
	}

	public void setxOffset(int xOffset) {
		this.xOffset = xOffset;
	}

	public int getyOffset() {
		return yOffset;
	}

	public void setyOffset(int yOffset) {
		this.yOffset = yOffset;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public void renderUnit(int xOrig, int yOrig, Unit unit, int mirrorDir, int scale) {
		int xp = xOrig - getxOffset();
		int yp = yOrig - getyOffset();
		boolean mirrorX = (mirrorDir & BIT_MIRROR_X) > 0;
		boolean mirrorY = (mirrorDir & BIT_MIRROR_Y) > 0;
		int scaleMap = scale - 1;

		for (int y = 0; y < unit.getCurrentSprite().getHeight(); y++) {
			int yt = y + yp;

			int ySheet = y;
			if (mirrorY) ySheet = Config.BOX_SIZE_FIXED - y;

			int yPixel = y + yp + (y * scaleMap) - ((scaleMap << Config.COORDINATES_SHIFTING) / 2);

			for (int x = 0; x < unit.getCurrentSprite().getWidth(); x++) {
				int xt = x + xp;

				int xSheet = x;
				if (mirrorX) xSheet = Config.BOX_SIZE_FIXED - x;

				int xPixel = x + xp + (x * scaleMap) - ((scaleMap << Config.COORDINATES_SHIFTING) / 2);

				if (0 - unit.getCurrentSprite().getWidth() > xt || xt >= getWidth()
						|| 0 - unit.getCurrentSprite().getHeight() > yt
						|| yt >= getHeight()) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}

				if (yt < 0) {
					yt = 0;
				}

				int color = unit.getCurrentSprite().getPixels()[xSheet + ySheet	* unit.getCurrentSprite().getWidth()];
				if (color != 0xFFFF00FF && color != 0xFF800080) {
					for (int yScale = 0; yScale < scale; yScale++) {
						if (yPixel + yScale < 0 || yPixel + yScale >= getHeight())
							continue;
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= getWidth())
								continue;
							int pixelIndex = (xPixel + xScale) + (yPixel + yScale) * getWidth();

							if (Config.DEFAULT_LEVEL_USE_FOG) {
								String key = "x"+(xt+getxOffset())+"y"+(yt+getyOffset());
								if (getFog().containsKey(key) && getFog().get(key) < 2) {
									continue;
								}
							}
							getPixels()[pixelIndex] = color;
							getPixels()[pixelIndex] =
									(unit.getHighlight() != 1)
									? Colors.brightness(color, unit.getHighlight())
									: color;
						}
					}
				}
			}
		}

		//rendering healthbar for npcs
		if (unit instanceof Npc && unit.getHealth() < unit.getMaxHealth() && !unit.isDead()) {
			yp -= 8;
			for (int y = 0; y < ((Npc) unit).getCurrentHealthSprite().getHeight(); y++) {
				int yt = y + yp;
				for (int x = 0; x < ((Npc) unit).getCurrentHealthSprite().getWidth(); x++) {
					int xt = x + xp;
					if (0 - ((Npc) unit).getCurrentHealthSprite().getWidth() > xt
							|| xt >= getWidth()
							|| 0 - ((Npc) unit).getCurrentHealthSprite().getHeight() > yt
							|| yt >= getHeight()) {
						break;
					}
					if (xt < 0) {
						xt = 0;
					}

					if (yt < 0) {
						yt = 0;
					}
					int color = ((Npc) unit).getCurrentHealthSprite().getPixels()[x + y * ((Npc) unit).getCurrentHealthSprite().getWidth()];
					if (color != 0xFFFF00FF && color != 0xFF800080) {
                        int pixelIndex = xt + yt * getWidth();
						if (Config.DEFAULT_LEVEL_USE_FOG) {
							String key = "x"+(xt+getxOffset())+"y"+(yt+getyOffset());
							if (getFog().containsKey(key) && getFog().get(key) < 2) {
								continue;
							}
						}
                        getPixels()[pixelIndex] = color;
					}
				}
			}
		}
	}

	public void renderTile(Level level, int xOrig, int yOrig, Tile tile) {
		renderTile(level, xOrig, yOrig, tile, tile.getMirrorMask(), 1);
	}

	public void renderLevelGui() {
		Game.getInstance().getWorld().getLevel().getStore().render(this);
		renderIcons();
	}

	private void renderIcons() {
		int fontScale = 2;
		int margin = 4;
		int iconXPos = 0;
		int yPos = Config.SCREEN_HEIGHT - SpritesData.MONEY.getHeight() + 2;
		int yBPos = yPos + 1;
		int xPos = SpritesData.MONEY.getWidth() + margin;
		int xBPos = xPos + 1;
		int white = 555;
		int black = 000;
		Level level = Game.getInstance().getWorld().getLevel();
		String playerMoneyText = level.getPlayerMoney() + "";
		String playerHealthText = level.getPlayerHealth() + "";
		String playerResource = level.getPlayerResource() + "";

		renderIcon(SpritesData.MONEY, iconXPos, getHeight() - SpritesData.MONEY.getHeight());
		FontRenderer.drawString(playerMoneyText, xBPos, yBPos, black, fontScale);
		FontRenderer.drawString(playerMoneyText, xPos, yPos, white, fontScale);

		iconXPos = SpritesData.MONEY.getWidth()+FontRenderer.getStringWidth(playerMoneyText, fontScale) + Config.BOX_SIZE/2;
		xPos = iconXPos + SpritesData.MONEY.getWidth() + margin;
		xBPos = xPos + 1;
		renderIcon(SpritesData.HEART, iconXPos, getHeight() - SpritesData.HEART.getHeight());
		FontRenderer.drawString(playerHealthText, xBPos, yBPos, black, fontScale);
		FontRenderer.drawString(playerHealthText, xPos, yPos, white, fontScale);

		iconXPos = SpritesData.MONEY.getWidth()+SpritesData.HEART.getWidth()+FontRenderer.getStringWidth(playerMoneyText + playerHealthText, fontScale) + Config.BOX_SIZE;
		xPos = iconXPos + SpritesData.HEART.getWidth() + margin;
		xBPos = xPos + 1;
		renderIcon(SpritesData.RESOURCE, iconXPos, getHeight() - SpritesData.RESOURCE.getHeight());
		FontRenderer.drawString(playerResource, xBPos, yBPos, black, fontScale);
		FontRenderer.drawString(playerResource, xPos, yPos, white, fontScale);

		String waveMessage = "WAVE " + (level.getWave()-1);
		int waveFontScale = fontScale-1;
		int waveMessageX = getWidth()/2 - FontRenderer.getStringWidth(waveMessage, waveFontScale)/2;
		FontRenderer.drawString(waveMessage, waveMessageX+1, 1, black, waveFontScale);
		FontRenderer.drawString(waveMessage, waveMessageX, 0, white, waveFontScale);
	}

	private void renderIcon(Sprite sprite, int xp, int yp) {
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yt = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xt = x + xp;
				if (0 > xt || xt >= getWidth() || 0 > yt || yt >= getHeight()) {
					break;
				}
				if (xt < 0) {
					xt = 0;
				}
				if (yt < 0) {
					yt = 0;
				}
				int color = sprite.getPixels()[x + y * sprite.getWidth()];
				if (color != 0xFFFF00FF && color != 0xFF800080) {
					getPixels()[xt + yt * getWidth()] = color;
				}
			}
		}
	}

	public void refineFogLayer(double x, double y, int radarSize) {
		HashMap<Coordinates, Integer> circle = Utils.getVisiblePixels(Config.VIEW_TYPE_LIGHT, x, y, radarSize);
		Iterator<Entry<Coordinates, Integer>> it = circle.entrySet().iterator();
		while (it.hasNext()) {
		    @SuppressWarnings("rawtypes")
			Map.Entry data = (Map.Entry)it.next();
		    Coordinates coordinates = (Coordinates) data.getKey();
		    String key = "x"+coordinates.getX()+"y"+coordinates.getY();
		    int value = (int) data.getValue();
		    if (getFog().containsKey(key)) {
		    	int oldValue = getFog().get(key);
		    	if (value == 0 && oldValue != 0) value = oldValue;
		    	if (value == 1 && oldValue > 1 ) value = oldValue;
		    }
		    getFog().put(key, value);
		}
	}

	public HashMap<String, Integer> getFog() {
		return fog;
	}

	public void setFog(HashMap<String, Integer> fog) {
		this.fog = fog;
	}
}
