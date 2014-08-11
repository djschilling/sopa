import java.util.HashMap;
import java.util.Map;

/**
 * David Schilling - davejs92@gmail.com
 */
public class FieldCreator {
    private Map<Character, Tile> characterTileHashMap;
    public FieldCreator() {
        characterTileHashMap = new HashMap<Character, Tile>();
        characterTileHashMap.put('s', new Tile(false,false,false,false,TileType.START));
        characterTileHashMap.put('f', new Tile(false,false,false,false,TileType.FINISH));
        characterTileHashMap.put('n', new Tile(false,false,false,false,TileType.NONE));
        characterTileHashMap.put('t', new Tile(true,false,false,false,TileType.PUZZLE));
        characterTileHashMap.put('b', new Tile(false,true,false,false,TileType.PUZZLE));
        characterTileHashMap.put('r', new Tile(false,false,false,true,TileType.PUZZLE));
        characterTileHashMap.put('l', new Tile(false,false,true,false,TileType.PUZZLE));
        characterTileHashMap.put('a', new Tile(false,false,true,true,TileType.PUZZLE));
        characterTileHashMap.put('u', new Tile(false,true,false,true,TileType.PUZZLE));
        characterTileHashMap.put('c', new Tile(false,true,true,false,TileType.PUZZLE));
        characterTileHashMap.put('d', new Tile(false,true,true,true,TileType.PUZZLE));
        characterTileHashMap.put('e', new Tile(true,false,false,true,TileType.PUZZLE));
        characterTileHashMap.put('g', new Tile(true,false,true,false,TileType.PUZZLE));
        characterTileHashMap.put('h', new Tile(true,false,true,true,TileType.PUZZLE));
        characterTileHashMap.put('i', new Tile(true,true,false,false,TileType.PUZZLE));
        characterTileHashMap.put('j', new Tile(true,true,false,true,TileType.PUZZLE));
        characterTileHashMap.put('k', new Tile(true,true,true,false,TileType.PUZZLE));
        characterTileHashMap.put('m', new Tile(true,true,true,true,TileType.PUZZLE));

    }
    public Tile[][] fromString(String[] string){
        Tile[][] field;
        field = new Tile[string[0].length()][string.length];
        for (int i = 0; i<string[0].length(); i++) {
            for (int j = 0; j<string.length; j++) {
                Tile currentTile = new Tile(characterTileHashMap.get(string[j].charAt(i)));
                if(currentTile.getTileType() != TileType.NONE) {
                    if(i == 0) {
                        checkInvalidTile(currentTile);
                        currentTile.setRight(true);
                    }
                    if(i == string[0].length()-1) {
                        checkInvalidTile(currentTile);
                        currentTile.setLeft(true);
                    }
                    if(j == 0) {
                        checkInvalidTile(currentTile);
                        currentTile.setBottom(true);
                    }
                    if(j == string.length-1) {
                        checkInvalidTile(currentTile);
                        currentTile.setTop(true);
                    }
                }
                field[i][j] = currentTile;
            }

        }
        return field;
    }

    private void checkInvalidTile(Tile currentTile) {
        if(currentTile.getTileType() == TileType.PUZZLE) {
            throw new IllegalArgumentException("Just Finish, Start and None allowed in the borders");
        }
    }
}
