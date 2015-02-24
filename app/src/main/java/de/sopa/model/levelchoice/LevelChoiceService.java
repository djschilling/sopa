package de.sopa.model.levelchoice;

import de.sopa.observer.Observer;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface LevelChoiceService {
    void moveRight();

    void moveLeft();

    int getCurrentScreen();

    boolean isLastScene();

    boolean isFirstScene();

    void attach(Observer observer);
}
