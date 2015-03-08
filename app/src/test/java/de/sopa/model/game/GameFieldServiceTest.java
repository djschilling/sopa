package de.sopa.model.game;

import org.junit.Before;
import org.junit.Test;

import de.sopa.helper.LevelTranslator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class GameFieldServiceTest {

    private GameFieldService sut;
    private LevelTranslator levelTranslator;

    private static final String[] SOLVED_LEVEL = {
            "21",
            "4",
            "4",
            "#",
            "nnnnnn",
            "noooon",
            "saaaaf",
            "noooon",
            "noooon",
            "nnnnnn"
    };

    private static final String[] SOLVED_LEVEL_2 = {
            "1",
            "2",
            "2",
            "#",
            "nnnn",
            "saaf",
            "noon",
            "nnnn"
    };


    @Before
    public void setUp() {
        sut = new GameFieldService();
        levelTranslator = new LevelTranslator();
    }

    @Test
    public void solvedPuzzle() {
        assertThat(sut.solvedPuzzle(levelTranslator.fromString(SOLVED_LEVEL)), is(true));
    }
    @Test
    public void solvedPuzzle2() {
        assertThat(sut.solvedPuzzle(levelTranslator.fromString(SOLVED_LEVEL_2)), is(true));
    }
}