package de.sopa.model.levelchoice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.sopa.observer.Observer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author David Schilling - davejs92@gmail.com
 */
@RunWith(MockitoJUnitRunner.class)
public class LevelChoiceServiceImplTest {

    private LevelChoiceServiceImpl sut;
    private static final int LEVEL_COUNT = 25;
    private static final int LEVEL_PER_SCREEN = 12;

    @Mock
    Observer observer;

    @Before
    public void setUp() {
        sut = new LevelChoiceServiceImpl(LEVEL_COUNT, LEVEL_PER_SCREEN);
        sut.attach(observer);
    }

    @Test
    public void calculateScreenCountLowerBound() {
        int screenCount = sut.calculateScreenCount(LEVEL_COUNT, LEVEL_PER_SCREEN);
        assertThat(screenCount, is(3));
    }

    @Test
    public void calculateScreenCountHigherBound() {
        int screenCount = sut.calculateScreenCount(LEVEL_PER_SCREEN, LEVEL_PER_SCREEN);
        assertThat(screenCount, is(1));
    }

    @Test
    public void moveRight() {
        sut.moveRight();
        sut.moveRight();
        assertThat(sut.getCurrentScreen(), is(2));
        sut.moveRight();
        assertThat(sut.getCurrentScreen(), is(2));
        verify(observer, times(3)).update();
    }

    @Test
    public void moveTo() {
        assertThat(sut.getCurrentScreen(), is(0));
        sut.moveTo(2);
        assertThat(sut.getCurrentScreen(), is(2));
        sut.moveTo(3);
        assertThat(sut.getCurrentScreen(), is(2));
        sut.moveTo(-1);
        assertThat(sut.getCurrentScreen(), is(2));
        verify(observer, times(3)).update();
    }

    @Test
    public void moveLeft() {
        sut.moveRight();
        assertThat(sut.getCurrentScreen(), is(1));
        sut.moveLeft();
        assertThat(sut.getCurrentScreen(), is(0));
        sut.moveLeft();
        assertThat(sut.getCurrentScreen(), is(0));
        verify(observer, times(3)).update();
    }

    @Test
    public void isFirstSceneTrue(){
        assertThat(sut.isFirstScene(), is(true));
    }

    @Test
    public void isFirstSceneFalse(){
        sut.moveRight();
        assertThat(sut.isFirstScene(), is(false));
    }

    @Test
    public void isLastSceneTrue() {
        sut.moveRight();
        sut.moveRight();
        assertThat(sut.isLastScene(), is(true));
    }

    @Test
    public void isLastSceneFalse() {
        assertThat(sut.isLastScene(), is(false));
        sut.moveRight();
        assertThat(sut.isLastScene(), is(false));
    }
}