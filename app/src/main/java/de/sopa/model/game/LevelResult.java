package de.sopa.model.game;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelResult {

    private int levelId;
    private int moveCount;
    private int stars;

    public LevelResult(int levelId, int moveCount, int stars) {
        this.levelId = levelId;
        this.moveCount = moveCount;
        this.stars = stars;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getStars() {
        return stars;
    }
}
