/**
 * David Schilling - davejs92@gmail.com
 */
public class GameField {

    private Tile[][] field;
    private PathState[][] pathStates;
    private int[] directionsX = new int[]{0, 1, 0, -1};
    private int[] directionsY = new int[]{1, 0, -1, 0};

    public GameField(int width, int height) {
        field = new Tile[][] {
                new Tile[]{new Tile(false, false, false, false, TileType.NONE), new Tile(false, true, true, false, TileType.NONE), new Tile(false, false, false, false, TileType.NONE), new Tile(false, true, true, false, TileType.NONE)},
                new Tile[]{new Tile(false, false, false, true, TileType.START), new Tile(false, true, true, true, TileType.PUZZLE), new Tile(false, true, true, false, TileType.PUZZLE), new Tile(false, false, false, false, TileType.NONE)},
                new Tile[]{new Tile(false, false, false, false, TileType.NONE), new Tile(true, false, false, false, TileType.PUZZLE), new Tile(true, true, false, false, TileType.PUZZLE), new Tile(false, false, false, false, TileType.NONE)},
                new Tile[]{new Tile(false, false, false, false, TileType.NONE), new Tile(false, false, false, false, TileType.NONE), new Tile(true, true, false, false, TileType.FINISH), new Tile(false, false, false, false, TileType.NONE)}
        };
        pathStates = new PathState[width + 2][height + 2];
    }

    public boolean solvedPuzzle() {
        initializePathStates();
        return searchFinish(0, 1);
    }

    private void initializePathStates() {
        for(int i = 0; i < pathStates.length; i++){
            for(int j = 0; j < pathStates[0].length; j++) {
                pathStates[i][j] = PathState.UNDEFINED;
            }
        }
    }

    public boolean searchFinish(int x, int y){
        for(int direction = 0; direction < 4; direction++){
            int yNew = y + directionsY[direction];
            int xNew = x + directionsX[direction];
            if(possibleTile(x, y ,xNew, yNew, direction)) {
                extendSolution(xNew, yNew);
                if(!foundFinish(xNew, yNew)){
                    if(searchFinish(xNew, yNew)) {
                        return true;
                    } else {
                        markAsImpossible(xNew, yNew);
                    }
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean possibleTile(int x, int y, int xNew, int yNew, int direction) {
        if(xNew >= 0 && xNew < field[0].length && yNew >= 0 && yNew < field.length) {
            Tile tileNew = field[yNew][xNew];
            Tile tile = field[y][x];
            if(tileNew.getTileType() != TileType.NONE && pathStates[yNew][xNew] == PathState.UNDEFINED){
                if(direction == 0) {
                    if (tile.isBottom() && tileNew.isTop()) {
                        return true;
                    }
                } else if(direction == 1){
                    if(tile.isRight() && tileNew.isLeft()) {
                        return true;
                    }
                } else if (direction == 2) {
                    if(tile.isTop() && tileNew.isBottom()) {
                        return true;
                    }
                } else {
                    if(tile.isLeft() && tile.isRight()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void extendSolution(int xNew, int yNew) {
        pathStates[yNew][xNew] = PathState.POSSIBLE;
    }

    private boolean foundFinish(int xNew, int yNew) {
        if(field[yNew][xNew].getTileType() == TileType.FINISH) {
            return true;
        }
        return false;
    }

    private void markAsImpossible(int xNew, int yNew) {
        pathStates[yNew][xNew] = PathState.IMPOSSIBLE;
    }

    public void print(){
        for (int i = 0; i < pathStates.length; i++){
            for(int j = 0; j < pathStates[0].length; j++){
                System.out.print(pathStates[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
