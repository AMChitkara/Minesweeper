package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Grid {
    static Integer gridSize;
    static Integer mineCount;
    static String[][] gridVals;
    static Boolean[][] uncoveredVals;

    public static void createBoard() {
        //initialize uncoveredVals board
        uncoveredVals = new Boolean[gridSize][gridSize];
        for (int x = 0; x < gridSize; x++) {
            Arrays.fill(uncoveredVals[x], Boolean.FALSE);
        }

        gridVals = new String[gridSize][gridSize];
        //Create a list of N unique random Integers in the range (0, gridSize*gridSize)
        //Using an a solution I found: https://stackoverflow.com/questions/8115722/generating-unique-random-numbers-in-java
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < gridSize * gridSize; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        ArrayList<Integer> minePlacements = new ArrayList<Integer>();
        mineCount = gridSize * 2;
        for (int i = 0; i < mineCount; i++) {
            minePlacements.add(list.get(i));
        }

        //Change all vals to zero
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                gridVals[row][col] = "0";
            }
        }

        //Initialize Mines and increment adjacent values
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (minePlacements.contains(row * gridSize + col)) {
                    gridVals[row][col] = "X";
                    incrementAdjacentMines(row, col);
                }
            }
        }
    }

    public static void printBoard() {
        //print line of col nums
        System.out.print("  ");
        for (int i = 0; i < gridSize; i++) {
            System.out.print(i + " ");
        }

        //print each row in board
        System.out.println();
        for (int row = 0; row < gridSize; row++) {
            System.out.print(row + " ");
            for (int col = 0; col < gridSize; col++) {
                if (uncoveredVals[row][col]) {
                    System.out.print(gridVals[row][col] + " ");
                }
                else {
                    System.out.print("-" + " ");
                }
            }
            System.out.println();
        }
    }

    public static void restart() {
        for (int x = 0; x < gridSize; x++) {
            Arrays.fill(uncoveredVals[x], Boolean.FALSE);
        }
    }

    public static void uncoverBoard() {
        for (int x = 0; x < gridSize; x++) {
            Arrays.fill(uncoveredVals[x], Boolean.TRUE);
        }
    }

    public static Boolean isMine(Integer row, Integer col) {
        if (gridVals[row][col] == "X") {
            return true;
        }
        return false;
    }

    public static void incrementAdjacentMines(Integer row, Integer col) {
        //check top
        if (row - 1 >= 0) {
            quickConvert(row - 1, col);
        }
        //check right
        if (col + 1 < gridSize) {
            quickConvert(row, col + 1);
        }
        //check bottom
        if (row + 1 < gridSize) {
            quickConvert(row + 1, col);
        }
        //check left
        if (col - 1 >= 0) {
            quickConvert(row, col - 1);
        }
    }

    public static void quickConvert(Integer row, Integer col) {
        if (!isMine(row, col)) {
            Integer temp = Integer.parseInt(gridVals[row][col]) + 1;
            gridVals[row][col] = temp.toString();
        }
    }
}
