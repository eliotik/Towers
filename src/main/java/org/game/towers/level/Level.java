package org.game.towers.level;

import org.game.towers.configs.Config;
import org.game.towers.configs.Npcs;
import org.game.towers.configs.Towers;
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
import org.game.towers.towers.TowerType;
import org.game.towers.units.Unit;
import org.game.towers.units.UnitFactory;
import org.game.towers.workers.Utils;
//import org.game.towers.workers.Utils;
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class Level implements GameActionListener {

	private String name;
    private HashMap<String, TileMap> blocks = new HashMap<String, TileMap>();
    private HashMap<Integer, Node[][]>  jpsTilesHashMap = new HashMap<Integer, Node[][]>();
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

    private long waveTime = 0;
    private long waveTimeInterval;
    private long npcLastStart = 0;
    private int quantity;
    private int remainingNpc;
    private HashMap<Integer, Integer> waveCheck = new HashMap<Integer, Integer>();
    private Random random = new Random();
//    private Npcs npcs = new Npcs();
//    private Class cls = npcs.getClass();

    public Level(String imagePath) {
		setImagePath(imagePath);
		loadLevelFromFile();
		initStore();
		initCamera();
		initFog(Game.instance.getScreen());

        TowerType tower = UnitFactory.getTower(Towers.BULB);
        tower.setLevel(this);
        tower.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE);
        tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE);
        addUnit(tower);

        tower = UnitFactory.getTower(Towers.BULB);
        tower.setLevel(this);
        tower.setX(Portals.getEntrance().getCoordinates().getX());
        tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE * 8);
        addUnit(tower);

        tower = UnitFactory.getTower(Towers.BULB);
        tower.setLevel(this);
        tower.setX(Portals.getEntrance().getCoordinates().getX() - Config.BOX_SIZE * 10);
        tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE * 11);
        addUnit(tower);

        tower = UnitFactory.getTower(Towers.BULB);
        tower.setLevel(this);
        tower.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE);
        tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE * 12);
        addUnit(tower);
	}

	private void initFog(Screen screen) {
		//System.out.println(getWidth()+"/"+getHeight()+", "+screen.getWidth()+"/"+screen.getHeight()+", "+Config.SCREEN_WIDTH+"/"+Config.SCREEN_HEIGHT+", "+Config.REAL_SCREEN_WIDTH+"/"+Config.REAL_SCREEN_HEIGHT);
		if (Config.DEFAULT_LEVEL_USE_FOG) {
			screen.setFog(new int[Config.REAL_SCREEN_WIDTH * Config.REAL_SCREEN_HEIGHT]);
			refineFogLayer(
				Portals.getExit().getCoordinates().getX() + Config.BOX_SIZE/2,
				Portals.getExit().getCoordinates().getY() + Config.BOX_SIZE/2,
				Config.DEFAULT_LEVEL_ENTRANCE_RADAR_VIEW_SIZE
			);
		}
	}

	private void initCamera() {
		if (getCamera() == null) {
			setCamera(new Camera(this, 0, 0, Game.instance.getInputHandler(), 4));
		}
	}

	private void initStore() {
		setStore(new Store());
	}

    private void setNextWave() {
        if (waveTime < System.currentTimeMillis()) {
            waveTime = System.currentTimeMillis() + Config.LEVEL_WAVE_TIMEOUT;
            wave++;
        }

        npcQuantity(wave);
//        if (waveCheck.size() == 0){
//            waveCheck.add(quantity);
//        }
        if (waveCheck.get(wave) == null) {
            remainingNpc += quantity;
            waveCheck.put(wave, quantity);
            waveTimeInterval = Config.LEVEL_WAVE_TIMEOUT / remainingNpc;
        }
    }


    private void npcQuantity(int wave) {
        quantity = (int)Math.round(wave * Config.LEVEL_WAVE_MULTIPLIER);
    }

    private double randomInRange(double min, double max) {
        Random random = new Random();
        double range = max - min;
        double scaled = random.nextDouble() * range;
        double shifted = scaled + min;
        return shifted;
    }

    private int randomIndexByAmount(int amount, int length) {
        int diff = Math.abs(amount - length);
        if (diff >= length) {
            return randomIndexByAmount(diff, length);
        } else {
            int result = /*Utils.randInt(0, amount);*/random.nextInt(amount);
            return result;
        }
    }

    private String randomUnitType(){
        String type = "";
        int npcTypeIndex = 0;
        Field[] npcsNames = Npcs.class.getDeclaredFields();
        if (getAmountNpcsTypesByWave() <= npcsNames.length - 1) {
            if (getAmountNpcsTypesByWave() == 0) {
                try {
                    type = (String)npcsNames[npcTypeIndex].get(Npcs.class);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return  type;
            }
            npcTypeIndex = /*Utils.randInt(0, getAmountNpcsTypesByWave());*/random.nextInt(getAmountNpcsTypesByWave());
            try {
                type = (String)npcsNames[npcTypeIndex].get(Npcs.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            int randomIndexByAmount = randomIndexByAmount(getAmountNpcsTypesByWave(), npcsNames.length);
            npcTypeIndex = 0;
            if (randomIndexByAmount > 0) {
                npcTypeIndex = /*Utils.randInt(0, randomIndexByAmount);*/random.nextInt(randomIndexByAmount);
            }
            try {
                type = (String)npcsNames[npcTypeIndex].get(Npcs.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return  type;
    }

    private int getAmountNpcsTypesByWave(){
        int amountTypes = (wave / 2);

        return amountTypes;
    }

	public void generateNpcs() {
        setNextWave();
        if (remainingNpc > 0 && npcLastStart < System.currentTimeMillis()) {
            NpcType npc = UnitFactory.getNpc(randomUnitType());
            npc.setLevel(this);
            npc.setX(Portals.getEntrance().getCoordinates().getX());
            npc.setY(Portals.getEntrance().getCoordinates().getY());
            addUnit(npc);
            npcLastStart = System.currentTimeMillis() + waveTimeInterval;
            remainingNpc--;
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

    public Node[][] generateGridForJSP(int unitId) {
        Node[][] jpsTiles = jpsTilesHashMap.get(unitId);
        if (jpsTiles == null) {
            jpsTiles = new Node[getWidth()*Config.BOX_SIZE][getHeight()*Config.BOX_SIZE];
            for(int y = 0; y < getHeight()*Config.BOX_SIZE; y++) {
                for(int x = 0; x < getWidth()*Config.BOX_SIZE; x++) {
                    int xa = x >> Config.COORDINATES_SHIFTING;
                    int ya = y >> Config.COORDINATES_SHIFTING;
                    jpsTiles[x][y] = new Node(x, y);
                    jpsTiles[x][y].setPass(!getTile(xa, ya).isSolid());
                }
                jpsTilesHashMap.put(unitId, jpsTiles);
            }
        }
        return jpsTiles;
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

        generateNpcs();

		synchronized (getUnits()) {
			for (Iterator<Unit> it = getUnits().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();
				unit.tick();
				if (unit instanceof NpcType && unit.isFinished()) {
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

	public void addUnit(Unit unit) {
		synchronized (getUnits()) {
			getUnits().add(unit);
			if (unit instanceof TowerType) {
				if (Config.DEFAULT_LEVEL_USE_FOG) {
					double x = unit.getX() + Config.BOX_SIZE/2;
					double y = unit.getY() + Config.BOX_SIZE/2;
					int radarSize = ((TowerType) unit).getRadarViewSize();
					refineFogLayer(x, y, radarSize);
				}
			}
		}
	}

	private void refineFogLayer(double x, double y, int radarSize) {
		HashMap<Coordinates, Integer> circle = Utils.getCirclePixels(radarSize, x, y);
		Iterator<Entry<Coordinates, Integer>> it = circle.entrySet().iterator();
		Screen screen = Game.instance.getScreen();
		while (it.hasNext()) {
		    Map.Entry data = (Map.Entry)it.next();
		    Coordinates coordinates = (Coordinates) data.getKey();
		    int pixelIndex = coordinates.getX() + coordinates.getY() * Game.instance.getScreen().getWidth();
		    if (pixelIndex < screen.getFog().length) {
		    	screen.getFog()[pixelIndex] = (int) data.getValue();
		    }
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

		if (Config.DEFAULT_LEVEL_USE_FOG)
			screen.renderFog();

		screen.renderLevelGui();
	}

	public long getNextWave() {
		return nextWave;
	}

//	public void setNextWave(long nextWave) {
//		this.nextWave = nextWave;
//	}

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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
}
