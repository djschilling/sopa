package de.sopa.model.levelchoice;

import de.sopa.observer.Observer;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public interface LevelChoiceService {
    void moveRight();

    void moveRight(int screen);

    void moveLeft();

    int getCurrentScreen();

    boolean isLastScene();

    boolean isFirstScene();

    void attach(Observer observer);
}
