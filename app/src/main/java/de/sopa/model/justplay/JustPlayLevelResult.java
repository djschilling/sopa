package de.sopa.model.justplay;

/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayLevelResult {

    private final int leftTime;
    private final int moves;
    private final int minLevelMoves;

    public JustPlayLevelResult(int leftTime, int moves, int minLevelMoves) {

        this.leftTime = leftTime;
        this.moves = moves;
        this.minLevelMoves = minLevelMoves;
    }

    public int getLeftTime() {

        return leftTime;
    }


    public int getMoves() {

        return moves;
    }


    public int getMinLevelMoves() {

        return minLevelMoves;
    }
}
