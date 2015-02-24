package de.sopa.model.game;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelInfo {

    private Integer levelId;
    private boolean locked;
    private Integer fewestMoves;
    private int stars;

    public LevelInfo(Integer levelId, boolean locked, Integer fewestMoves, int starts) {
        this.locked = locked;
        this.fewestMoves = fewestMoves;
        this.levelId = levelId;
        this.stars = starts;
    }

    public Integer getLevelId() {
        return levelId;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
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
