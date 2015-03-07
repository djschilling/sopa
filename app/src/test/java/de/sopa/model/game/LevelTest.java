package de.sopa.model.game;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LevelTest {

    private Level sut;

    @Test
    public void testConstructor() {
        sut = new Level();
        assertThat(sut.getMovesCount(), is(0));
    }

}