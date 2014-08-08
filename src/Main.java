public class Main {

    public static void main(String[] args) {
        GameField gameField = new GameField(2, 2);
        System.out.println(gameField.solvedPuzzle());
        gameField.print();

    }
}
