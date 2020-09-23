package com.peanutbutte7;

import java.util.ArrayList;
import java.util.List;

class SimplePuzzleSolver {
    private Puzzle puzzle;
    private Tile[][] labyrinth;

    private Tile startingTile;
    private Tile goalTile;
    private List<Tile> checkQueue = new ArrayList<>();
    private List<Tile> temporaryQueue = new ArrayList<>();
    private boolean goalFound = false;

    SimplePuzzleSolver(Puzzle puzzle) {
        this.puzzle = puzzle;
        this.labyrinth = puzzle.labyrinth;
    }

    String solvePuzzle(){
        String solution = "";
        puzzle.log(true);

        startingTile = getStartingTile();
        System.out.println(startingTile.row + " " + startingTile.column);
        checkQueue.add(startingTile);

        // Finds the goal
        while (!checkQueue.isEmpty()) {
            for (Tile currentTile : checkQueue) {
                findNavigableNeighbors(currentTile);
                if (goalFound) break;

                puzzle.log(false);
            }

//            System.out.println("Temporary queue: " + temporaryQueue);
            checkQueue.clear();
            checkQueue.addAll(temporaryQueue);
            temporaryQueue.clear();
//            System.out.println("Check queue: " + checkQueue);

            if (goalFound) {
                break;
            }
        }

        if (goalFound) {
            System.out.println("Number of steps: " + goalTile.cost);
            System.out.println("Goal at: row: " + goalTile.row + ", column: " + goalTile.column);
            solution = traceRoute(goalTile);
        } else {
            System.out.println("Goal not found");
            solution = "tak to je konec";
        }

        return solution;
    }

    private String traceRoute(Tile pathTile) {
        StringBuilder route = new StringBuilder();
        Tile nextTile = pathTile;

        for (int i = 0; i < pathTile.cost; i++) {
            NextTileMove result = findNextTileMove(nextTile);
            route.append(result.move);
            nextTile = result.tile;
        }

        // The program traces the route back from the goal and therefore the instructions are reversed by default
        route.reverse();

        System.out.println(route.toString());
        return route.toString();
    }

    private NextTileMove findNextTileMove(Tile pathTile){
        int row = pathTile.row;
        int column = pathTile.column;
        int numberOfRows = labyrinth.length - 1;
        int numberOfColumns = labyrinth[0].length - 1;
        int requiredCost = pathTile.cost - 1;
        NextTileMove nextTileMove = null;
        System.out.println("Required cost: " + requiredCost + ", Row: " + row + ", Column: " + column + ", Type: " + pathTile.type);

        // Right check
        if (column < numberOfColumns && labyrinth[row][column + 1].wasChecked && labyrinth[row][column + 1].type != Tile.Type.WALL && labyrinth[row][column + 1].cost == requiredCost) {
            nextTileMove = new NextTileMove('<', labyrinth[row][column + 1]);
        }
        // Left check
        else if (column > 0 && labyrinth[row][column - 1].wasChecked && labyrinth[row][column - 1].type != Tile.Type.WALL && labyrinth[row][column - 1].cost == requiredCost) {
            nextTileMove = new NextTileMove('>', labyrinth[row][column - 1]);
        }
        // Top check
        else if (row > 0 && labyrinth[row - 1][column].wasChecked && labyrinth[row - 1][column].type != Tile.Type.WALL && labyrinth[row - 1][column].cost == requiredCost) {
            nextTileMove =  new NextTileMove('v', labyrinth[row - 1][column]);
        }
        // Bottom check
        else if (row < numberOfRows && labyrinth[row + 1][column].wasChecked && labyrinth[row + 1][column].type != Tile.Type.WALL && labyrinth[row + 1][column].cost == requiredCost) {
            nextTileMove = new NextTileMove('ˆ', labyrinth[row + 1][column]);
        } else {
            throw new IllegalArgumentException("No legitimate adjacent tile was found with: Required cost: " + requiredCost + ", Row: " + row + ", Column: " + column + ", Type: " + pathTile.type);
        }

        return nextTileMove;
    }

    private void findNavigableNeighbors(Tile tile) {
        int x = tile.row;
        int y = tile.column;
        int numberOfColumns = labyrinth.length - 1;
        int numberOfRows = labyrinth[0].length - 1;

        // Right check
        if (x < numberOfColumns && labyrinth[x + 1][y].isNavigable()) {
            Tile currentTile = labyrinth[x + 1][y];
            currentTile.check(tile.cost + 1);
            checkForGoal(currentTile);
        }

        // Left check
        if (x > 0 && labyrinth[x - 1][y].isNavigable()) {
            Tile currentTile = labyrinth[x - 1][y];
            currentTile.check(tile.cost + 1);
            checkForGoal(currentTile);
        }

        // Top check
        if (y > 0 && labyrinth[x][y - 1].isNavigable()) {
            Tile currentTile = labyrinth[x][y - 1];
            currentTile.check(tile.cost + 1);
            checkForGoal(currentTile);
        }

        // Bottom check
        if (y < numberOfRows && labyrinth[x][y + 1].isNavigable()) {
            Tile currentTile = labyrinth[x][y + 1];
            currentTile.check(tile.cost + 1);
            checkForGoal(currentTile);
        }
    }

    private void checkForGoal(Tile currentTile) {
        if (currentTile.type == Tile.Type.GOAL) {
            goalTile = currentTile;
            goalFound = true;
            System.out.println("GOAL FOUND");
        } else {
            temporaryQueue.add(currentTile);
        }
    }

    private Tile getStartingTile(){
        Tile start = null;

        for(int x = 0; x < puzzle.rows; x++) {
            for (int y = 0; y < puzzle.columns; y++) {
                if (labyrinth[x][y].type == Tile.Type.START) {
                    start = labyrinth[x][y];
                    start.wasChecked = true;

                }
            }
        }
        return start;
    }
}

class NextTileMove {
    char move;
    Tile tile;

    public NextTileMove(char move, Tile tile) {
        this.move = move;
        this.tile = tile;
    }
}
