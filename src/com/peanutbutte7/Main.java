package com.peanutbutte7;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Puzzle> puzzles = FileReader.readFile();
        List<Puzzle> simplePuzzles = new ArrayList<>();
        List<Puzzle> monsterPuzzles = new ArrayList<>();

        for (Puzzle puzzle : puzzles) {
            if (puzzle.hasMonster) {
                monsterPuzzles.add(puzzle);
            } else {
                simplePuzzles.add(puzzle);
            }
        }

        //SimplePuzzleSolver simplePuzzleSolver = new SimplePuzzleSolver(simplePuzzles.get(22));

        FileCreator.createFile();
        for (Puzzle simplePuzzle : simplePuzzles) {
            SimplePuzzleSolver simplePuzzleSolver = new SimplePuzzleSolver(simplePuzzle);
            String solution = simplePuzzleSolver.solvePuzzle();
            System.out.println("Solution: " + solution);
            FileCreator.writeLine(solution);
        }

//        for (Puzzle simplePuzzle : simplePuzzles) {
//            SimplePuzzleSolver simplePuzzleSolver = new SimplePuzzleSolver(simplePuzzle);
//            String solution = simplePuzzleSolver.solvePuzzle();
//            System.out.println(solution);
//        }
    }
}
