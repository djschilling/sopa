package de.sopa.model.game;

import de.sopa.observer.GameSceneObserver;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public interface GameService {

    boolean solvedPuzzle();


    boolean lostLevel();


    void shiftLine(boolean horizontal, int row, int steps, boolean silent);


    Level getLevel();


    void attach(GameSceneObserver observer);


    void detatch(GameSceneObserver observer);


    void notifyAllObserver();
}
