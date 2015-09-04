package de.sopa.model.justplay;

/**
 * @author  Raphael Schilling - raphaelschiling@gmail.com
 */
public class JustPlayResult {

    private final int levelAnzahl;
    private final int leftTime;
    private final int extraTime;
    private final int lastScore;

    private final int score;

    public JustPlayResult(int levelCount, int leftTime, int extraTime, int lastScore, int score) {

        this.levelAnzahl = levelCount;
        this.leftTime = leftTime;
        this.extraTime = extraTime;
        this.lastScore = lastScore;
        this.score = score;
    }

    public int getLevelCount() {

        return levelAnzahl;
    }


    public int getExtraTime() {

        return extraTime;
    }


    public int getLeftTime() {

        return leftTime;
    }


    public int getScore() {

        return score;
    }


    public int getLastScore() {

        return lastScore;
    }


    public boolean lost() {

        return (getLeftTime() == -1);
    }
}
