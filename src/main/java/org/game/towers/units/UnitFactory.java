package org.game.towers.units;

import java.util.ArrayList;
import java.util.List;

import org.game.towers.buildings.BuildingType;
import org.game.towers.game.Game;
import org.game.towers.game.Game.DebugLevel;
import org.game.towers.npcs.NpcType;
import org.game.towers.npcs.NpcTypesCollection;
import org.game.towers.towers.TowerType;
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

	public static ArrayList<TowerType> getTower(){
		return null;
	}

	public static ArrayList<BuildingType> getBuilding(){
		return null;
	}
}
