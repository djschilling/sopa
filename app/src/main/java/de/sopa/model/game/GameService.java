package de.sopa.model.game;

import de.sopa.observer.Observer;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface GameService {

    boolean solvedPuzzle();

    void shiftLine(boolean horizontal, int row, int steps);

    Level getLevel();

    void startGame(Level level);

    void attach(Observer observer);

    void detach(Observer observer);
}
