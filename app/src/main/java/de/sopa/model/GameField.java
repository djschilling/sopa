package de.sopa.model;

/**
 * Created by raphael on 11.08.14.
 */
public class GameField {
    private Tile[][] field;
    private int startX;
    private int startY;
    private int movesCounter;

    public GameField() {
        this.movesCounter = 0;
    }

    public void setField(Tile[][] field) {
        this.field = field;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public Tile[][] getField() {
        return field;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void increaseMovesCounter() { movesCounter++; }

    public int getMovesCount() {
        return movesCounter;
    }

    public void resetMovesCounter() {
        movesCounter = 0;
    }
}
