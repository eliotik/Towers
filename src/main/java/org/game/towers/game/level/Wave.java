package org.game.towers.game.level;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Random;

import org.game.towers.game.Config;
import org.game.towers.units.UnitFactory;
import org.game.towers.units.npcs.Npc;
import org.game.towers.units.npcs.Npcs;

public class Wave {
	private int id;

	private Level level;

    private long waveTime = 0;
    private long waveTimeInterval;
    private long npcLastStart = 0;
    private int quantity;
    private int remainingNpc;
    private HashMap<Integer, Integer> waveCheck = new HashMap<Integer, Integer>();
    private Random random = new Random();

	public Wave(Level level) {
		setId(0);
		setLevel(level);
		Portals.assign();
	}

	private int randomIndexByAmount(int amount, int length) {
        int diff = Math.abs(amount - length);
        if (diff > length) {
            return randomIndexByAmount(diff, length);
        } else {
            int result = random.nextInt(amount);
            return result;
        }
    }

    public String randomUnitType(){
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
            npcTypeIndex = random.nextInt(getAmountNpcsTypesByWave());
            try {
                type = (String)npcsNames[npcTypeIndex].get(Npcs.class);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            int randomIndexByAmount = randomIndexByAmount(getAmountNpcsTypesByWave(), npcsNames.length - 1);
            npcTypeIndex = 0;
            if (randomIndexByAmount > 0) {
                npcTypeIndex = random.nextInt(randomIndexByAmount);
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
        int amountTypes = (getId() / 2);

        return amountTypes;
    }

    public void setNextWave() {
        if (getWaveTime() < System.currentTimeMillis()) {
            setWaveTime(System.currentTimeMillis() + Config.LEVEL_WAVE_TIMEOUT);
            setId(getId() + 1);
            Portals.assign();
        }

        npcQuantity(getId());

        if (getWaveCheck().get(getId()) == null) {
            setRemainingNpc(getRemainingNpc() + getQuantity());
            getWaveCheck().put(getId(), getQuantity());
            setWaveTimeInterval(Config.LEVEL_WAVE_TIMEOUT / getRemainingNpc());
        }
    }

    private void npcQuantity(int wave) {
        quantity = (int)Math.round(wave * Config.LEVEL_WAVE_MULTIPLIER);
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getWaveTime() {
		return waveTime;
	}
	public void setWaveTime(long waveTime) {
		this.waveTime = waveTime;
	}
	public long getWaveTimeInterval() {
		return waveTimeInterval;
	}
	public void setWaveTimeInterval(long waveTimeInterval) {
		this.waveTimeInterval = waveTimeInterval;
	}
	public long getNpcLastStart() {
		return npcLastStart;
	}
	public void setNpcLastStart(long npcLastStart) {
		this.npcLastStart = npcLastStart;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getRemainingNpc() {
		return remainingNpc;
	}
	public void setRemainingNpc(int remainingNpc) {
		this.remainingNpc = remainingNpc;
	}
	public HashMap<Integer, Integer> getWaveCheck() {
		return waveCheck;
	}
	public void setWaveCheck(HashMap<Integer, Integer> waveCheck) {
		this.waveCheck = waveCheck;
	}
	public Random getRandom() {
		return random;
	}
	public void setRandom(Random random) {
		this.random = random;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public void generateNpcs() {
        if (getRemainingNpc() > 0 && getNpcLastStart() < System.currentTimeMillis()) {
            Npc npc = UnitFactory.getNpc(randomUnitType());
            npc.setLevel(getLevel());
            npc.setX(Portals.getEntrance().getCoordinates().getX());
            npc.setY(Portals.getEntrance().getCoordinates().getY());
            npc.setEntrance(Portals.getEntrance());
            npc.setExit(Portals.getExit());
            getLevel().addUnit(npc);
            setNpcLastStart(System.currentTimeMillis() + getWaveTimeInterval());
            setRemainingNpc(getRemainingNpc() - 1);
        }
	}
}
