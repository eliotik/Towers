package org.game.towers.level;

import org.game.towers.configs.Config;
import org.game.towers.configs.Npcs;
import org.game.towers.game.Game;
import org.game.towers.geo.Coordinates;
import org.game.towers.geo.Geo;
import org.game.towers.gfx.Camera;
import org.game.towers.gfx.Screen;
import org.game.towers.gui.GuiLost;
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
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

public class Level implements GameActionListener {

	private String name;
    private HashMap<String, TileMap> blocks = new HashMap<String, TileMap>();
    private volatile Node[][] jpsTiles;
	private Tile[] tiles;
	private int width;
	private int height;
	private int xOffset = 0;
	private int yOffset = 0;
	private int wave = 1;
	private String imagePath;
	private BufferedImage image;
	private volatile List<Unit> units = new ArrayList<Unit>();
	private Store store;
	private Camera camera;
	private long nextWave = System.currentTimeMillis() + Config.LEVEL_WAVE_TIMEOUT;

	private int playerHealth = Config.DEFAULT_PLAYER_HEALTH;
	private int playerMoney = Config.DEFAULT_PLAYER_MONEY;
	private int playerResource = Config.DEFAULT_PLAYER_RESOURCE;

	public Level(String imagePath) {
		setImagePath(imagePath);
		loadLevelFromFile();
		initStore();
		initCamera();
        generateGridForJSP();
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

//		if (System.currentTimeMillis() < getNextWave()) return;
//		setNextWave(System.currentTimeMillis() + Config.LEVEL_WAVE_TIMEOUT);

		switch(getWave()) {
			case 1:
				NpcType bulb = UnitFactory.getNpc(Npcs.BULB);
//				NpcType bulb2 = UnitFactory.getNpc(Npcs.BULB);
//				NpcType bulb3 = UnitFactory.getNpc(Npcs.BULB);
//				NpcType drone1 = UnitFactory.getNpc(Npcs.DRONE);
//				NpcType drone2 = UnitFactory.getNpc(Npcs.DRONE);
//				NpcType vent1 = UnitFactory.getNpc(Npcs.VENT);
				NpcType vent2 = UnitFactory.getNpc(Npcs.VENT);
				if (bulb != null) {
//                    drone2.setLevel(this);
//                    drone2.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE*16);
//                    drone2.setY(Portals.getEntrance().getCoordinates().getY());
//                    addNpc(drone2);

                    vent2.setLevel(this);
                    vent2.setX(Portals.getEntrance().getCoordinates().getX());
                    vent2.setY(Portals.getEntrance().getCoordinates().getY());



                    bulb.setLevel(this);
                    bulb.setX(Portals.getEntrance().getCoordinates().getX() - 2*Config.BOX_SIZE);
                    bulb.setY(Portals.getEntrance().getCoordinates().getY() + 7*Config.BOX_SIZE);





                    addNpc(bulb);
                    addNpc(vent2);

//					bulb3.setLevel(this);
//					bulb3.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE*13);
//					bulb3.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE*3);
//					addNpc(bulb3);
////
//					drone1.setLevel(this);
//					drone1.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE*14);
//					drone1.setY(Portals.getEntrance().getCoordinates().getY());
//					addNpc(drone1);
//


//					vent1.setLevel(this);
//					vent1.setX(Portals.getEntrance().getCoordinates().getX());
//					vent1.setY(Portals.getEntrance().getCoordinates().getY());
//					addNpc(vent1);

//					vent2.setLevel(this);
//					vent2.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE*13);
//					vent2.setY(Portals.getEntrance().getCoordinates().getY() - Config.BOX_SIZE*16);
//					addNpc(vent2);
				}
		}
	}

	private void loadLevelFromFile() {
		try {
			setImage(ImageIO.read(Level.class.getResource(this.getImagePath())));
			setWidth(getImage().getWidth());
			setHeight(getImage().getHeight());
			setTiles(new Tile[getWidth() * getHeight()]);
			loadTiles();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Tile parseTileFromColor(int color, int x, int y) {
		for (TileTypes tt : TileTypes.types) {
			if (tt.get().getLevelColor() == color) {
				return tt.get(this, x, y, false);
			}
		}
		return TileTypes.get("VOID").get(this, x, y, true);
	}

	private void loadTiles() {
		int[] tiles = new int[getWidth() * getHeight()];
		tiles = getImage().getRGB(0, 0, getWidth(), getHeight(), null, 0, getWidth());
        for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Tile tile = parseTileFromColor(tiles[x + y * getWidth()], x, y);
				String key = "x:"+tile.getX()+"y:"+tile.getY()+"bx:"+(tile.getX()+Config.BOX_SIZE)+"by:"+(tile.getY()+Config.BOX_SIZE);
				getTiles()[x + y * getWidth()] = tile;
				if (!blocks.containsKey(key) && tile.isSolid()) {
                    blocks.put(key, new TileMap(tile, new Geo(new Coordinates(tile.getX(), tile.getY()), Config.BOX_SIZE)));
				}
			}
		}
        Portals.setEntrance(getEntranceLocation());
		Portals.setExit(getExitLocation());

	}

    public Node[][] generateGridForJSP() {
        Node[][] jpsTilesT = new Node[getWidth()*Config.BOX_SIZE][getHeight()*Config.BOX_SIZE];
        for(int y = 0; y < getHeight()*Config.BOX_SIZE; y++) {
            for(int x = 0; x < getWidth()*Config.BOX_SIZE; x++) {
                int xa = x >> Config.COORDINATES_SHIFTING;
                int ya = y >> Config.COORDINATES_SHIFTING;
                jpsTilesT[x][y] = new Node(x, y);
                jpsTilesT[x][y].setPass(!getTile(xa, ya).isSolid());
            }
        }
        return jpsTilesT;
    }

	public synchronized void alterTile(int x, int y, Tile newTile) {
		getTiles()[x + y * getWidth()] = newTile;
		getImage().setRGB(x, y, newTile.getLevelColor());
	}

    public synchronized Tile[] getTiles() {
        return tiles;
    }

	public synchronized Tile getTile(int index) {
		return getTiles()[index];
	}

    public synchronized Tile getTile(int x, int y) {
		if (0 > x || x >= getWidth() || 0 > y || y >= getHeight()) {
			return TileTypes.get("VOID").get(this, x, y, true);
		}
		return getTiles()[x + y * getWidth()];
	}

    public void tick() {
		for (int i = 0; i < tiles.length; i++) {
			Tile tile = getTile(i);
			tile.tick();
		}

		getStore().tick();

		synchronized (getUnits()) {
			for (Iterator<Unit> it = getUnits().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();
				unit.tick();
				if (unit.isFinished()) {
					setPlayerHealth(getPlayerHealth() - 1);
					it.remove();
				}
			}
		}

    	if (getCamera() != null) {
    		getCamera().tick();
	    }

    	if (getPlayerHealth() <= 0) {
    		Game.instance.showGui(new GuiLost(Game.instance, Game.instance.getWidth(), Game.instance.getHeight()));
    	}
	}

	public Coordinates getEntranceLocation() {
		return getTileLocation(Config.TILE_ENTRANCE);
	}

	public Coordinates getExitLocation() {
		return getTileLocation(Config.TILE_EXIT);
	}

	public Coordinates getTileLocation(String name) {
		Coordinates coords = new Coordinates();
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Tile tile = getTile(x, y);
				if (tile.getName().equals(name)) {
					coords.setX(x << Config.COORDINATES_SHIFTING);
					coords.setY(y << Config.COORDINATES_SHIFTING);
					return coords;
				}
			}
		}
		return coords;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public synchronized Tile getBackgroundTile(int x, int y) {
		return TileTypes.get(Config.TILE_GRASS).get(this, x, y, true);
	}

	@Override
	public void actionPerformed(InputEvent event) {
		if (event.key.id == Game.instance.getInputHandler().esc.id
				&& event.type == InputEventType.PRESSED) {
			Game.instance.showGui(new GuiPause(Game.instance, Game.instance.getWidth(), Game.instance.getHeight()));
		}
	}

	public void addNpc(NpcType npc) {
		synchronized (getUnits()) {
			getUnits().add(npc);
		}
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

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void setTiles(Tile[] tiles) {
		this.tiles = tiles;
	}

	public void render(Screen screen) {
		int xOffset = getCamera().getX();
		int yOffset = getCamera().getY();

		if(xOffset < 0) xOffset = 0;
		if(xOffset > ((getWidth() << Config.COORDINATES_SHIFTING) - screen.getWidth())) xOffset = ((getWidth() << Config.COORDINATES_SHIFTING) - screen.getWidth());
		if(yOffset < 0) yOffset = 0;
		if(yOffset > ((getHeight() << Config.COORDINATES_SHIFTING) - screen.getHeight())) yOffset = ((getHeight() << Config.COORDINATES_SHIFTING) - screen.getHeight());

		screen.setOffset(xOffset, yOffset);

		int xMin = xOffset >> Config.COORDINATES_SHIFTING;
	    int xMax = (xOffset + screen.getWidth()) >> Config.COORDINATES_SHIFTING;
	    int yMin = yOffset >> Config.COORDINATES_SHIFTING;
	    int yMax = (yOffset + screen.getHeight()) >> Config.COORDINATES_SHIFTING;

		for (int y = yMin; y < yMax + Config.BOX_SIZE_FIXED; y++) {
			for (int x = xMin; x < xMax + Config.BOX_SIZE_FIXED; x++) {
				Tile tile = getTile(x, y);
				tile.render(screen);
			}
		}

		synchronized (getUnits()) {
			for (Iterator<Unit> it = getUnits().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();
				unit.render(screen);
			}
		}

		screen.renderLevelGui();
	}

	public long getNextWave() {
		return nextWave;
	}

	public void setNextWave(long nextWave) {
		this.nextWave = nextWave;
	}

	public List<Unit> getUnits() {
		return units;
	}

	public void setUnits(List<Unit> units) {
		this.units = units;
	}

	public int getPlayerResource() {
		return playerResource;
	}

	public void setPlayerResource(int playerResource) {
		this.playerResource = playerResource;
	}

	public int getPlayerHealth() {
		return playerHealth;
	}

	public void setPlayerHealth(int playerHealth) {
		this.playerHealth = playerHealth;
	}

	public int getPlayerMoney() {
		return playerMoney;
	}

	public void setPlayerMoney(int playerMoney) {
		this.playerMoney = playerMoney;
	}

    public Node[][] getTilesForJPS() {
        return jpsTiles;
    }

    public void setTilesForJSP(Node[][] tilesForJSP) {
        this.jpsTiles = tilesForJSP;
    }

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
