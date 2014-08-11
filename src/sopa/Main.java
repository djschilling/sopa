package sopa;

public class Main {

    public static void main(String[] args) {
       // sopa.GameField gameField = new sopa.GameField(2, 2);
        FieldCreator fieldCreator = new FieldCreator();
        GameField gameField = new GameField(fieldCreator.fromString(new String[]{"nnnnn", "smacn", "nninn", "nnfnn"}));
        gameField.shiftLine(true, 0, -1);
        System.out.println(gameField.solvedPuzzle());
        gameField.printBacktracking();
        gameField.printField();

    }
}
