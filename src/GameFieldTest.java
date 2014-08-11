import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class GameFieldTest {

    @Test
    public void testShiftLine() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] field = fieldCreator.fromString(new String[]{"nnnnn", "snimn", "nnnnn", "nnnnn"});
        Tile[][] field2 = fieldCreator.fromString(new String[]{"nnnnn", "smnin", "nnnnn", "nnnnn"});
        GameField sut = new GameField(2,2);
        sut.setField(field);
        sut.printField();
        sut.shiftLine(true,0,16);
        sut.printField();
        assertThat(field2, is(sut.getField()));
    }
}