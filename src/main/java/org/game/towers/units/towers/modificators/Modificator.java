package org.game.towers.units.towers.modificators;

import java.io.Serializable;
import java.util.HashMap;

public class Modificator implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String description;
	private int duration;
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
}
