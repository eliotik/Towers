package org.game.towers.units.towers;

import java.awt.Rectangle;

import org.game.towers.game.Config;
import org.game.towers.game.Game;
import org.game.towers.gfx.Screen;
import org.game.towers.units.ShootingUnit;

public class Tower extends ShootingUnit {

	private static final long serialVersionUID = 1L;

	private int price;
	private int radarViewSize;
	private int resources;

	@Override
	public void tick() {
		super.tick();
		Rectangle r1 = new Rectangle((int)(((Game.getInstance().getScreen().getMousePosition().getX()/Config.SCALE)+Game.getInstance().getScreen().getxOffset()) - Config.BOX_SIZE/2), (int)(((Game.getInstance().getScreen().getMousePosition().getY()/Config.SCALE)+Game.getInstance().getScreen().getyOffset()) - Config.BOX_SIZE/2), Config.BOX_SIZE, Config.BOX_SIZE);
		Rectangle r2 = new Rectangle((int)getX(), (int)getY(), Config.BOX_SIZE, Config.BOX_SIZE);

		if (r2.intersects(r1)) {
			System.out.println(getTypeName());
		}
	}

	@Override
	public void render(Screen screen) {
		screen.renderUnit((int) getX(), (int) getY(), this, getMirrorMask(), getScale());
	}

	@Override
	public boolean hasCollided(int xa, int ya) {
		return false;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getRadarViewSize() {
		return radarViewSize;
	}

	public void setRadarViewSize(int radarViewSize) {
		this.radarViewSize = radarViewSize;
	}

	public int getResources() {
		return resources;
	}

	public void setResources(int resources) {
		this.resources = resources;
	}
}
