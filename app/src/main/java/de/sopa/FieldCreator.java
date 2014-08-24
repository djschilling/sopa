package de.sopa;

import java.lang.Character;import java.lang.IllegalArgumentException;import java.lang.IllegalStateException;import java.lang.Math;import java.lang.String;import java.util.HashMap;
import java.util.Map;

/**
 * David Schilling - davejs92@gmail.com
 */

public class FieldCreator {

    private int[] directionsX = new int[]{0, 1, 0, -1};
    private int[] directionsY = new int[]{1, 0, -1, 0};
    private Tile[][] tiles;

    private Map<Character, Tile> characterTileHashMap;
    private Map<Tile, Tile> tileTileShortcutHashMap;
    public FieldCreator() {
        characterTileHashMap = new HashMap<>();
        characterTileHashMap.put('s', new Tile(false,false,false,false,TileType.START, 's'));
        characterTileHashMap.put('f', new Tile(false,false,false,false,TileType.FINISH, 'f'));
        characterTileHashMap.put('n', new Tile(false,false,false,false,TileType.NONE, 'n'));
        characterTileHashMap.put('o', new Tile(false,false,false,false,TileType.PUZZLE, 'o'));
        characterTileHashMap.put('t', new Tile(true,false,false,false,TileType.PUZZLE, 't'));
        characterTileHashMap.put('b', new Tile(false,true,false,false,TileType.PUZZLE, 'b'));
        characterTileHashMap.put('r', new Tile(false,false,false,true,TileType.PUZZLE, 'r'));
        characterTileHashMap.put('l', new Tile(false,false,true,false,TileType.PUZZLE, 'l'));
        characterTileHashMap.put('a', new Tile(false,false,true,true,TileType.PUZZLE, 'a'));
        characterTileHashMap.put('u', new Tile(false,true,false,true,TileType.PUZZLE, 'u'));
        characterTileHashMap.put('c', new Tile(false,true,true,false,TileType.PUZZLE, 'c'));
        characterTileHashMap.put('d', new Tile(false,true,true,true,TileType.PUZZLE, 'd'));
        characterTileHashMap.put('e', new Tile(true,false,false,true,TileType.PUZZLE, 'e'));
        characterTileHashMap.put('g', new Tile(true,false,true,false,TileType.PUZZLE, 'g'));
        characterTileHashMap.put('h', new Tile(true,false,true,true,TileType.PUZZLE, 'h'));
        characterTileHashMap.put('i', new Tile(true,true,false,false,TileType.PUZZLE, 'i'));
        characterTileHashMap.put('j', new Tile(true,true,false,true,TileType.PUZZLE, 'j'));
        characterTileHashMap.put('k', new Tile(true,true,true,false,TileType.PUZZLE, 'k'));
        characterTileHashMap.put('m', new Tile(true,true,true,true,TileType.PUZZLE, 'm'));

        tileTileShortcutHashMap = new HashMap<>();
        tileTileShortcutHashMap.put(new Tile(false,false,false,false,TileType.NONE, 'n'),new Tile(false,false,false,false,TileType.NONE, 'n'));
        tileTileShortcutHashMap.put(new Tile(false,false,false,false,TileType.PUZZLE, 'o'),new Tile(false,false,false,false,TileType.PUZZLE, 'o'));
        tileTileShortcutHashMap.put(new Tile(true,false,false,false,TileType.PUZZLE, 'n'),new Tile(true,false,false,false,TileType.PUZZLE, 't'));
        tileTileShortcutHashMap.put(new Tile(false,true,false,false,TileType.PUZZLE, 'n'),new Tile(false,true,false,false,TileType.PUZZLE, 'b'));
        tileTileShortcutHashMap.put(new Tile(false,false,false,true,TileType.PUZZLE, 'n'),new Tile(false,false,false,true,TileType.PUZZLE, 'r'));
        tileTileShortcutHashMap.put(new Tile(false,false,true,false,TileType.PUZZLE, 'n'),new Tile(false,false,true,false,TileType.PUZZLE, 'l'));
        tileTileShortcutHashMap.put(new Tile(false,false,true,true,TileType.PUZZLE, 'n'),new Tile(false,false,true,true,TileType.PUZZLE, 'a'));
        tileTileShortcutHashMap.put(new Tile(false,true,false,true,TileType.PUZZLE, 'n'),new Tile(false,true,false,true,TileType.PUZZLE, 'u'));
        tileTileShortcutHashMap.put(new Tile(false,true,true,false,TileType.PUZZLE, 'n'),new Tile(false,true,true,false,TileType.PUZZLE, 'c'));
        tileTileShortcutHashMap.put(new Tile(false,true,true,true,TileType.PUZZLE, 'n'),new Tile(false,true,true,true,TileType.PUZZLE, 'd'));
        tileTileShortcutHashMap.put(new Tile(true,false,false,true,TileType.PUZZLE, 'n'),new Tile(true,false,false,true,TileType.PUZZLE, 'e'));
        tileTileShortcutHashMap.put(new Tile(true,false,true,false,TileType.PUZZLE, 'n'),new Tile(true,false,true,false,TileType.PUZZLE, 'g'));
        tileTileShortcutHashMap.put(new Tile(true,false,true,true,TileType.PUZZLE, 'n'),new Tile(true,false,true,true,TileType.PUZZLE, 'h'));
        tileTileShortcutHashMap.put(new Tile(true,true,false,false,TileType.PUZZLE, 'n'),new Tile(true,true,false,false,TileType.PUZZLE, 'i'));
        tileTileShortcutHashMap.put(new Tile(true,true,false,true,TileType.PUZZLE, 'n'),new Tile(true,true,false,true,TileType.PUZZLE, 'j'));
        tileTileShortcutHashMap.put(new Tile(true,true,true,false,TileType.PUZZLE, 'n'),new Tile(true,true,true,false,TileType.PUZZLE, 'k'));
        tileTileShortcutHashMap.put(new Tile(true,true,true,true,TileType.PUZZLE, 'n'), new Tile(true,true,true,true,TileType.PUZZLE, 'm'));


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
        for(int i = 1; i< width-1; i++) {
            for (int j = 1; j< height-1; j++) {
                tiles[i][j] = characterTileHashMap.get('o');
            }
        }
        for(int i = 0; i< width; i++) {
            tiles[i][0] = characterTileHashMap.get('n');
        }

        for(int i = 0; i< width; i++) {
            tiles[i][height-1] = characterTileHashMap.get('n');
        }

        for(int i = 0; i< height; i++) {
            tiles[0][i] = characterTileHashMap.get('n');
        }

        for(int i = 0; i< height; i++) {
            tiles[width-1][i] = characterTileHashMap.get('n');
        }
        Tile startTile;
        switch ((int) (Math.random() * (4))) {
            case 0:
                startTile = new Tile(false,true,false,false,TileType.START, 's');
                startX = (int) (Math.random() * (width-2) +1);
                startY = 0;
                direction = 0;
                tiles[startX][0] = startTile;
                break;
            case 1:
                startTile = new Tile(true,false,false,false,TileType.START, 's');
                startX = (int) (Math.random() * (width-2) +1);
                startY = height-1;
                direction = 2;
                tiles[startX][height-1] = startTile;
                break;
            case 2:
                startTile = new Tile(false,false,false,true,TileType.START, 's');
                startX = 0;
                startY = (int) (Math.random() * (height-2) +1);
                direction = 1;
                tiles[startX][startY] = startTile;
                break;
            case 3:
                startTile = new Tile(false,false,true,false,TileType.START, 's');
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
            while (tiles[xNew][yNew].getShortcut() != 'o' && tiles[xNew][yNew].getShortcut() != 'n' ) {
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
            tiles[x][y] = tileTileShortcutHashMap.get(tiles[x][y]);
        }
        tiles[x][y].setTileType(TileType.FINISH);
        tiles[x][y].setShortcut('f');
        gameField.setStartX(startX);
        gameField.setStartY(startY);
        gameField.setField(tiles);
        return gameField;
    }
}
