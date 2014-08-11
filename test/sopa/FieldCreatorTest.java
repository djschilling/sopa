package sopa;

import sopa.FieldCreator;
import sopa.Tile;
import sopa.TileType;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FieldCreatorTest {

    @org.junit.Test
    public void testFromString() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] generatedField = fieldCreator.fromString(new String[]{"nsn", "nin", "nfn"});
        Tile[][] expectedField = new Tile[3][3];
        expectedField[0][0] = new Tile(false, false, false, false, TileType.NONE);
        expectedField[1][0] = new Tile(false, true, false, false, TileType.START);
        expectedField[2][0] = new Tile(false, false, false, false, TileType.NONE);
        expectedField[0][1] = new Tile(false, false, false, false, TileType.NONE);
        expectedField[1][1] = new Tile(true, true, false, false, TileType.PUZZLE);
        expectedField[2][1] = new Tile(false, false, false, false, TileType.NONE);
        expectedField[0][2] = new Tile(false, false, false, false, TileType.NONE);
        expectedField[1][2] = new Tile(true, false, false, false, TileType.FINISH);
        expectedField[2][2] = new Tile(false, false, false, false, TileType.NONE);
        System.out.println(generatedField[0][1].getTileType());
        assertThat(generatedField, is(expectedField));
    }

    @org.junit.Test
    public void testLeft() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] generatedField = fieldCreator.fromString(new String[]{"nnn", "sin", "fnn", "nnn"});
        Tile[][] expectedField = new Tile[3][4];
        expectedField[0][1] = new Tile(false, false, false, true, TileType.START);
        expectedField[0][2] = new Tile(false, false, false, true, TileType.FINISH);
        assertThat(generatedField[0][1], is(expectedField[0][1]));
        assertThat(generatedField[0][2], is(expectedField[0][2]));
    }

    @org.junit.Test
    public void testRight() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] generatedField = fieldCreator.fromString(new String[]{"nnn", "nis", "nnf", "nnn"});
        Tile[][] expectedField = new Tile[3][4];
        expectedField[2][1] = new Tile(false, false, true, false, TileType.START);
        expectedField[2][2] = new Tile(false, false, true, false, TileType.FINISH);
        assertThat(generatedField[2][1], is(expectedField[2][1]));
        assertThat(generatedField[2][2], is(expectedField[2][2]));
    }

    @org.junit.Test
    public void testTop() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] generatedField = fieldCreator.fromString(new String[]{"nsfn", "nnin", "nnnn"});
        Tile[][] expectedField = new Tile[4][3];
        expectedField[1][0] = new Tile(false, true, false, false, TileType.START);
        expectedField[2][0] = new Tile(false, true, false, false, TileType.FINISH);
        assertThat(generatedField[1][0], is(expectedField[1][0]));
        assertThat(generatedField[2][0], is(expectedField[2][0]));
    }

    @org.junit.Test
    public void testBottom() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] generatedField = fieldCreator.fromString(new String[]{"nnnn", "nnin", "nsfn"});
        Tile[][] expectedField = new Tile[4][3];
        expectedField[1][2] = new Tile(true, false, false, false, TileType.START);
        expectedField[2][2] = new Tile(true, false, false, false, TileType.FINISH);
        assertThat(generatedField[1][2], is(expectedField[1][2]));
        assertThat(generatedField[2][2], is(expectedField[2][2]));
    }
}