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
        return gameEndService.solvedPuzzle(level.getStartX(), level.getStartY(), level.getField().length, level.getField()[0].length, level.getField(), level.getTilesCount());
    }

    public boolean shiftLine(Level level, boolean horizontal, int row, int steps) {
        if (row < 0) {
            return false;
        }
        if (horizontal) {
            if (row < level.getField().length - 2) {
                boolean shiftRelevant = false;
                Tile line[] = new Tile[level.getField().length - 2];
                for (int i = 0; i < level.getField().length - 2; i++) {
                    if(level.getField()[i + 1][row + 1].getShortcut()!= 'o') {
                        shiftRelevant = true;
                    }
                    line[i] = level.getField()[i + 1][row + 1];
                    level.getField()[i + 1][row + 1] = null;
                }
                for (int i = 0; i < level.getField().length - 2; i++) {
                    int newPosition = i + steps;
                    newPosition = shiftToPositive(newPosition, level.getField().length - 2);
                    newPosition = newPosition % (level.getField().length - 2);
                    level.getField()[newPosition + 1][row + 1] = line[i];
                }
                if(shiftRelevant) {
                    level.increaseMovesCounter();
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            if (row < level.getField()[0].length - 2) {
                boolean shiftRelevant = false;
                Tile line[] = new Tile[level.getField()[0].length - 2];
                for (int i = 0; i < level.getField()[0].length - 2; i++) {
                    line[i] = level.getField()[row + 1][i + 1];
                    level.getField()[row + 1][i + 1] = null;
                    if(line[i].getShortcut() != 'o') {
                        shiftRelevant = true;
                    }
                }
                for (int i = 0; i < level.getField()[0].length - 2; i++) {
                    int newPosition = (i + steps);
                    newPosition = shiftToPositive(newPosition, level.getField()[0].length - 2);
                    newPosition = newPosition % (level.getField()[0].length - 2);
                    level.getField()[row + 1][newPosition + 1] = line[i];
                }
                if(shiftRelevant) {
                    level.increaseMovesCounter();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private int shiftToPositive(int number, int steps) {
        int shifted = number;
        while (shifted < 0) {
            shifted = shifted + steps;
        }
        return shifted;
    }

}
