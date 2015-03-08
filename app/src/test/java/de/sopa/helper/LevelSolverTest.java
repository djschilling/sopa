package de.sopa.helper;

import org.junit.Before;
import org.junit.Test;

import de.sopa.model.game.GameFieldService;
import de.sopa.model.game.Level;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

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
    private LevelTranslator levelTranslator;


    @Before
    public void setUp() {
        sut = new LevelSolver(new GameFieldService());
        this.levelTranslator = new LevelTranslator();
    }

    @Test
    public void solveOneMoveColumnTrue() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_ONE_MOVE_COLUMN));
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(1));
    }
    @Test
    public void solveOneMoveColumnFalse() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_TWO_MOVE_COLUMN));
        assertThat(solvedLevel, nullValue());
    }

    @Test
    public void solveOneMoveRowTrue() {
        Level solvedLevel = sut.solve(levelTranslator.fromString(LEVEL_ONE_MOVE_ROW));
        assertThat(solvedLevel, notNullValue());
        assertThat(solvedLevel.getMinimumMovesToSolve(), is(1));
    }
}