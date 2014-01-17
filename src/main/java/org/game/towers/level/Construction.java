package org.game.towers.level;


import org.game.towers.units.Unit;

import java.util.HashMap;

public class Construction {
    private int apexAX;
    private int apexAY;
    private int apexBX;
    private int apexBY;
    private int apexCX;
    private int apexCY;
    private int apexDX;
    private int apexDY;
    private int width;
    private int height;
    private Unit type;
    private HashMap<Integer, Construction> neighbor = new HashMap<Integer, Construction>();


    public Construction(int x, int y, int width, int height, Unit type) {
        this.apexAX = x;
        this.apexAY = y;
        this.apexBX = x + width;
        this.apexBY = y;
        this.apexBX = x + width;
        this.apexBY = y + height;
        this.apexBX = x;
        this.apexBY = y + height;
        this.width  = width;
        this.height = height;
        this.type   = type;
    }

    public Construction(int x, int y){
        this.apexAX = x;
        this.apexAY = y;
    }

    public int getApexAX() {
        return apexAX;
    }

    public int getApexAY() {
        return apexAY;
    }

    public int getApexBX() {
        return apexBX;
    }

    public int getApexBY() {
        return apexBY;
    }

    public int getApexCX() {
        return apexCX;
    }

    public int getApexCY() {
        return apexCY;
    }

    public int getApexDX() {
        return apexDX;
    }

    public int getApexDY() {
        return apexDY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Unit getType() {
        return type;
    }

    public void setType(Unit type) {
        this.type = type;
    }

    public HashMap<Integer, Construction> getNeighbor() {
        return neighbor;
    }

    public void setNeighbor(HashMap<Integer, Construction> neighbor) {
        this.neighbor = neighbor;
    }
}
