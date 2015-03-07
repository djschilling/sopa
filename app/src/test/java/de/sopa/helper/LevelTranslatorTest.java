package de.sopa.helper;

import org.junit.Before;
import org.junit.Test;

import de.sopa.model.game.Level;
import de.sopa.model.game.Tile;
import de.sopa.model.game.TileType;

import static de.sopa.model.game.TileType.FINISH;
import static de.sopa.model.game.TileType.PUZZLE;
import static de.sopa.model.game.TileType.START;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelTranslatorTest {

    private LevelTranslator sut;
    private static final String[] levelAsString = {
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
    private static final String[] invalidLevelAsString = {
        "5",
        "2",
        "6",
        "#",
        "nonsnn",
        "ncooen",
        "noooin",
        "noooin",
        "ngooun",
        "nnnfnn"
    };
    private Level level;

    @Before
    public void setUp() {
        sut = new LevelTranslator();
        level = new Level();
        setupLevel();
    }

    @Test
    public void fromString() {
        Level resultingLevel = sut.fromString(levelAsString);
        assertThat(resultingLevel, is(level));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromStringInvalid() {
        sut.fromString(invalidLevelAsString);
    }

    @Test
    public void fromGameField() {
        String[] fromGameField = sut.fromGameField(level);
        assertThat(fromGameField, is(levelAsString));
    }

    private void setupLevel() {
        level.setId(5);
        level.setMinimumMovesToSolve(2);
        level.setTilesCount(6);
        level.setStartX(3);
        level.setStartY(0);
        Tile[][] field = new Tile[][]{
                new Tile[]{new Tile(), new Tile(),                                                  new Tile(),             new Tile(false, true, false, false, START, 's'),    new Tile(),                                         new Tile()},
                new Tile[]{new Tile(), new Tile(false, true, true, false, TileType.PUZZLE, 'c'),    new Tile(PUZZLE, 'o'),  new Tile(PUZZLE, 'o'),                              new Tile(true, false, false, true, PUZZLE, 'e'),    new Tile()},
                new Tile[]{new Tile(), new Tile(TileType.PUZZLE, 'o'),                              new Tile(PUZZLE, 'o'),  new Tile(PUZZLE, 'o'),                              new Tile(true, true, false, false, PUZZLE, 'i'),    new Tile()},
                new Tile[]{new Tile(), new Tile(TileType.PUZZLE, 'o'),                              new Tile(PUZZLE, 'o'),  new Tile(PUZZLE, 'o'),                              new Tile(true, true, false, false, PUZZLE, 'i'),    new Tile()},
                new Tile[]{new Tile(), new Tile(true, false, true, false, TileType.PUZZLE, 'g'),    new Tile(PUZZLE, 'o'),  new Tile(PUZZLE, 'o'),                              new Tile(false, true, false, true, PUZZLE, 'u'),    new Tile()},
                new Tile[]{new Tile(), new Tile(),                                                  new Tile(),             new Tile(true, false, false, false, FINISH, 'f'),   new Tile(),                                         new Tile()},
        };
        level.setField(swapColumnAndRow(field));
    }

    private Tile[][] swapColumnAndRow(Tile[][] field) {
        Tile[][] fieldSwapped = new Tile[field[0].length][];
        for(int row = 0; row < field[0].length; row++) {
            fieldSwapped[row] = new Tile[field.length];
        }
        for (int row = 0; row < field.length; row++){
            for (int col = 0; col < field[row].length; col++){
                fieldSwapped[col][row] = field[row][col];
            }
        }
        return fieldSwapped;


    }
}