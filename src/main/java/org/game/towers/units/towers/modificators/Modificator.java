package org.game.towers.units.towers.modificators;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.game.towers.units.Unit;

public class Modificator implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private int duration;
	private int priority;
	private String impact;
	private long startTime;
	private boolean applied;
	private HashMap<String, ArrayList<HashMap<String, Object>>> attributes = new HashMap<String, ArrayList<HashMap<String, Object>>>();

	public void apply(Unit unit) {
		ArrayList<HashMap<String, Object>> attributes = getAttributes().get(unit.getClass().getSimpleName());
		if (attributes != null && attributes.size() > 0) {
			Iterator<HashMap<String, Object>> ait = attributes.iterator();
		    while (ait.hasNext()) {
		        HashMap<String, Object> pairs = (HashMap<String, Object>)ait.next();
		        for ( String key : pairs.keySet() ) {
					try {
						Object currentValue = PropertyUtils.getProperty(unit, (String) key);
						switch(getImpact()) {
						case "negative":
							switch(pairs.get(key).getClass().getSimpleName()) {
							case "Integer":
								currentValue = (int) currentValue - (int) pairs.get(key);
								if ((int) currentValue < 0) currentValue = 0;
								break;
							case "Double":
								currentValue = (double) currentValue - (double) pairs.get(key);
								if ((double) currentValue < 0) currentValue = 0;
								break;
							}
							break;
						case "positive":
							switch(pairs.get(key).getClass().getSimpleName()) {
							case "Integer":
								currentValue = (int) currentValue + (int) pairs.get(key);
								if ((int) currentValue < 0) currentValue = 0;
								break;
							case "Double":
								currentValue = (double) currentValue + (double) pairs.get(key);
								if ((double) currentValue < 0) currentValue = 0;
								break;
							}
							break;
						}
						PropertyUtils.setProperty(unit, (String) key, currentValue);
					} catch (IllegalAccessException
							| InvocationTargetException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}
		        }
		    }
		    setApplied(true);
		}
	}

	public void clear(Unit unit, Unit canonical) {
		ArrayList<HashMap<String, Object>> attributes = getAttributes().get(unit.getClass().getSimpleName());
		if (attributes != null && attributes.size() > 0) {
			Iterator<HashMap<String, Object>> ait = attributes.iterator();
		    while (ait.hasNext()) {
		        HashMap<String, Object> pairs = (HashMap<String, Object>)ait.next();
		        for ( String key : pairs.keySet() ) {
					try {
						Object defaultValue = PropertyUtils.getProperty(canonical, (String) key);
						PropertyUtils.setProperty(unit, (String) key, defaultValue);
					} catch (IllegalAccessException
							| InvocationTargetException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}
		        }
		    }
		}
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public HashMap<String, ArrayList<HashMap<String, Object>>> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, ArrayList<HashMap<String, Object>>> attributes) {
		this.attributes = attributes;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isApplied() {
		return applied;
	}

	public void setApplied(boolean applied) {
		this.applied = applied;
	}

	public void restore(Unit unit) {
		ArrayList<HashMap<String, Object>> attributes = getAttributes().get(this.getClass().getSimpleName());
		if (attributes != null && attributes.size() > 0) {
			Iterator<HashMap<String, Object>> ait = attributes.iterator();
		    while (ait.hasNext()) {
		        HashMap<String, Object> pairs = (HashMap<String, Object>)ait.next();
		        for ( String key : pairs.keySet() ) {
					try {
						Object currentValue = PropertyUtils.getProperty(unit, (String) key);
						switch(getImpact()) {
						case "negative":
							switch(pairs.get(key).getClass().getName()) {
							case "int":
								currentValue = (int) currentValue + (int) pairs.get(key);
								if ((int) currentValue < 0) currentValue = 0;
								break;
							case "double":
								currentValue = (double) currentValue + (double) pairs.get(key);
								if ((double) currentValue < 0) currentValue = 0;
								break;
							case "boolean":
								currentValue = !((boolean) currentValue);
								break;
							}
							break;
						case "positive":
							switch(pairs.get(key).getClass().getName()) {
							case "int":
								currentValue = (int) currentValue - (int) pairs.get(key);
								if ((int) currentValue < 0) currentValue = 0;
								break;
							case "double":
								currentValue = (double) currentValue - (double) pairs.get(key);
								if ((double) currentValue < 0) currentValue = 0;
								break;
							case "boolean":
								currentValue = !((boolean) currentValue);
								break;
							}
							break;
						}
						PropertyUtils.setProperty(unit, (String) key, currentValue);
					} catch (IllegalAccessException
							| InvocationTargetException
							| NoSuchMethodException e) {
						e.printStackTrace();
					}
		        }
		    }
		}
	}
}
