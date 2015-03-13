package de.sopa.helper;

import org.junit.Before;
import org.junit.Test;

import de.sopa.model.game.GameFieldService;
import de.sopa.model.game.Level;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class LevelSolverTest {

    private LevelSolver sut;

    private static final String[] LEVEL_ONE_MOVE_COLUMN = {
            "1",
            "1",
            "2",
            "#",
            "nnnn",
            "saof",
            "noan",
            "nnnn"
    };
    private static final String[] LEVEL_ONE_MOVE_ROW = {
            "1",
            "1",
            "2",
            "#",
            "nsnn",
            "nion",
            "noin",
            "nfnn"
    };
    private static final String[] LEVEL_TWO_MOVE_COLUMN = {
            "1",
            "2",
            "2",
            "#",
            "nnnn",
            "soof",
            "naan",
            "nnnn"
    };
    private static final String[] LEVEL_TWO_MOVES = {
            "5",
            "2",
            "6",
            "#",
            "nnnsnn",
            "ncooen",
            "noooin",
            "noooin",
            "ngooun",
            "nnnfnn"
    };
    private static final String[] LEVEL_THREE_MOVES = {
            "21",
            "3",
            "5",
            "#",
            "nnfnnn",
            "noioon",
            "soioon",
            "ngoocn",
            "noeoon",
            "nnnnnn"
    };
    private static final String[] LEVEL_FOUR_MOVES = {
            "21",
            "4",
            "4",
            "#",
            "nnnnnn",
            "naaaan",
            "soooof",
            "noooon",
            "noooon",
            "nnnnnn"
    };

    private static final String[] LEVEL_SIX_MOVES = {
            "100",
            "6",
            "12",
            "#",
            "nnnnnn",
            "nouoas",
            "naacin",
            "ngueon",
            "naoiin",
            "nnnnfn"
    };


    private LevelTranslator levelTranslator;


    @Before
    public void setUp() {
        sut = new LevelSolver(new GameFieldService());
        this.levelTranslator = new LevelTranslator();
    }

    @Test
    public void solveOneMoveColumnTrue() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_ONE_MOVE_COLUMN), 4);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(1));
    }

    @Test
    public void solveTwoMoveColumnFalse() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_TWO_MOVE_COLUMN), 4);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(2));
    }

    @Test
    public void solveOneMoveRowTrue() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_ONE_MOVE_ROW), 4);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(1));
    }

    @Test
    public void solveTwoMoves() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_TWO_MOVES), 4);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(2));
    }

    @Test
    public void solveThreeMoves() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_THREE_MOVES), 4);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(3));
    }

    @Test
    public void solveFourMovesNotEnoughDeepth() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_FOUR_MOVES), 3);
        assertThat(solvedLevel, nullValue());
    }

    @Test
    public void solveFourMoves() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_FOUR_MOVES), 4);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(4));
    }

    @Test
    public void solveSixMovesFast() {
        long startTime = System.currentTimeMillis();
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_SIX_MOVES), 6);
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(6));
        long timeInMs = System.currentTimeMillis() - startTime;
        fail("sixs moves lasted " + timeInMs);
    }
}