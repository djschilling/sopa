package de.sopa.model;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelInfo {

    private Integer levelId;
    private boolean locked;
    private Integer fewestMoves;

    public LevelInfo(Integer levelId, boolean locked, Integer fewestMoves) {
        this.locked = locked;
        this.fewestMoves = fewestMoves;
        this.levelId = levelId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Integer getFewestMoves() {
        return fewestMoves;
    }

    public void setFewestMoves(Integer fewestMoves) {
        this.fewestMoves = fewestMoves;
    }

    @Override
    public String toString() {
        return "LevelInfo{" +
                "levelId=" + levelId +
                ", locked=" + locked +
                ", fewestMoves=" + fewestMoves +
                '}';
    }
}
