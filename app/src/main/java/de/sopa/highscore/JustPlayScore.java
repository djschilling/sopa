package de.sopa.highscore;

/**
 * DTO which contains all relevant infos of an Just play result.
 *
 * @author  David Schilling - schilling@synyx.de
 */
public class JustPlayScore {

    private final int points;
    private final int solvedLevels;

    public JustPlayScore(int points, int solvedLevels) {

        this.points = points;
        this.solvedLevels = solvedLevels;
    }

    public int getPoints() {

        return points;
    }


    public int getSolvedLevels() {

        return solvedLevels;
    }
}
