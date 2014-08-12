package sopa;

import java.util.HashMap;
import java.util.Map;

/**
 * David Schilling - davejs92@gmail.com
 */

public class FieldCreator {

    private int[] directionsX = new int[]{0, 1, 0, -1};
    private int[] directionsY = new int[]{1, 0, -1, 0};
    private Tile[][] tiles;

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
    public GameField fromString(String[] string){
        Tile[][] field;
        GameField gameField = new GameField();
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
                if(currentTile.getTileType() == TileType.START) {
                    gameField.setStartX(i);
                    gameField.setStartY(j);
                }
            }

        }
        gameField.setField(field);
        return gameField;
    }

    private void checkInvalidTile(Tile currentTile) {
        if(currentTile.getTileType() == TileType.PUZZLE) {
            throw new IllegalArgumentException("Just Finish, Start and None allowed in the borders");
        }
    }

    public GameField generateSolvedField(int width, int height) {
        GameField gameField = new GameField();
        int startX;
        int startY;
        int direction;
        tiles = new Tile[width][height];
        for(int i = 0; i< tiles.length; i++) {
            for (int j = 0; j< tiles[i].length; j++) {
                tiles[i][j] = new Tile(false,false,false,false,TileType.NONE);
            }
        }
        Tile startTile;
        switch ((int) (Math.random() * (4))) {
            case 0:
                startTile = new Tile(false,true,false,false,TileType.START);
                startX = (int) (Math.random() * (width-2) +1);
                startY = 0;
                direction = 0;
                tiles[startX][0] = startTile;
                break;
            case 1:
                startTile = new Tile(true,false,false,false,TileType.START);
                startX = (int) (Math.random() * (width-2) +1);
                startY = height-1;
                direction = 2;
                tiles[startX][height-1] = startTile;
                break;
            case 2:
                startTile = new Tile(false,false,false,true,TileType.START);
                startX = 0;
                startY = (int) (Math.random() * (height-2) +1);
                direction = 1;
                tiles[startX][startY] = startTile;
                break;
            case 3:
                startTile = new Tile(false,false,true,false,TileType.START);
                startX = width-1;
                startY = (int) (Math.random() * (height-2) +1);
                direction = 3;
                tiles[startX][startY] = startTile;
                break;
            default:
                throw new IllegalStateException("Wrong Random");
        }
        int x = startX;
        int y = startY;
        while ((x != 0 && x != width-1 && y != 0 && y != height-1)||(x == startX && y == startY )) {
            if(Math.random()<0.7f && !(startX == x && startY == y )) {
                direction = (int) (Math.random() *4);
            }

            int xNew = x + directionsX[direction];
            int yNew = y + directionsY[direction];
            while (tiles[xNew][yNew].getTileType() != TileType.NONE ) {
                direction = (int) (Math.random() * (4) +0);
                xNew = x + directionsX[direction];
                yNew = y + directionsY[direction];
            }
            switch (direction) {
                case 0:
                    tiles[x][y].setBottom(true);
                    tiles[xNew][yNew].setTop(true);
                    break;
                case 1:
                    tiles[x][y].setRight(true);
                    tiles[xNew][yNew].setLeft(true);
                    break;
                case 2:
                    tiles[x][y].setTop(true);
                    tiles[xNew][yNew].setBottom(true);
                    break;
                case 3:
                    tiles[x][y].setLeft(true);
                    tiles[xNew][yNew].setRight(true);
                    break;
                default:
                    throw new IllegalStateException("Wrong Direction");
            }
            x = xNew;
            y = yNew;
            tiles[x][y].setTileType(TileType.PUZZLE);
        }
        tiles[x][y].setTileType(TileType.FINISH);
        gameField.setStartX(startX);
        gameField.setStartY(startY);
        gameField.setField(tiles);
        return gameField;
    }
}
