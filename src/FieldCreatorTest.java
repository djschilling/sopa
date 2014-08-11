import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class FieldCreatorTest {

    @org.junit.Test
    public void testFromString() throws Exception {
        FieldCreator fieldCreator = new FieldCreator();
        Tile[][] generatedField = fieldCreator.fromString(new String[] {"nsn", "nin","nfn"});

        Tile[][] expectedField = new Tile[3][3];
        expectedField[0][0] = new Tile(false,false,false,false,TileType.NONE); expectedField[1][0] = new Tile(false,true,false,false,TileType.START); expectedField[2][0] = new Tile(false,false,false,false,TileType.NONE);
        expectedField[0][1] = new Tile(false,false,false,false,TileType.NONE); expectedField[1][1] = new Tile(true,true,false,false,TileType.PUZZLE); expectedField[2][1] = new Tile(false,false,false,false,TileType.NONE);
        expectedField[0][2] = new Tile(false,false,false,false,TileType.NONE); expectedField[1][2] = new Tile(true,false,false,false,TileType.FINISH); expectedField[2][2] = new Tile(false,false,false,false,TileType.NONE);
        System.out.println(generatedField[0][1].getTileType());
        assertThat(generatedField, is(expectedField));
    }
}