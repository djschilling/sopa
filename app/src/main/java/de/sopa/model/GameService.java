package de.sopa.model;

import de.sopa.observer.Observer;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface GameService {

    boolean solvedPuzzle();

    void shiftLine(GameField gameField, boolean horizontal, int row, int steps);

    GameField getGameField();

    void attach(Observer observer);

    void detach(Observer observer);

    void notifyAllObserver();
}
