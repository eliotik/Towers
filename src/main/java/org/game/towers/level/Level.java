package org.game.towers.level;

import org.game.towers.configs.Config;
import org.game.towers.gfx.Colors;
import org.game.towers.gfx.Font;
import org.game.towers.gfx.Screen;
import org.game.towers.level.tiles.Tile;
import org.game.towers.units.Unit;
import org.game.towers.workers.Utils;

import java.util.ArrayList;
import java.util.List;

public class Level {

	private byte[] tiles;
    private List<Construction> constructions = new ArrayList<Construction>();
	public int width;
	public int height;
	public int xOffset = 0;
	public int yOffset = 0;
	
	public Level(int width, int height) {
		tiles = new byte[width * height];
		this.width = width;
		this.height = height;
		this.generateLevel();
	}
	
	public void setOffset(Screen screen) {
		this.xOffset = -(screen.xOffset + screen.width / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2); 
		this.yOffset = -(screen.yOffset + screen.height / 2 - (Config.MAP_X_SIZE*Config.BOX_SIZE)/2);
	}

	private void generateLevel() {
		int entrenceY = Utils.randInt(1, Config.MAP_Y_SIZE-2);
		int exitY = Utils.randInt(1, Config.MAP_Y_SIZE-2);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (y == 0 || 
					x == Config.MAP_X_SIZE-1 || 
					y == Config.MAP_Y_SIZE-1 || x == 0) {
					
					if ((x == 0 && y == entrenceY) || 
						(x == Config.MAP_X_SIZE-1 && y == exitY)) {
						tiles[x + y * width] = Tile.ENTRENCE.getId();
					} else {
						tiles[x + y * width] = Tile.BUSH.getId();												
					}
					
				} else {
					if (Utils.randInt(0, 88)==13 && 
						(x != 1 && y != entrenceY) && 
						(x != Config.MAP_X_SIZE-1 && y != exitY)) {
						
						tiles[x + y * width] = Tile.STONE.getId();
						
					} else {
						tiles[x + y * width] = Tile.GRASS.getId();
					}
				}
//				if (x * y % 10 == 0) {
//					tiles[x + y * width] = Tile.BUSH.getId();
//				} else {
//					tiles[x + y * width] = Tile.GRASS.getId();
//				}
			}
		}
	}
	
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
				switch(tile.getId()) {
					case 4://brush
						tile.render(screen, this, x << 3, y << 3, (x + y % 10 > 0) ? 0x00 : 0x02);
						break;
					case 1://stone
						tile.render(screen, this, x << 3, y << 3, (x * y % 10 == 0) ? 0x01 : 0x02);
						break;	
					default:
						tile.render(screen, this, x << 3, y << 3);
						break;
				}
				if (tile.getId() == 4) {
//				if ((x == 0 && y == 10) || (x == Config.MAP_X_SIZE-1 && y == 10)) {
					tile.render(screen, this, x << 3, y << 3, (x + y % 10 > 0) ? 0x00 : 0x02);
				} else {
					tile.render(screen, this, x << 3, y << 3);
				}
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
				Font.render((x%10)+"", screen, 0 + (x * 8), 0, color);
			}
			for (int y = 0; y < height; y++) {
				int color = Colors.get(-1, -1, -1, 000);
				if(y % 10 == 0 && y != 0) {
					color = Colors.get(-1, -1, -1, 500);
				}
				Font.render((y%10)+"", screen, 0, 0 + (y * 8), color);
			}
		}
	}

	private Tile getTile(int x, int y) {
		if (x < 0 || x > width || y < 0 || y > height) return Tile.VOID;
		return Tile.tiles[tiles[x + y * width]];
	}

    public void addConstruction(int x, int y, int width, int height, Unit type) {
        Construction construction = new Construction(x, y, width, height, type);
        constructions.add(construction);
    }

    public List<Construction> getConstructions() {
        return constructions;
    }

    public void tick() {
	}
}
