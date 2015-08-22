package de.sopa.model.justplay;

/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayLevelResult {

    private final int leftTime;
    private final int moves;

    public JustPlayLevelResult(int leftTime, int moves) {

        this.leftTime = leftTime;
        this.moves = moves;
    }

    public int getLeftTime() {

        return leftTime;
    }


    public int getMoves() {

        return moves;
    }
}
