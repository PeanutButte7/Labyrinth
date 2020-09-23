package com.peanutbutte7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileReader {
    static List<Puzzle> readFile() {
        List<Puzzle> puzzles = new ArrayList<>();

        try {
            File file = new File("C:\\FIKS\\1. kolo\\Labyrint\\Labyrinth\\src\\com\\peanutbutte7\\input.txt");
            Scanner scanner = new Scanner(file);

            int puzzleAmount = Integer.parseInt(scanner.nextLine());
            for (int i = 1; i <= puzzleAmount; i++) {
                // Gets number of rows and columns
                PuzzleInfo puzzleInfo = new PuzzleInfo(scanner);

                // Puts the labyrinth in to a 2d array of chars
                char[][] labyrinthLayout = createLabyrinthLayout(scanner, puzzleInfo);

                //Creates a puzzle and adds it to the list
                Puzzle newPuzzle = new Puzzle(puzzleInfo.rows, puzzleInfo.columns, labyrinthLayout);
                puzzles.add(newPuzzle);

                // Skips the empty line after each puzzle
                if(scanner.hasNextLine()) scanner.nextLine();
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return puzzles;
    }

    private static char[][] createLabyrinthLayout(Scanner scanner, PuzzleInfo puzzleInfo) {
        char[][] labyrinth = new char[puzzleInfo.rows][puzzleInfo.columns];

        for (int i = 0; i < puzzleInfo.rows; i++) {
            String row = scanner.nextLine();

            for (int y = 0; y < puzzleInfo.columns; y++) {
                labyrinth[i][y] = row.charAt(y);
            }
        }

        return labyrinth;
    }
}

class PuzzleInfo {
    int rows;
    int columns;

    PuzzleInfo(Scanner scanner) {
        int[] puzzleInfo = getPuzzleInfo(scanner);

        this.rows = puzzleInfo[0];
        this.columns = puzzleInfo[1];
    }

    private static int[] getPuzzleInfo(Scanner scanner) {
        String puzzleInfo = scanner.nextLine();

        int rows = Integer.parseInt(puzzleInfo.split(" ")[0]);
        int columns = Integer.parseInt(puzzleInfo.split(" ")[1]);

        return new int [] {rows, columns};
    }
}
