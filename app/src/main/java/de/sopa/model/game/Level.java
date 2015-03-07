package de.sopa.model.game;

import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class Level {

    private Integer id;
    private Tile[][] field;
    private int startX;
    private int startY;
    private int movesCounter;
    private LevelInfo levelInfo;
    private int minimumMovesToSolve;
    private int tilesCount;


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

    public void increaseMovesCounter() {
        movesCounter++;
    }

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

    public Level copy() {
        Level level = new Level();
        Tile[][] tiles = new Tile[field.length][field[0].length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                tiles[i][j] = field[i][j];
            }
        }
        level.setField(tiles);
        level.setId(id);
        if (levelInfo == null) {
            level.setLevelInfo(null);
        } else {
            level.setLevelInfo(new LevelInfo(levelInfo));
        }
        level.setMinimumMovesToSolve(minimumMovesToSolve);
        level.setStartX(startX);
        level.setStartY(startY);
        level.setTilesCount(tilesCount);
        return level;
    }

    public int getTilesCount() {
        return tilesCount;
    }

    public void setTilesCount(int tilesCount) {
        this.tilesCount = tilesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof Level)){
            return false;
        }

        Level that = (Level) o;

        return new EqualsBuilder().append(id, that.id).append(field, that.field).
                append(startX, that.startX).append(startY, that.startY).
                append(movesCounter, that.movesCounter).append(levelInfo, that.levelInfo).
                append(minimumMovesToSolve, that.minimumMovesToSolve).
                append(tilesCount, that.tilesCount).isEquals();
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + startX;
        result = 31 * result + startY;
        result = 31 * result + movesCounter;
        result = 31 * result + (levelInfo != null ? levelInfo.hashCode() : 0);
        result = 31 * result + minimumMovesToSolve;
        result = 31 * result + tilesCount;
        return result;
    }
}
