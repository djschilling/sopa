package de.sopa.helper;

import de.sopa.model.game.Level;
import de.sopa.model.game.Tile;
import de.sopa.model.game.TileType;

import java.lang.IllegalArgumentException;import java.lang.IllegalStateException;import java.lang.Math;import java.lang.String;
import java.util.ArrayList;

import static de.sopa.helper.TileCharacterMapping.CHARACTER_TILE_MAP;
import static de.sopa.helper.TileCharacterMapping.TILE_CHARACTER_MAP;
import static de.sopa.model.game.TileType.PUZZLE;
import static de.sopa.model.game.TileType.UNDEFINED;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */

public class LevelCreator {

    private int[] directionsX = new int[]{0, 1, 0, -1};
    private int[] directionsY = new int[]{1, 0, -1, 0};
    private Tile[][] tiles;

    public Level generateSolvedField(int width, int height) {
        int number = 0;
        Level level = new Level();
        int startX;
        int startY;
        int direction;
        tiles = new Tile[width][height];
        for(int i = 1; i< width-1; i++) {
            for (int j = 1; j< height-1; j++) {
                tiles[i][j] = new Tile(false,false,false,false, UNDEFINED,'o');
            }
        }
        for(int i = 0; i< width; i++) {
            tiles[i][0] = new Tile(false,false,false,false,TileType.NONE,'n');
        }

        for(int i = 0; i< width; i++) {
            tiles[i][height-1] = new Tile(false,false,false,false,TileType.NONE,'n');
        }

        for(int i = 0; i< height; i++) {
            tiles[0][i] = new Tile(false,false,false,false,TileType.NONE,'n');
        }

        for(int i = 0; i< height; i++) {
            tiles[width-1][i] = new Tile(false,false,false,false,TileType.NONE,'n');
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
            number++;
            if(Math.random()<0.7f && !(startX == x && startY == y )) {
                direction = (int) (Math.random() *4);
            }

            int xNew = x + directionsX[direction];
            int yNew = y + directionsY[direction];
            boolean directions[] = new boolean[]{false,false,false,false};
            while (tiles[xNew][yNew].getTileType() != UNDEFINED && tiles[xNew][yNew].getShortcut() != 'n' ) {
                if (directions[0]&&directions[1]&&directions[2]&&directions[3]) {
                   return generateSolvedField(width, height);
                }
                directions[direction] = true;
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
        for(int i = 1; i< width-1; i++) {
            for (int j = 1; j< height-1; j++) {
                tiles[i][j].setTileType(PUZZLE);
                tiles[i][j].setShortcut(TILE_CHARACTER_MAP.get(tiles[i][j]));
            }
        }
        tiles[x][y].setTileType(TileType.FINISH);
        tiles[x][y].setShortcut('f');
        level.setStartX(startX);
        level.setStartY(startY);
        level.setField(tiles);
        level.setTilesCount(number - 1);
        if(number>(width-2)*(height-2)/3) {
        return level;
        } else {
            return generateSolvedField(width, height);
        }

    }
}

