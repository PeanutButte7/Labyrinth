package com.peanutbutte7;

import java.lang.reflect.Array;

class Puzzle {
    int rows;
    int columns;
    boolean hasMonster;
    Tile[][] labyrinth;

    Puzzle(int rows, int columns, char[][] labyrinthLayout) {
        this.rows = rows;
        this.columns = columns;
        this.labyrinth = createLabyrinth(labyrinthLayout, rows, columns);
    }

    private Tile[][] createLabyrinth(char[][] labyrinthLayout, int rows, int columns) {
        Tile[][] labyrinth = new Tile[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                labyrinth[row][column] = new Tile(row, column, false, getTileType(labyrinthLayout[row][column]));
            }
        }

        return labyrinth;
    }

    private Tile.Type getTileType(char c) {
        switch(c) {
            case '.':
                return Tile.Type.CORRIDOR;
            case '#':
                return Tile.Type.WALL;
            case 's':
                return Tile.Type.START;
            case 'x':
                return Tile.Type.GOAL;
            case 'M':
                hasMonster = true;
                return Tile.Type.MONSTER;
            default:
                throw new IllegalArgumentException("Unknow character: " + c);
        }
    }

    void log(boolean withInfo) {
        if (withInfo) {
            System.out.println("Has monster: " + hasMonster);
            System.out.println(rows + " " + columns);
        }

        for(int x = 0; x < rows; x++) {
            for (int y = 0; y < columns; y++) {
                Tile currentTile = labyrinth[x][y];

                if (currentTile.wasChecked && currentTile.type != Tile.Type.START) {
                    System.out.print(currentTile.cost + " ");
                } else {
                    switch(currentTile.type) {
                        case CORRIDOR:
                            System.out.print(". ");
                            break;
                        case WALL:
                            System.out.print("# ");
                            break;
                        case START:
                            System.out.print("s ");
                            break;
                        case GOAL:
                            System.out.print("x ");
                            break;
                        case MONSTER:
                            hasMonster = true;
                            System.out.print("M ");
                            break;
                    }
                }
            }
            System.out.println();
        }

        System.out.println();
    }
}
