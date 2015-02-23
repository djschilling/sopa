package de.sopa.model;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameEndService {

    private PathState[][] pathStates;
    private int[] directionsX = new int[]{0, 1, 0, -1};
    private int[] directionsY = new int[]{1, 0, -1, 0};
    private Tile[][] field;


    private void initializePathStates(int width, int heigth) {
        pathStates = new PathState[width][heigth];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < heigth; j++) {
                pathStates[i][j] = PathState.UNDEFINED;
            }
        }
    }

    public boolean solvedPuzzle(int startX, int startY, int width, int heigth, Tile[][] field, int tilesCountForLevel) {
        this.field = field;
        initializePathStates(width, heigth);
        return searchFinish(startX, startY, tilesCountForLevel);
    }


    public boolean searchFinish(int x, int y, int numberMissingTiles) {
        for (int direction = 0; direction < 4; direction++) {
            int xNew = x + directionsX[direction];
            int yNew = y + directionsY[direction];
            if (possibleTile(x, y, xNew, yNew, direction)) {
                extendSolution(xNew, yNew);
                if (!foundFinish(xNew, yNew)) {
                    if (searchFinish(xNew, yNew, numberMissingTiles - 1)) {
                        return true;
                    } else {
                        markAsImpossible(xNew, yNew);
                    }
                } else {
                    if(numberMissingTiles > 0)
                    {
                        return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean possibleTile(int x, int y, int xNew, int yNew, int direction) {
        if (xNew >= 0 && xNew < field.length && yNew >= 0 && yNew < field[0].length) {
            Tile tileNew = field[xNew][yNew];
            Tile tile = field[x][y];
            if (tileNew.getTileType() != TileType.NONE && pathStates[xNew][yNew] == PathState.UNDEFINED) {
                if (direction == 0) {
                    if (tile.isBottom() && tileNew.isTop()) {
                        return true;
                    }
                } else if (direction == 1) {
                    if (tile.isRight() && tileNew.isLeft()) {
                        return true;
                    }
                } else if (direction == 2) {
                    if (tile.isTop() && tileNew.isBottom()) {
                        return true;
                    }
                } else {
                    if (tile.isLeft() && tileNew.isRight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void extendSolution(int xNew, int yNew) {
        pathStates[xNew][yNew] = PathState.POSSIBLE;
    }

    private boolean foundFinish(int xNew, int yNew) {
        if (field[xNew][yNew].getTileType() == TileType.FINISH) {
            return true;
        }
        return false;
    }

    private void markAsImpossible(int xNew, int yNew) {
        pathStates[xNew][yNew] = PathState.IMPOSSIBLE;
    }

    public void printBacktracking() {
        System.out.println("Backtracking Result :");
        for (int i = 0; i < pathStates[0].length; i++) {
            for (int j = 0; j < pathStates.length; j++) {
                System.out.print(pathStates[j][i] + "\t");
            }
            System.out.println();
        }
    }
}
