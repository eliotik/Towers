package org.game.towers.units;

import java.util.ArrayList;
import java.util.List;

import org.game.towers.buildings.BuildingType;
import org.game.towers.bullets.BulletType;
import org.game.towers.bullets.BulletTypesCollection;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.npcs.NpcType;
import org.game.towers.npcs.NpcTypesCollection;
import org.game.towers.towers.TowerType;
import org.game.towers.towers.TowerTypesCollection;
import org.hamcrest.Matchers;
import org.apache.commons.lang3.SerializationUtils;

import static ch.lambdaj.Lambda.*;

public class UnitFactory {

	public static NpcType getNpc(String type) {
		try {
			List<NpcType> types = filter(having(on(NpcType.class).getId(), Matchers.equalTo(type)),
					NpcTypesCollection.getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find NPC unit by type: " + type);
				return null;
			}
			NpcType unit = (NpcType) types.get(0);
			return SerializationUtils.clone(unit);
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize NPC unit: " + type);
		}
		return null;
	}

	public static TowerType getTower(String type){
		try {
			List<TowerType> types = filter(having(on(TowerType.class).getId(), Matchers.equalTo(type)),
					TowerTypesCollection.getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find NPC unit by type: " + type);
				return null;
			}
			TowerType unit = (TowerType) types.get(0);
			return SerializationUtils.clone(unit);
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize Tower unit: " + type);
		}
		return null;
	}

	public static BulletType getBullet(String type, Unit owner){
		try {
			List<BulletType> types = filter(having(on(BulletType.class).getId(), Matchers.equalTo(type)),
					BulletTypesCollection.getItems());
			if (types == null || types.size() == 0) {
				Game.debug(DebugLevel.WARNING, "Could not find BULLET by type: " + type);
				return null;
			}
			BulletType unit = (BulletType) types.get(0);
			BulletType bullet = SerializationUtils.clone(unit);
			bullet.setOwner(owner);
			return bullet;
		} catch (Exception e) {
			e.printStackTrace();
			Game.debug(DebugLevel.ERROR, "Could not initialize Bullet unit: " + type);
		}
		return null;
	}

	public static ArrayList<BuildingType> getBuilding(){
		return null;
	}
}
