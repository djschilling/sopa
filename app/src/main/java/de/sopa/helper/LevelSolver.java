package de.sopa.helper;

import de.sopa.model.game.GameFieldService;
import de.sopa.model.game.Level;
import de.sopa.model.game.Tile;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelSolver {

    private final GameFieldService gameFieldService;


    public LevelSolver(GameFieldService gameFieldService) {
        this.gameFieldService = gameFieldService;
    }

    public Level solve(Level level) {

        Tile[][] field = level.getField();
        int columns = field.length;
        int rows = field[0].length;

        boolean solvedPuzzle = gameFieldService.solvedPuzzle(level);
        if(solvedPuzzle) {
            return level;
        }
        Level levelToModify;
        for (int column = 0; column < columns - 2; column++) {
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, false, column, 1);
            if (gameFieldService.solvedPuzzle(levelToModify)) {
                return returnSolvedLevel(levelToModify, 1);
            }
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, false, column, -1);
            if(gameFieldService.solvedPuzzle(levelToModify)) {
                return returnSolvedLevel(levelToModify, 1);
            }
        }
        for (int row = 0; row < rows - 2; row++) {
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, true, row, 1);
            if (gameFieldService.solvedPuzzle(levelToModify)) {
                return returnSolvedLevel(levelToModify, 1);
            }
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, true, row, -1);
            if(gameFieldService.solvedPuzzle(levelToModify)) {
                return returnSolvedLevel(levelToModify, 1);
            }
        }
        return null;
    }

    private Level returnSolvedLevel(Level levelToModify, int moveCount) {
        levelToModify.setMinimumMovesToSolve(moveCount);
        return levelToModify;
    }
}
