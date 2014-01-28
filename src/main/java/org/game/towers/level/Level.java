package org.game.towers.level;

import org.game.towers.configs.Config;
import org.game.towers.configs.Npcs;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.geo.Geo;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Font;
import org.game.towers.gfx.Screen;
import org.game.towers.gui.GuiPause;
import org.game.towers.handlers.InputHandler.GameActionListener;
import org.game.towers.handlers.InputHandler.InputEvent;
import org.game.towers.handlers.InputHandler.InputEventType;
import org.game.towers.level.tiles.Tile;
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

public class Level implements NBTCapable, GameActionListener {

	private String name;
//	private byte[] tiles;
	private HashMap<Integer, TileMap> tiles = new HashMap<Integer, TileMap>();
	public int width;
	public int height;
	public int xOffset = 0;
	public int yOffset = 0;
	private int wave = 1;
	private String imagePath;
	private BufferedImage image;
	public List<NpcType> npcs = new ArrayList<NpcType>();
	private Store store;

	public Level(String imagePath) {
//		if (imagePath != null) {
			this.imagePath = imagePath;
			loadLevelFromFile();
			initStore();
//		} else {
//			width = Config.DEFAULT_LEVEL_WIDTH;
//			height = Config.DEFAULT_LEVEL_HEIGHT;
//			tiles = new byte[width * height];
//			generateLevel();
//		}
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

	public Level(Tag tag) {
		this.setName("LEVEL");
		this.loadFromNBT(tag);
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

	public class TileMap {
		private Geo geo;
		private byte tileId;

		public TileMap(byte tId, Geo geo) {
			setTileId(tId);
			setGeo(geo);
		}

		public Geo getGeo() {
			return geo;
		}

		public void setGeo(Geo geo) {
			this.geo = geo;
		}

		public byte getTileId() {
			return tileId;
		}

		public void setTileId(byte id) {
			this.tileId = id;
		}

	}

	public static class Portals {

		private static final byte ENTRANCE = Tile.ENTRANCE.getId();
		private static final byte EXIT = Tile.EXIT.getId();

		private static Portal entrance = new Portal();
		private static Portal exit = new Portal();

		public static class Portal {
			private Tile tile;
			private int x;
			private int y;

			public Tile getTile() {
				return tile;
			}

			public void setTile(Tile tile) {
				this.tile = tile;
			}

			public int getX() {
				return x * Config.BOX_SIZE;
			}

			public void setX(int x) {
				this.x = x;
			}

			public int getY() {
				return y * Config.BOX_SIZE;
			}

			public void setY(int y) {
				this.y = y;
			}
		}

		public static Portal getEntrance() {
			return entrance;
		}

		public static void setEntrance(Tile ent, int x, int y) {
			entrance.setTile(ent);
			entrance.setX(x);
			entrance.setY(y);
		}

		public static Portal getExit() {
			return exit;
		}

		public static void setExit(Tile ex, int x, int y) {
			exit.setTile(ex);
			exit.setX(x);
			exit.setY(y);
		}
	}

	private void loadTiles() {
		int[] tileColors = image.getRGB(0, 0, width, height, null, 0, width);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tileCheck: for (Tile t : Tile.tiles) {
					if (t != null && t.getLevelColor() == tileColors[x + y * width]) {
						byte tId = t.getId();
						tiles.put(x + y * width, new TileMap(tId, new Geo(new Coordinates(x, y), Config.BOX_SIZE, Config.BOX_SIZE)));
						//tiles[x + y * width] = tId;//new tileMap(tId, new Geo(new Coordinates(x, y), Config.BOX_SIZE, Config.BOX_SIZE));
						if (tId == Portals.ENTRANCE) {
							Portals.setEntrance(t, x, y);
						}
						if (tId == Portals.EXIT) {
							Portals.setExit(t, x, y);
						}
						break tileCheck;
					}
				}
			}
		}
//		System.out.println("entrance "+Portals.getEntrance().getX() +":"+ Portals.getEntrance().getY());
//		System.out.println("exit "+Portals.getExit().getX() +":"+ Portals.getExit().getY());
	}

	private void saveLevelToFile() {
		try {
			ImageIO.write(image, "png", new File(Level.class.getResource(imagePath).getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void alterTile(int x, int y, Tile newTile) {
		tiles.get((byte)(x + y * width)).setTileId(newTile.getId());
//		tiles[x + y * width] = newTile.getId();
		image.setRGB(x, y, newTile.getLevelColor());
	}

	public void setOffset(Screen screen) {
		this.xOffset = -(screen.xOffset + screen.width / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
		this.yOffset = -(screen.yOffset + screen.height / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
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
//		if(xOffset < 0) xOffset = 0;
//		if(xOffset > ((width << 3) - screen.width)) xOffset = ((width << 3) - screen.width);
//		if(yOffset < 0) yOffset = 0;
//		if(yOffset > ((height << 3) - screen.height)) yOffset = ((height << 3) - screen.height);

//		screen.setOffset(xOffset, yOffset);
		//-38, -19
//		System.out.println("---------------------");
//		System.out.println(screen.xOffset + screen.width / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
//		System.out.println(screen.yOffset + screen.height / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
//		System.out.println(screen.width);
//		System.out.println("---------------------");
//		System.out.println(Config.REAL_SCREEN_WIDTH/Config.BOX_SIZE/2);
//		System.out.println(Config.MAP_X_SIZE);
//		System.out.println(-(Config.REAL_SCREEN_WIDTH / 2 - Config.MAP_X_SIZE*Config.BOX_SIZE)/Config.BOX_SIZE);
//		System.out.println(-(Config.REAL_SCREEN_HEIGHT / 2 - Config.MAP_Y_SIZE*Config.BOX_SIZE)/Config.BOX_SIZE);
//		screen.setOffset(
//				this.xOffset,
//				this.yOffset);

		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				Tile tile = getTile(x,y);
//				switch(tile.getId()) {
//					case 4://brush
//						tile.render(screen, this, x << 3, y << 3, (x + y % 10 > 0) ? 0x00 : 0x02);
//						break;
//					case 1://stone
//						tile.render(screen, this, x << 3, y << 3, (x * y % 10 == 0) ? 0x01 : 0x02);
//						break;
//					default:
						tile.render(screen, this, x << 3, y << 3);
//						break;
//				}
//				if (tile.getId() == 4) {
////				if ((x == 0 && y == 10) || (x == Config.MAP_X_SIZE-1 && y == 10)) {
//					tile.render(screen, this, x << 3, y << 3, (x + y % 10 > 0) ? 0x00 : 0x02);
//				} else {
//					tile.render(screen, this, x << 3, y << 3);
//				}
			}
		}

		renderIds(screen);
	}

	private void renderIds(Screen screen) {
		if (Config.LEVEL_SHOW_IDS) {
			for (int x = 0; x < width; x++) {
				int color = Colors.get(-1, -1, -1, 000);
				if(x % 10 == 0 && x != 0) {
					color = Colors.get(-1, -1, -1, 500);
				}
				Font.render((x%10)+"", screen, 0 + (x * 8), 0, color, 1);
			}
			for (int y = 0; y < height; y++) {
				int color = Colors.get(-1, -1, -1, 000);
				if(y % 10 == 0 && y != 0) {
					color = Colors.get(-1, -1, -1, 500);
				}
				Font.render((y%10)+"", screen, 0, 0 + (y * 8), color, 1);
			}
		}
	}

    public HashMap<Integer, TileMap> getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height) {
			return Tile.VOID;
		}
		return Tile.tiles[tiles.get((x + y * width)).getTileId()];
	}

	public Geo getTileGeo(int x, int y) {
		if (0 > x || x >= width || 0 > y || y >= height) {
			return null;
		}
		return tiles.get(x + y * width).getGeo();
	}

    public void tick() {
    	for (Tile t : Tile.tiles) {
    		if (t == null) {
    			break;
    		}
    		t.tick();
    	}

    	for (NpcType n : npcs) {
    		n.tick();
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

	public HashMap<Integer, TileMap> getTileIdArray() {
		return tiles;
	}

	@Override
	public void loadFromNBT(Tag tag) {
		this.name = tag.findTagByName("NAME").getValue().toString();
		this.width = (int) tag.findTagByName("WIDTH").getValue();
		this.height = (int) tag.findTagByName("HEIGHT").getValue();
		this.tiles = (HashMap<Integer, TileMap>) tag.findTagByName("TILES").getValue();
		// this.meta = (byte[]) tag.findTagByName("META").getValue();
		// this.overlay = (byte[]) tag.findTagByName("OVERLAY").getValue();
		if (tiles.size() != width * height) {
			Game.debug(Game.DebugLevel.WARNING, "Tile data corrupted!");
			Game.debug(Game.DebugLevel.ERROR, "Error while loading level \""
					+ name + "\"!");
		}
	}

	@Override
	public Tag saveToNBT(Tag tag) {
		tag.addTag(new Tag(Tag.Type.TAG_String, "NAME", this.getName()));
		tag.addTag(new Tag(Tag.Type.TAG_Int, "WIDTH", this.getWidthInTiles()));
		tag.addTag(new Tag(Tag.Type.TAG_Int, "HEIGHT", this.getHeightInTiles()));
		tag.addTag(new Tag(Tag.Type.TAG_Byte_Array, "TILES", this
				.getTileIdArray()));
		tag.addTag(new Tag(Tag.Type.TAG_End, null, null));
		return tag;
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
}
