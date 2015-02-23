package de.sopa.model;

import de.sopa.observer.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameServiceImpl implements GameService {

    private final GameFieldService gameFieldService;
    private Level level;
    private List<Observer> observers;
    private boolean solvedPuzzle;

    public GameServiceImpl() {
        observers = new ArrayList<>();
        gameFieldService = new GameFieldService();
        solvedPuzzle = false;
    }

    @Override
    public boolean solvedPuzzle() {
        return solvedPuzzle;
    }

    @Override
    public void shiftLine(boolean horizontal, int row, int steps) {
        gameFieldService.shiftLine(level, horizontal, row, steps);
        solvedPuzzle = gameFieldService.solvedPuzzle(level);
        notifyAllObserver();
    }

    @Override
    public Level getLevel() {
        return level;
    }

    @Override
    public void startGame(Level level) {
        this.level = level;
        solvedPuzzle = gameFieldService.solvedPuzzle(level);
        level.resetMovesCounter();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    private void notifyAllObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
