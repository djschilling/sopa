package de.sopa.model;

import de.sopa.Tile;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameFieldService {

    private GameEndService gameEndService;

    public GameFieldService() {
        gameEndService = new GameEndService();
    }

    public boolean solvedPuzzle(GameField gameField) {
        return gameEndService.solvedPuzzle(gameField.getStartX(), gameField.getStartY(), gameField.getField().length, gameField.getField()[0].length, gameField.getField());
    }


    public void printBacktracking() {
        gameEndService.printBacktracking();
    }

    public void printField(GameField gameField) {
        System.out.println("Field:");
        for (int i = 0; i < gameField.getField()[0].length; i++) {
            for (int j = 0; j < gameField.getField().length; j++) {
                System.out.print(gameField.getField()[j][i].getTileType() + "\t");
            }
            System.out.println();
        }
    }

    public void printFieldWay(GameField gameField) {
        System.out.println("Field:");
        for (int i = 0; i < gameField.getField()[0].length; i++) {
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < gameField.getField().length; j++) {
                    switch (k) {
                        case 0:
                            System.out.print("\t " + gameField.getField()[j][i].isTop() + "\t\t");
                            break;
                        case 1:
                            System.out.print(gameField.getField()[j][i].isLeft() + " 0\t  " + gameField.getField()[j][i].isRight() + " ");
                            break;
                        case 2:
                            System.out.print("\t " + gameField.getField()[j][i].isBottom() + "\t\t");

                        default:
                            break;
                    }

                }
                System.out.println();
                // System.out.print(gameField.getField()[j][i].getTileType() + "\t");
            }
            System.out.println();

        }
    }

    public void shiftLine(GameField gameField, boolean horizontal, int row, int steps) {
        if (horizontal) {
            if (row < gameField.getField().length - 2) {
                Tile line[] = new Tile[gameField.getField().length - 2];
                for (int i = 0; i < gameField.getField().length - 2; i++) {
                    line[i] = gameField.getField()[i + 1][row + 1];
                    gameField.getField()[i + 1][row + 1] = null;
                }
                for (int i = 0; i < gameField.getField().length - 2; i++) {
                    int newPosition = i + steps;
                    newPosition = shiftToPositive(newPosition, gameField.getField().length - 2);
                    newPosition = newPosition % (gameField.getField().length - 2);
                    gameField.getField()[newPosition + 1][row + 1] = line[i];
                }
            }
        } else {
            if (row < gameField.getField()[0].length - 2) {
                Tile line[] = new Tile[gameField.getField()[0].length - 2];
                for (int i = 0; i < gameField.getField()[0].length - 2; i++) {
                    line[i] = gameField.getField()[row + 1][i + 1];
                    gameField.getField()[row + 1][i + 1] = null;
                }
                for (int i = 0; i < gameField.getField()[0].length - 2; i++) {
                    int newPosition = (i + steps);
                    newPosition = shiftToPositive(newPosition, gameField.getField()[0].length - 2);
                    newPosition = newPosition % (gameField.getField()[0].length - 2);
                    gameField.getField()[row + 1][newPosition + 1] = line[i];
                }
            }
        }
    }

    private int shiftToPositive(int number, int steps) {
        int shifted = number;
        while (shifted < 0) {
            shifted = shifted + steps;
        }
        return shifted;
    }

}
