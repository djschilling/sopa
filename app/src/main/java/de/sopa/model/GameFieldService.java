package de.sopa.model;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameFieldService {

    private GameEndService gameEndService;

    public GameFieldService() {
        gameEndService = new GameEndService();
    }

    public boolean solvedPuzzle(Level level) {
        return gameEndService.solvedPuzzle(level.getStartX(), level.getStartY(), level.getField().length, level.getField()[0].length, level.getField());
    }


    public void printBacktracking() {
        gameEndService.printBacktracking();
    }

    public void printField(Level level) {
        System.out.println("Field:");
        for (int i = 0; i < level.getField()[0].length; i++) {
            for (int j = 0; j < level.getField().length; j++) {
                System.out.print(level.getField()[j][i].getTileType() + "\t");
            }
            System.out.println();
        }
    }

    public void printFieldWay(Level level) {
        System.out.println("Field:");
        for (int i = 0; i < level.getField()[0].length; i++) {
            for (int k = 0; k < 3; k++) {
                for (int j = 0; j < level.getField().length; j++) {
                    switch (k) {
                        case 0:
                            System.out.print("\t " + level.getField()[j][i].isTop() + "\t\t");
                            break;
                        case 1:
                            System.out.print(level.getField()[j][i].isLeft() + " 0\t  " + level.getField()[j][i].isRight() + " ");
                            break;
                        case 2:
                            System.out.print("\t " + level.getField()[j][i].isBottom() + "\t\t");

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

    public void shiftLine(Level level, boolean horizontal, int row, int steps) {
        if (row < 0) {
            return;
        }
        if (horizontal) {
            if (row < level.getField().length - 2) {
                Tile line[] = new Tile[level.getField().length - 2];
                for (int i = 0; i < level.getField().length - 2; i++) {
                    line[i] = level.getField()[i + 1][row + 1];
                    level.getField()[i + 1][row + 1] = null;
                }
                for (int i = 0; i < level.getField().length - 2; i++) {
                    int newPosition = i + steps;
                    newPosition = shiftToPositive(newPosition, level.getField().length - 2);
                    newPosition = newPosition % (level.getField().length - 2);
                    level.getField()[newPosition + 1][row + 1] = line[i];
                }
                level.increaseMovesCounter();
            }
        } else {
            if (row < level.getField()[0].length - 2) {
                Tile line[] = new Tile[level.getField()[0].length - 2];
                for (int i = 0; i < level.getField()[0].length - 2; i++) {
                    line[i] = level.getField()[row + 1][i + 1];
                    level.getField()[row + 1][i + 1] = null;
                }
                for (int i = 0; i < level.getField()[0].length - 2; i++) {
                    int newPosition = (i + steps);
                    newPosition = shiftToPositive(newPosition, level.getField()[0].length - 2);
                    newPosition = newPosition % (level.getField()[0].length - 2);
                    level.getField()[row + 1][newPosition + 1] = line[i];
                }
                level.increaseMovesCounter ();
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
