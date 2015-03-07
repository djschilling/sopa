package de.sopa.model.levelchoice;

import de.sopa.observer.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelChoiceServiceImpl implements LevelChoiceService {

    private final int screenCount;
    private int currentScreen;
    private List<Observer> observers;

    public LevelChoiceServiceImpl(int levelCount, int levelPerScreen) {
        currentScreen = 0;
        screenCount = (((int) (levelCount - 0.1) / levelPerScreen)) + 1;
        observers = new ArrayList<>();
    }

    @Override
    public void moveRight() {
        if (currentScreen < screenCount - 1) {
            currentScreen++;
        }
        updateAll();
    }

    @Override
    public void moveRight(int screen) {
        if(screen < screenCount) {
            currentScreen = screen;
        }
        updateAll();
    }

    @Override
    public void moveLeft() {
        if(currentScreen > 0) {
            currentScreen--;
        }
        updateAll();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public int getCurrentScreen() {
        return currentScreen;
    }

    @Override
    public boolean isLastScene() {
        return currentScreen == screenCount - 1;
    }

    @Override
    public boolean isFirstScene() {
        return currentScreen == 0;
    }

    private void updateAll(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
