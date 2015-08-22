package de.sopa.highscore;

/**
 * @author  David Schilling - schilling@synyx.de
 */
public interface JustPlayScoreService {

    void submitScore(JustPlayScore score);


    JustPlayScore getHighscore();
}
