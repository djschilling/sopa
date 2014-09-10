package de.sopa.model;

/**
 * Created by raphael on 08.09.14.
 */
public class StarCalculator {
    public int getStars(int neededMoves, int minimumMoves ) {
        int stars = 0;
        if ((float) (minimumMoves) / neededMoves > 0.9f) {
            stars = 3;
        } else if ((float) (minimumMoves) / neededMoves > 0.4) {
            stars = 2;
        } else {
            stars = 1;
        }
        return stars;
    }
}
