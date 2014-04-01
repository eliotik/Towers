package org.game.towers.game.level;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.game.level.tiles.Tile;
import org.game.towers.game.level.tiles.TileTypes;
import org.game.towers.gfx.Camera;
import org.game.towers.gfx.Screen;
import org.game.towers.gui.GuiLost;
import org.game.towers.gui.GuiPause;
import org.game.towers.handlers.GameActionListener;
import org.game.towers.handlers.InputEvent;
import org.game.towers.handlers.InputEventType;
import org.game.towers.handlers.MouseInputEvent;
import org.game.towers.units.Unit;
import org.game.towers.units.UnitFactory;
import org.game.towers.units.bullets.Bullet;
import org.game.towers.units.collections.ModificatorsCollection;
import org.game.towers.units.npcs.Npc;
import org.game.towers.units.towers.Tower;
import org.game.towers.units.towers.Towers;
import org.game.towers.units.towers.modificators.Modificator;
import org.game.towers.units.towers.modificators.Modificators;
import org.game.towers.workers.Algorithms.JumpPointSearch.Node;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Level implements GameActionListener {

	private String name;
    private HashMap<Integer, Node[][]>  jpsTilesHashMap = new HashMap<Integer, Node[][]>();
	private Tile[] tiles;
	private int width;
	private int height;
	private int xOffset = 0;
	private int yOffset = 0;
	private String imagePath;
	private BufferedImage image;
	private volatile List<Unit> units = new ArrayList<Unit>();
	private volatile List<Unit> bullets = new ArrayList<Unit>();
	private Store store;
	private Camera camera;
	private long nextWave = System.currentTimeMillis() + Config.LEVEL_WAVE_TIMEOUT;
	private volatile TileTypes backgroundTileTypes;
	private volatile TileTypes voidTileTypes;

	private int playerHealth = Config.DEFAULT_PLAYER_HEALTH;
	private int playerMoney = Config.DEFAULT_PLAYER_MONEY;
	private int playerResource = Config.DEFAULT_PLAYER_RESOURCE;

    private Wave currentWave;

    public Level(String imagePath) {
		setImagePath(imagePath);
		loadLevelFromFile();
		setBackgroundTileTypes(TileTypes.get(Config.TILE_GRASS));
		setVoidTileTypes(TileTypes.get("VOID"));
		setCurrentWave(new Wave(this));

		if (!Config.DEFAULT_LEVEL_USE_WAVES) {
			Npc npc = UnitFactory.getNpc(getCurrentWave().randomUnitType());
	        npc.setLevel(this);
	        npc.setX(Portals.getEntrance().getCoordinates().getX());
	        npc.setY(Portals.getEntrance().getCoordinates().getY());
	        addUnit(npc);
		}
	}

    public void initComponents() {
		initStore(Game.getInstance().getScreen());
		initCamera();
		initFog(Game.getInstance().getScreen());
    }

    public void addTowers() {
    	 Tower tower = UnitFactory.getTower(Towers.BULB);
         tower.setLevel(this);
         tower.setX(240);
         tower.setY(208);
         addUnit(tower);

//         System.out.println("TOWER POSITION: "+tower.getX()+" : "+tower.getY());
//         tower = UnitFactory.getTower(Towers.BLOCKPOST);
//         tower.setLevel(this);
//         tower.setX(Portals.getEntrance().getCoordinates().getX());
//         tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE * 8);
//         addUnit(tower);
//
//         tower = UnitFactory.getTower(Towers.BULB);
//         tower.setLevel(this);
//         tower.setX(Portals.getEntrance().getCoordinates().getX() - Config.BOX_SIZE * 10);
//         tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE * 11);
//         addUnit(tower);
//
//         tower = UnitFactory.getTower(Towers.BULB);
//         tower.setLevel(this);
//         tower.setX(Portals.getEntrance().getCoordinates().getX() + Config.BOX_SIZE);
//         tower.setY(Portals.getEntrance().getCoordinates().getY() + Config.BOX_SIZE * 12);
//         addUnit(tower);
    }

	private void initFog(Screen screen) {
//		System.out.println(getWidth()+"/"+getHeight()+", "+screen.getWidth()+"/"+screen.getHeight()+", "+Config.SCREEN_WIDTH+"/"+Config.SCREEN_HEIGHT+", "+Config.REAL_SCREEN_WIDTH+"/"+Config.REAL_SCREEN_HEIGHT);
		if (Config.DEFAULT_LEVEL_USE_FOG) {
			HashMap<String, Integer> fog = new HashMap<String, Integer>();
			for (int y = 0; y < getHeight() * Config.BOX_SIZE; y++) {
				for (int x = 0; x < getWidth() * Config.BOX_SIZE; x++) {
					String key = "x"+x+"y"+y;
					fog.put(key, 0);
				}
			}
			screen.setFog(fog);
			screen.refineFogLayer(
				Portals.getExit().getCoordinates().getX() + Config.BOX_SIZE/2,
				Portals.getExit().getCoordinates().getY() + Config.BOX_SIZE/2,
				Config.DEFAULT_LEVEL_ENTRANCE_RADAR_VIEW_SIZE
			);
		}
	}

	private void initCamera() {
		if (getCamera() == null) {
			setCamera(new Camera(this, 0, 0, Game.getInstance().getInputHandler(), 4));
		}
	}

	private void initStore(Screen screen) {
		setStore(new Store(screen));
	}

	public void generateNpcs() {
        getCurrentWave().setNextWave();
        getCurrentWave().generateNpcs();
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
		for (TileTypes tt : TileTypes.getTypes()) {
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
				getTiles()[x + y * getWidth()] = tile;
			}
		}
        locatePortals();
	}

    private void locatePortals() {
		for (int y = 0; y < getHeight(); ++y) {
			for (int x = 0; x < getWidth(); ++x) {
				Tile tile = getTile(x, y);
				switch (tile.getName()) {
				case Config.TILE_ENTRANCE:
					Portals.addEntrance(Portals.createPortal(x, y));
					break;
				case Config.TILE_EXIT:
					Portals.addExit(Portals.createPortal(x, y));
					break;
				}
			}
		}
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

    public Node[][] generateGridForJSP() {
        Node[][] jpsTiles = new Node[getWidth()*Config.BOX_SIZE][getHeight()*Config.BOX_SIZE];
        for(int y = 0; y < getHeight()*Config.BOX_SIZE; y++) {
            for(int x = 0; x < getWidth()*Config.BOX_SIZE; x++) {
                int xa = x >> Config.COORDINATES_SHIFTING;
                int ya = y >> Config.COORDINATES_SHIFTING;
                jpsTiles[x][y] = new Node(x, y);
                jpsTiles[x][y].setPass(!getTile(xa, ya).isSolid());
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
			return getVoidTileTypes().get(this, x, y, true);
		}
		return getTiles()[x + y * getWidth()];
	}

    public void tick() {
		for (int i = 0; i < tiles.length; i++) {
			Tile tile = getTile(i);
			tile.tick();
		}

		getStore().tick();

		if (Config.DEFAULT_LEVEL_USE_WAVES) {
			generateNpcs();
		}

		updateBullets();
		updateUnits();

    	if (getCamera() != null) {
    		getCamera().tick();
	    }

    	if (getPlayerHealth() <= 0) {
    		Game.getInstance().showGui(new GuiLost(Game.getInstance(), Game.getInstance().getWidth(), Game.getInstance().getHeight()));
    	}
	}

	private void updateUnits() {
		synchronized (getUnits()) {
			for (Iterator<Unit> it = getUnits().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();
				unit.tick();

				if (unit instanceof Npc) {
					Npc npc = (Npc) unit;
					if (npc.isCanBeRemoved()) {
						if (npc.isDead()) {
							setPlayerMoney(getPlayerMoney() + npc.getAward());
							it.remove();
						} else if (npc.isFinished()) {
							setPlayerHealth(getPlayerHealth() - 1);
							it.remove();
						}
					}
				}
			}
		}
	}

	private void updateBullets() {
		synchronized (getUnits()) {
			for (Iterator<Unit> it = getUnits().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();

				for (Iterator<Unit> bulletIt = getBullets().iterator(); bulletIt.hasNext();) {
					Unit bulletUnit = (Unit) bulletIt.next();
					bulletUnit.tick();
					if (bulletUnit instanceof Bullet && unit instanceof Npc && !unit.isDead()) {
						Bullet bullet = (Bullet) bulletUnit;
//						if (bullet.getTileX() == unit.getTileX() && bullet.getTileY() == unit.getTileY()) {
						if (bullet.hasCollision(unit)) {
							Npc npc = (Npc) unit;
							npc.setHealth(npc.getHealth() - bullet.getDamage());
							if (!bullet.getOwner().getModificator().equals(Modificators.NONE)) {
								Modificator modificator = ModificatorsCollection.getByType(bullet.getOwner().getModificator());
								if (modificator != null) {
									((Npc) unit).addImpact(modificator);
								}
							}
							bulletIt.remove();
						} else if (!bullet.isMoving()) {
							bulletIt.remove();
						}
					}
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public synchronized Tile getBackgroundTile(int x, int y) {
		return getBackgroundTileTypes().get(this, x, y, true);
	}

	@Override
	public void actionPerformed(InputEvent event) {
		if (event.getKey().getId() == Game.getInstance().getInputHandler().getEsc().getId()
				&& event.getType() == InputEventType.PRESSED) {
			Game.getInstance().showGui(new GuiPause(Game.getInstance(), Game.getInstance().getWidth(), Game.getInstance().getHeight()));
		}
	}

	public void addUnit(Unit unit) {
		synchronized (getUnits()) {
			getUnits().add(unit);
			if (unit instanceof Tower) {
				Tower tower = (Tower) unit;
				setPlayerMoney(getPlayerMoney() - tower.getPrice());
				setPlayerResource(getPlayerResource() - tower.getResources());
				if (Config.DEFAULT_LEVEL_USE_FOG) {
					double x = unit.getX() + Config.BOX_SIZE/2;
					double y = unit.getY() + Config.BOX_SIZE/2;
					int radarSize = ((Tower) unit).getRadarViewSize();
					Game.getInstance().getScreen().refineFogLayer(x, y, radarSize);
				}
			}
		}
	}

	public void addBullet(Unit unit) {
		synchronized (getUnits()) {
			getBullets().add(unit);
		}
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

		synchronized (getBullets()) {
			for (Iterator<Unit> it = getBullets().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();
				unit.render(screen);
			}
		}

		synchronized (getUnits()) {
			for (Iterator<Unit> it = getUnits().iterator(); it.hasNext();) {
				Unit unit = (Unit) it.next();
				unit.render(screen);
			}
		}

		getStore().render();
		screen.renderLevelGui();
	}

	public long getNextWave() {
		return nextWave;
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<Unit> getBullets() {
		return bullets;
	}

	public void setBullets(List<Unit> bullets) {
		this.bullets = bullets;
	}

	public Wave getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(Wave currentWave) {
		this.currentWave = currentWave;
	}

	@Override
	public void actionPerformed(MouseInputEvent event) {}

	public TileTypes getBackgroundTileTypes() {
		return backgroundTileTypes;
	}

	public void setBackgroundTileTypes(TileTypes backgroundTileTypes) {
		this.backgroundTileTypes = backgroundTileTypes;
	}

	public TileTypes getVoidTileTypes() {
		return voidTileTypes;
	}

	public void setVoidTileTypes(TileTypes voidTileTypes) {
		this.voidTileTypes = voidTileTypes;
	}
}
