package de.sopa.observer;

import de.sopa.model.GameService;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface Observer {

    void update();

    void setSubject(GameService gameService);
}
