package com.peanutbutte7;

class Tile {
    int row;
    int column;
    int cost;
    boolean wasChecked;
    Type type;

    public enum Type {
        WALL,
        CORRIDOR,
        START,
        GOAL,
        MONSTER
    }

    Tile(int row, int column, boolean wasChecked, Type type) {
        this.row = row;
        this.column = column;
        this.wasChecked = wasChecked;
        this.type = type;
        this.cost = 0;
    }

    void check(int cost){
        this.cost += cost;
        wasChecked = true;
    }

    boolean isNavigable() {
        return !wasChecked && (type == Tile.Type.CORRIDOR || type == Tile.Type.GOAL);
    }
}