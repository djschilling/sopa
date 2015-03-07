package de.sopa.model.levelchoice;

import de.sopa.observer.Observer;

/**
 * Service for calculating screen to show for {@link de.sopa.scene.choicelevel.LevelChoiceScene}
 *
 * @author David Schilling - davejs92@gmail.com
 */
public interface LevelChoiceService {

    /**
     * Move one Screen right.
     */
    void moveRight();

    /**
     * Move one Screen left.
     */
    void moveLeft();

    /**
     * Move to specified screen.
     *
     * @param screen to move to.
     */
    void moveTo(int screen);

    /**
     * @return the current screen number.
     */
    int getCurrentScreen();

    /**
     * @return if the current screen is the last.
     */
    boolean isLastScene();

    /**
     * @return if the current screen is the first.
     */
    boolean isFirstScene();

    /**
     * Attaches an observer to the Service. All observers get notified when there is a state change.
     *
     * @param observer the {@link de.sopa.observer.Observer} to get notified.
     */
    void attach(Observer observer);
}
