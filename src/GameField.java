/**
 * David Schilling - davejs92@gmail.com
 */
public class GameField {

    private Tile[][] field;
    private PathState[][] pathStates;
    private int[] directionsX = new int[]{0, 1, 0, -1};
    private int[] directionsY = new int[]{1, 0, -1, 0};

    public GameField(int width, int height) {
//        field = new Tile[][] {
//                new Tile[]{new Tile(false, false, false, false, TileType.NONE), new Tile(false, true, true, false, TileType.NONE), new Tile(false, false, false, false, TileType.NONE), new Tile(false, true, true, false, TileType.NONE)},
//                new Tile[]{new Tile(false, false, false, true, TileType.START), new Tile(false, true, true, true, TileType.PUZZLE), new Tile(false, true, true, false, TileType.PUZZLE), new Tile(false, false, false, false, TileType.NONE)},
//                new Tile[]{new Tile(false, false, false, false, TileType.NONE), new Tile(true, false, false, false, TileType.PUZZLE), new Tile(true, true, false, false, TileType.PUZZLE), new Tile(false, false, false, false, TileType.NONE)},
//                new Tile[]{new Tile(false, false, false, false, TileType.NONE), new Tile(false, false, false, false, TileType.NONE), new Tile(true, true, false, false, TileType.FINISH), new Tile(false, false, false, false, TileType.NONE)}
//        };
        field = new Tile[width + 2][height + 2];
        initializeField();
        pathStates = new PathState[width + 2][height + 2];
    }

    private void initializeField() {
        field[0][0] = new Tile(false, false, false, false, TileType.NONE);
        field[1][0] = new Tile(false, true, true, false, TileType.NONE);
        field[2][0] = new Tile(false, false, false, false, TileType.NONE);
        field[3][0] = new Tile(false, true, true, false, TileType.NONE);
        field[0][1] = new Tile(false, false, false, true, TileType.START);
        field[1][1] = new Tile(false, true, true, true, TileType.PUZZLE);
        field[2][1] = new Tile(false, true, true, false, TileType.PUZZLE);
        field[3][1] = new Tile(false, false, false, false, TileType.NONE);
        field[0][2] = new Tile(false, false, false, false, TileType.NONE);
        field[1][2] = new Tile(true, false, false, false, TileType.PUZZLE);
        field[2][2] = new Tile(true, true, false, false, TileType.PUZZLE);
        field[3][2] = new Tile(false, false, false, false, TileType.NONE);
        field[0][3] = new Tile(false, false, false, false, TileType.NONE);
        field[1][3] = new Tile(false, false, false, false, TileType.NONE);
        field[2][3] = new Tile(true, true, false, false, TileType.FINISH);
        field[3][3] = new Tile(false, false, false, false, TileType.NONE);
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
            int xNew = x + directionsX[direction];
            int yNew = y + directionsY[direction];
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
            Tile tileNew = field[xNew][yNew];
            Tile tile = field[x][y];
            if(tileNew.getTileType() != TileType.NONE && pathStates[xNew][yNew] == PathState.UNDEFINED){
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
        pathStates[xNew][yNew] = PathState.POSSIBLE;
    }

    private boolean foundFinish(int xNew, int yNew) {
        if(field[xNew][yNew].getTileType() == TileType.FINISH) {
            return true;
        }
        return false;
    }

    private void markAsImpossible(int xNew, int yNew) {
        pathStates[xNew][yNew] = PathState.IMPOSSIBLE;
    }

    public void printBacktracking(){
        System.out.println("Backtracking Result :");
        for (int i = 0; i < pathStates.length; i++){
            for(int j = 0; j < pathStates[0].length; j++){
                System.out.print(pathStates[j][i] + "\t");
            }
            System.out.println();
        }
    }
    public void printField(){
        System.out.println("Field:");
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[0].length; j++){
                System.out.print(field[j][i].getTileType() + "\t");
            }
            System.out.println();
        }
    }
}
