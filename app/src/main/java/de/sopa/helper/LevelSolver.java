package de.sopa.helper;

import java.util.ArrayList;
import java.util.List;

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

    public Level solve(Level level, int maxDepth) {
        List<Level> possibleSolutions = new ArrayList<>();
        solve(level, maxDepth, 0, possibleSolutions);
        Level best = new Level();
        best.setMinimumMovesToSolve(maxDepth + 1);
        for (Level possibleSolution : possibleSolutions) {
            if (possibleSolution.getMinimumMovesToSolve() < best.getMinimumMovesToSolve()) {
                best = possibleSolution;
            }
        }
        if(best.getMinimumMovesToSolve() == maxDepth + 1 ) {
            return null;
        }
        return best;
    }

    public void solve(Level level, int maxDepth, int currentDepth, List<Level> possibleSolutions) {
        Tile[][] field = level.getField();
        int columns = field.length;
        int rows = field[0].length;

        boolean solvedPuzzle = gameFieldService.solvedPuzzle(level);
        if(solvedPuzzle) {
            possibleSolutions.add(level);
        }
        Level levelToModify;
        for (int column = 0; column < columns - 2; column++) {
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, false, column, 1);
            if (gameFieldService.solvedPuzzle(levelToModify)) {
                addPossibleSolutions(levelToModify, currentDepth + 1, possibleSolutions);
            } else if (currentDepth < maxDepth - 1) {
                solve(levelToModify, maxDepth, currentDepth + 1, possibleSolutions);
            }
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, false, column, -1);
            if(gameFieldService.solvedPuzzle(levelToModify)) {
                addPossibleSolutions(levelToModify, currentDepth + 1, possibleSolutions);
            } else if (currentDepth < maxDepth - 1) {
                solve(levelToModify, maxDepth, currentDepth + 1, possibleSolutions);
            }
        }
        for (int row = 0; row < rows - 2; row++) {
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, true, row, 1);
            if (gameFieldService.solvedPuzzle(levelToModify)) {
                addPossibleSolutions(levelToModify, currentDepth + 1, possibleSolutions);
            } else if (currentDepth < maxDepth - 1) {
                solve(levelToModify, maxDepth, currentDepth + 1, possibleSolutions);
            }
            levelToModify = new Level(level);
            gameFieldService.shiftLine(levelToModify, true, row, -1);
            if(gameFieldService.solvedPuzzle(levelToModify)) {
                addPossibleSolutions(levelToModify, currentDepth + 1, possibleSolutions);
            } else if (currentDepth < maxDepth - 1) {
                solve(levelToModify, maxDepth, currentDepth + 1, possibleSolutions);
            }
        }
    }

    private void addPossibleSolutions(Level levelToModify, int moveCount, List<Level> possibleSolutions) {
        levelToModify.setMinimumMovesToSolve(moveCount);
        possibleSolutions.add(levelToModify);
    }
}
