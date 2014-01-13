/**
 * 
 */
package org.game.towers.npcs;

import java.util.ArrayList;

import org.game.towers.units.Unit;

/**
 * @author eliotik
 *
 */
public class NpcType extends Unit {
	private ArrayList<String> hands;

	public ArrayList<String> getHands() {
		return hands;
	}

	public void setHands(ArrayList<String> hands) {
		this.hands = hands;
	}
}
