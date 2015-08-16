package de.sopa.model.game;

import de.sopa.observer.Observer;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface TimeBasedGameService extends GameService, Observer {

    int getRemainingTime();

    void start();
}
