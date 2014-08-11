package sopa;

import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameFieldServiceTest {

    @Test
    public void testShiftLineHorizontal() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        GameField field = fieldCreator.fromString(new String[]{"nnnnn", "snimn", "nnnnn", "nnnnn"});
        GameField field2 = fieldCreator.fromString(new String[]{"nnnnn", "smnin", "nnnnn", "nnnnn"});
        GameFieldService sut = new GameFieldService(field);
        sut.printField();
        sut.shiftLine(true, 0, 16);
        sut.printField();
        assertThat(field2.getField(), is(sut.getField()));
    }

    @Test
    public void testShiftLineVertical() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        GameField field = fieldCreator.fromString(new String[]{"nnnnn", "snimn", "nnnnn", "nnnnn"});
        GameField field2 = fieldCreator.fromString(new String[]{"nnnnn", "snnmn", "nninn", "nnnnn"});
        GameFieldService sut = new GameFieldService(field);
        sut.printField();
        sut.shiftLine(false,1,-19);
        sut.printField();
        assertThat(field2.getField(), is(sut.getField()));
    }
}