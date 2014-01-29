package org.game.towers.level;

import org.game.towers.configs.Config;
import org.game.towers.configs.Npcs;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.geo.Geo;
import org.game.towers.gfx.Camera;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Font;
import org.game.towers.gfx.Screen;
import org.game.towers.gui.GuiPause;
import org.game.towers.handlers.InputHandler.GameActionListener;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.handlers.InputHandler.InputEventType;
import org.game.towers.level.tiles.Tile;
import org.game.towers.level.tiles.TileMap;
import org.game.towers.level.tiles.TileTypes;
import org.game.towers.npcs.NpcType;
import org.game.towers.units.Unit;
import org.game.towers.units.UnitFactory;
import org.game.towers.workers.NBTCapable;
import org.game.towers.workers.Tag;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

public class Level implements GameActionListener {

	private String name;
    private HashMap<String, TileMap> blocks = new HashMap<String, TileMap>();
	private byte[] tiles;
	public int width;
	public int height;
	public int xOffset = 0;
	public int yOffset = 0;
	private int wave = 1;
	private String imagePath;
	private BufferedImage image;
	public List<NpcType> npcs = new ArrayList<NpcType>();
	private Store store;
	private Camera camera;

	public Level(String imagePath) {
//		if (imagePath != null) {
			this.imagePath = imagePath;
			loadLevelFromFile();
			initStore();
			initCamera();
//		} else {
//			width = Config.DEFAULT_LEVEL_WIDTH;
//			height = Config.DEFAULT_LEVEL_HEIGHT;
//			tiles = new byte[width * height];
//			generateLevel();
//		}
	}

	private void initCamera() {
		if (getCamera() == null) {
			setCamera(new Camera(this, 0, 0, Game.instance.getInputHandler(), 4));
		}
	}

	private void initStore() {
		setStore(new Store());
	}

	public void generateNpcs() {
		switch(wave) {
			case 1:
				NpcType tank1 = UnitFactory.getNpc(Npcs.TANK);
				if (tank1 != null) {
					tank1.setLevel(this);
					tank1.setX(8);
					tank1.setY(160-Config.BOX_SIZE*4);

					addNpc(tank1);
				}
		}
	}

	private void loadLevelFromFile() {
		try {
			image = ImageIO.read(Config.class.getResource(this.imagePath));
			width = image.getWidth();
			height = image.getHeight();
			//tiles = new byte [width * height];
			loadTiles();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void loadTiles() {
		int[] tileColors = image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : TileTypes.tiles) {
					if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
						byte tId = t.getId();
						tiles[x + y * width] = tId;
						String key = "x:"+(x*Config.BOX_SIZE) +",y:"+ (y*Config.BOX_SIZE) +",xb:"+((x*Config.BOX_SIZE)+Config.BOX_SIZE)+",yb"+((y*Config.BOX_SIZE)+Config.BOX_SIZE);
						if (!blocks.containsKey(key)) {
						  blocks.put(key, new TileMap(tId, new Geo(new Coordinates(x * Config.BOX_SIZE, y * Config.BOX_SIZE), Config.BOX_SIZE, Config.BOX_SIZE)));
						}
						if (tId == Config.ENTRANCE) {
							Portals.setEntrance(t, x, y);
						}
						if (tId == Config.EXIT) {
							Portals.setExit(t, x, y);
						}
						break tileCheck;
					}
				}
			}
		}
        System.out.println(blocks.size()+"=="+(Config.MAP_X_SIZE*Config.MAP_Y_SIZE));
//		System.out.println("entrance "+Portals.getEntrance().getX() +":"+ Portals.getEntrance().getY());
//		System.out.println("exit "+Portals.getExit().getX() +":"+ Portals.getExit().getY());
	}

	public void alterTile(int x, int y, Tile newTile) {
		tiles[x + y * width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
	}

	public void setOffset(Screen screen) {
		this.xOffset = -(screen.getxOffset() + screen.width / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
		this.yOffset = -(screen.getyOffset() + screen.height / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
	}

//	private void generateLevel() {
//		int entrenceY = Utils.randInt(1, Config.MAP_Y_SIZE-2);
//		int exitY = Utils.randInt(1, Config.MAP_Y_SIZE-2);
//		for (int y = 0; y < height; y++) {
//			for (int x = 0; x < width; x++) {
//				if (y == 0 ||
//					x == Config.MAP_X_SIZE-1 ||
//					y == Config.MAP_Y_SIZE-1 || x == 0) {
//
//					if ((x == 0 && y == entrenceY) ||
//						(x == Config.MAP_X_SIZE-1 && y == exitY)) {
//						tiles[x + y * width] = Tile.ENTRANCE.getId();
//					} else {
//						tiles[x + y * width] = Tile.BUSH.getId();
//					}
//
//				} else {
//					if (Utils.randInt(0, 88)==13 &&
//						(x != 1 && y != entrenceY) &&
//						(x != Config.MAP_X_SIZE-1 && y != exitY)) {
//
//						tiles[x + y * width] = Tile.STONE.getId();
//
//					} else {
//						tiles[x + y * width] = Tile.GRASS.getId();
//					}
//				}
////				if (x * y % 10 == 0) {
////					tiles[x + y * width] = Tile.BUSH.getId();
////				} else {
////					tiles[x + y * width] = Tile.GRASS.getId();
////				}
//			}
//		}
//	}

	public void renderTiles(Screen screen, int xOffset, int yOffset) {
		if(xOffset < 0) xOffset = 0;
		if(xOffset > ((width << 4) - screen.width)) xOffset = ((width << 4) - screen.width);
		if(yOffset < 0) yOffset = 0;
		if(yOffset > ((height << 4) - screen.height)) yOffset = ((height << 4) - screen.height);

		screen.setOffset(xOffset, yOffset);

		int xMin = xOffset >> 4;
	    int xMax = (xOffset + screen.width) >> 4;
	    int yMin = yOffset >> 4;
	    int yMax = (yOffset + screen.height) >> 4;

	    for (int y = yMin; y < yMax + 15; y++) {
	      for (int x = xMin; x < xMax + 15; x++) {
	        Tile tile = getTile(x, y);
	        tile.render(screen);
	      }
	    }

//		for(int y = 0; y < height; y++) {
//			for(int x = 0; x < width; x++) {
//				Tile tile = getTile(x,y);
//				tile.render(screen, this, x << 3, y << 3);
//			}
//		}
//
//		renderIds(screen);
	}

//	private void renderIds(Screen screen) {
//		if (Config.LEVEL_SHOW_IDS) {
//			for (int x = 0; x < width; x++) {
//				int color = Colors.get(-1, -1, -1, 000);
//				if(x % 10 == 0 && x != 0) {
//					color = Colors.get(-1, -1, -1, 500);
//				}
//				Font.render((x%10)+"", screen, 0 + (x * 8), 0, color, 1);
//			}
//			for (int y = 0; y < height; y++) {
//				int color = Colors.get(-1, -1, -1, 000);
//				if(y % 10 == 0 && y != 0) {
//					color = Colors.get(-1, -1, -1, 500);
//				}
//				Font.render((y%10)+"", screen, 0, 0 + (y * 8), color, 1);
//			}
//		}
//	}

    public byte[] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height) {
			return TileTypes.get("VOID").get(this, x, y);
//			return TileTypes.VOID;
		}
		return TileTypes.tiles[tiles[x + y * width]];
	}

    public void tick() {
    	for (Tile t : TileTypes.tiles) {
    		if (t == null) {
    			break;
    		}
    		t.tick();
    	}

    	for (NpcType n : npcs) {
    		n.tick();
    	}

    	if (getCamera() != null) {
    		getCamera().tick();
	    }

    	getStore().tick();
	}

    public void renderStore(Screen screen) {
    	getStore().render(screen);
    }

    public void renderNpcs(Screen screen) {
    	for (NpcType n : npcs) {
    		n.render(screen);
    	}
    }

	public int getWidthInTiles() {
		return width;
	}

	public int getHeightInTiles() {
		return height;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void actionPerformed(InputEvent event) {
		if (event.key.id == Game.instance.getInputHandler().esc.id
				&& event.type == InputEventType.PRESSED) {
			Game.instance.showGui(new GuiPause(Game.instance, Game.instance.getWidth(), Game.instance.getHeight()));
		}
	}

	public void addNpc(NpcType npc) {
		npcs.add(npc);
	}

	public int getWave() {
		return wave;
	}

	public void setWave(int wave) {
		this.wave = wave;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

    public HashMap<String, TileMap> getBlocks() {
        return blocks;
    }

    public void setBlocks(HashMap<String, TileMap> blocks) {
        this.blocks = blocks;
    }
}
