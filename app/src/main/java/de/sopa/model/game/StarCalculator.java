package de.sopa.model.game;

/**
 * Calculates the amount of stars one get after winning a level.
 *
 * @author Raphael Schilling
 * @author David Schilling - davejs92@gmail.com
 */
public class StarCalculator {

    public int getStars(int neededMoves, int minimumMoves ) {
        int stars;
        if ((minimumMoves - neededMoves) >= 0) {
            stars = 3;
        } else if (((float) (minimumMoves) / neededMoves) > 0.6) {
            stars = 2;
        } else {
            stars = 1;
        }
        return stars;
    }
}
