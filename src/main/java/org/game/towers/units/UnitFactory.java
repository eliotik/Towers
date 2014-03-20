package org.game.towers.units;

import java.util.ArrayList;
import java.util.List;

import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.units.buildings.Building;
import org.game.towers.units.bullets.Bullet;
import org.game.towers.units.collections.BulletsCollection;
import org.game.towers.units.collections.NpcsCollection;
import org.game.towers.units.collections.TowersCollection;
import org.game.towers.units.npcs.Npc;
import org.game.towers.units.towers.Tower;
import org.hamcrest.Matchers;
import org.apache.commons.lang3.SerializationUtils;

import static ch.lambdaj.Lambda.*;

public class UnitFactory {

	public static Npc getNpc(String type) {
		try {
			List<Npc> types = filter(having(on(Npc.class).getId(), Matchers.equalTo(type)),
					NpcsCollection.getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find NPC unit by type: " + type);
				return null;
			}
			Npc unit = (Npc) types.get(0);
			return SerializationUtils.clone(unit);
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize NPC unit: " + type);
		}
		return null;
	}

	public static Tower getTower(String type){
		try {
			List<Tower> types = filter(having(on(Tower.class).getId(), Matchers.equalTo(type)),
					TowersCollection.getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find NPC unit by type: " + type);
				return null;
			}
			Tower unit = (Tower) types.get(0);
			return SerializationUtils.clone(unit);
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize Tower unit: " + type);
		}
		return null;
	}

	public static Bullet getBullet(String type, Unit owner){
		try {
			List<Bullet> types = filter(having(on(Bullet.class).getId(), Matchers.equalTo(type)),
					BulletsCollection.getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find BULLET by type: " + type);
				return null;
			}
			Bullet unit = (Bullet) types.get(0);
			Bullet bullet = SerializationUtils.clone(unit);
			bullet.setOwner(owner);
			return bullet;
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize Bullet unit: " + type);
		}
		return null;
	}

	public static ArrayList<Building> getBuilding(){
		return null;
	}
}
