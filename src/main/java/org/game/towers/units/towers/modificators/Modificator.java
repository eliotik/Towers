package org.game.towers.units.towers.modificators;

import java.io.Serializable;
import java.util.HashMap;

public class Modificator implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private int duration;
	private int priority;
	private String impact;
	private long startTime;
	private HashMap<String, HashMap<String, Object>> attributes = new HashMap<String, HashMap<String, Object>>();

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
	public HashMap<String, HashMap<String, Object>> getAttributes() {
		return attributes;
	}
	public void setAttributes(HashMap<String, HashMap<String, Object>> attributes) {
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
}
