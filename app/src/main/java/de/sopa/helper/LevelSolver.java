package de.sopa.helper;

import de.sopa.model.game.GameFieldService;
import de.sopa.model.game.Level;
import de.sopa.model.game.Tile;

import java.util.ArrayList;
import java.util.List;


/**
 * @author  David Schilling - davejs92@gmail.com
 */
public class LevelSolver {

    private final GameFieldService gameFieldService;

    private int highestHit = -1;

    private int columns = -1;
    private int rows = -1;

    public LevelSolver(GameFieldService gameFieldService) {

        this.gameFieldService = gameFieldService;
    }

    public Level solve(Level level, int maxDepth) {

        List<Level> possibleSolutions = new ArrayList<>();
        Level levelToSolve = new Level(level);
        levelToSolve.setMinimumMovesToSolve(maxDepth + 1);
        highestHit = maxDepth + 1;

        Tile[][] field = level.getField();
        columns = field.length;
        rows = field[0].length;
        solve(levelToSolve, maxDepth, 0, possibleSolutions);

        Level best = new Level();
        best.setMinimumMovesToSolve(maxDepth + 1);

        for (Level possibleSolution : possibleSolutions) {
            if (possibleSolution.getMinimumMovesToSolve() < best.getMinimumMovesToSolve()) {
                best = possibleSolution;
            }
        }

        if (best.getMinimumMovesToSolve() == maxDepth + 1) {
            return null;
        }

        return best;
    }


    public boolean solve(Level level, int maxDepth, int currentDepth, List<Level> possibleSolutions) {

        if (gameFieldService.solvedPuzzle(level)) {
            addPossibleSolutions(new Level(level), currentDepth, possibleSolutions);

            if (currentDepth < highestHit) {
                highestHit = currentDepth;
            }

            return true;
        }

        if (currentDepth < maxDepth) {
            for (int column = 0; column < columns - 2; column++) {
                gameFieldService.shiftLine(level, false, column, 1);

                if (currentDepth + 1 < highestHit && solve(level, maxDepth, currentDepth + 1, possibleSolutions)) {
                    if (currentDepth + 1 < highestHit)
                        return true;
                }

                gameFieldService.shiftLine(level, false, column, -2);

                if (currentDepth + 1 < highestHit && solve(level, maxDepth, currentDepth + 1, possibleSolutions)) {
                    if (currentDepth + 1 < highestHit)
                        return true;
                }

                // restore state
                gameFieldService.shiftLine(level, false, column, 1);
            }

            for (int row = 0; row < rows - 2; row++) {
                gameFieldService.shiftLine(level, true, row, 1);

                if (currentDepth + 1 < highestHit && solve(level, maxDepth, currentDepth + 1, possibleSolutions)) {
                    if (currentDepth + 1 < highestHit)
                        return true;
                }

                gameFieldService.shiftLine(level, true, row, -2);

                if (currentDepth + 1 < highestHit && solve(level, maxDepth, currentDepth + 1, possibleSolutions)) {
                    if (currentDepth + 1 < highestHit)
                        return true;
                }

                // restore state
                gameFieldService.shiftLine(level, true, row, 1);
            }
        }

        return false;
    }


    private void addPossibleSolutions(Level level, int moveCount, List<Level> possibleSolutions) {

        if (level.getMinimumMovesToSolve() > moveCount) {
            level.setMinimumMovesToSolve(moveCount);
            possibleSolutions.add(level);
        }
    }
}
