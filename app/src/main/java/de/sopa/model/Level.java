package de.sopa.model;

public class Level {

    private Integer id;
    private Tile[][] field;
    private int startX;
    private int startY;
    private int movesCounter;
    private LevelInfo levelInfo;
    private int minimumMovesToSolve;

    public Level() {
        this.movesCounter = 0;
    }

    public void setField(Tile[][] field) {
        this.field = field;
    }

    public int getMinimumMovesToSolve() {
        return minimumMovesToSolve;
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

    public LevelInfo getLevelInfo() {
        return levelInfo;
    }

    public void setLevelInfo(LevelInfo levelInfo) {
        this.levelInfo = levelInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMinimumMovesToSolve(int minimumMovesToSolve) {
        this.minimumMovesToSolve = minimumMovesToSolve;
    }
}
