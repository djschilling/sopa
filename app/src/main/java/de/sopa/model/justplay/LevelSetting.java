package de.sopa.model.justplay;

/**
 * @author Raphael Schilling - raphaelschiling@gmail.com
 */
public class LevelSetting {
    private final int size;
    private final int moves;
    private final int extraTime;
    private final int maxScore;

    public LevelSetting(int size, int moves, int extraTime, int maxScore) {
        this.size = size;
        this.moves = moves;
        this.extraTime = extraTime;
        this.maxScore = maxScore;
    }

    public int getExtraTime() {
        return extraTime;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public int getMoves() {
        return moves;
    }

    public int getSize() {
        return size;
    }
}
