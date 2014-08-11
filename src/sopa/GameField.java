package sopa;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameField {

    private Tile[][] field;
    private final GameEndService gameEndService;


    public GameField(Tile[][] field) {
        gameEndService = new GameEndService(field.length, field[0].length);
        this.field = field;
    }

    public boolean solvedPuzzle() {
        return gameEndService.solvedPuzzle(0, 1, field.length, field[0].length, field);
    }



    public void printBacktracking() {
        gameEndService.printBacktracking();
    }
    public void printField(){
        System.out.println("Field:");
        for(int i = 0; i < field[0].length; i++){
            for(int j = 0; j < field.length; j++){
                System.out.print(field[j][i].getTileType() + "\t");
            }
            System.out.println();
        }
    }

    public void shiftLine(boolean horizontal, int row, int steps) {
        if(horizontal) {
            Tile line[] = new Tile[field.length-2];
            for(int i = 0; i < field.length - 2; i++) {
                line[i] = field[i+1][row+1];
            }
            for(int i = 0; i < field.length-2; i++) {
                int newPosition = i + steps;
                newPosition = shiftToPositive(newPosition,field.length-2);
                newPosition = newPosition%(field.length-2);
                field[newPosition+1][row+1] = line[i];
            }
        } else {
            Tile line[] = new Tile[field[0].length-2];
            for(int i = 0; i < field[0].length-2; i++) {
                line[i] = field[row+1][i+1];
            }
            for(int i = 0; i < field[0].length-2; i++) {
                int newPosition = (i+steps);
                newPosition = shiftToPositive(newPosition,field[0].length-2);
                newPosition = newPosition%(field[0].length-2);
                field[row+1][newPosition+1] = line[i];
            }
        }
    }

    public void setField(Tile[][] field) {
        this.field = field;
    }

    public Tile[][] getField() {
        return field;
    }

    private int shiftToPositive(int number, int steps) {
        int shifted = number;
        while (shifted < 0) {
            shifted = shifted + steps;
        }
        return shifted;
    }
}
