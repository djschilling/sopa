package de.sopa.model.game;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class StarCalculatorTest {

    private StarCalculator sut;
    private int minimumMoves;

    @Before
    public void setUp() {
        sut = new StarCalculator();
        minimumMoves = 10;
    }

    @Test
    public void getStarsThree() {
        int neededMoves = 10;
        assertThat(sut.getStars(neededMoves, minimumMoves), is(3));
    }

    @Test
    public void getStarsTwoLowerBorder() {
        int neededMoves = 11;
        assertThat(sut.getStars(neededMoves, minimumMoves), is(2));
    }

    @Test
    public void getStarsTwoHigherBorder() {
        int neededMoves = 16;
        assertThat(sut.getStars(neededMoves, minimumMoves), is(2));
    }

    @Test
    public void getStarsOneHigherBorder() {
        int neededMoves = 17;
        assertThat(sut.getStars(neededMoves, minimumMoves), is(1));
    }

    @Test
    public void getStarsOneLowerBorder() {
        int neededMoves = 100;
        assertThat(sut.getStars(neededMoves, minimumMoves), is(1));
    }
}