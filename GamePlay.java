package com.company;

import java.util.Scanner;

import static com.company.Grid.*;

public class GamePlay {
    static private boolean gameOver = false;
    static private boolean playerWon = false;


    public static void play() {
        //Start the game!
        Scanner scanner = new Scanner(System.in);
        System.out.println("What level of game do you want to play? Type 1 (easy), 2 (medium) or 3 (hard).");
        gridSize = scanner.nextInt() * 5;
        Grid.createBoard();
        Grid.printBoard();

        //Take input from player until they either win or lose (gameOver == true).
        while (!gameOver) {
            System.out.println("Enter your next move. Format should be 'rowNumber colNumber'.");
            Integer nextMoveRow = scanner.nextInt();
            Integer nextMoveCol = scanner.nextInt();
            checkSquare(nextMoveRow, nextMoveCol, true);
            if (checkPlayerWin()) {
                gameOver = true;
                playerWon = true;
                break;
            }
            System.out.println("Current Board:");
            Grid.printBoard();
        }

        //Print outcome message
        String finalMessage = playerWon == true ? "Congratulations! You won." : "Sorry! You lost.";
        System.out.println(finalMessage);

        //Print final board
        System.out.println("Final Board:");
        Grid.uncoverBoard();
        Grid.printBoard();

        //Give player option to restart or end the game
        System.out.println("Do you want to play again? Type yes or no");
        if (scanner.next().equals("yes")) {
            gameOver = false;
            playerWon = false;
            Grid.restart();
            play();
            //TODO: Fix this, currently still ends.
        }
        else {
            System.out.println("Thanks for playing!");
        }
    }

    //  Checks to see if square is a mine.
    //  If it is, sets gameOver to true and playerWon to false.
    //  If it is now, recursively checks all adjacent squares.
    public static void checkSquare(Integer row, Integer col, Boolean isPlayersSelectedSquare) {
        //if OOB or index is a mine but player did not select this square, do not uncover. Return
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize ||
                Grid.uncoveredVals[row][col] ||
                (Grid.isMine(row, col) && !isPlayersSelectedSquare)) {
            return;
        }

        //LOSE: if index contains a mine and this is the index that the player selected: uncover the location, game over.
        if (Grid.isMine(row, col) && isPlayersSelectedSquare) {
            gameOver = true;
            playerWon = false;
            return;
        }

        //else if index does not contain a mine: uncover row/col index and all row/col adjacent values
        Grid.uncoveredVals[row][col] = true;
        //check above, right, below, left
        checkSquare(row - 1, col, false);
        checkSquare(row, col + 1, false);
        checkSquare(row + 1, col, false);
        checkSquare(row, col - 1, false);
    }

    public static boolean checkPlayerWin() {
        int uncoveredCount = 0;
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (!uncoveredVals[row][col]) {
                    uncoveredCount++;
                }
            }
        }
        if (uncoveredCount == mineCount) {
            return true;
        }
        return false;
    }

}
