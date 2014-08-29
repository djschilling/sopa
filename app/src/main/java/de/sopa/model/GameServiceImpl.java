package de.sopa.model;

import de.sopa.observer.Observer;
import java.util.ArrayList;
import java.util.List;

/**
 * David Schilling - davejs92@gmail.com
 */
public class GameServiceImpl implements GameService {

    private final GameFieldService gameFieldService;
    private final FieldHandler fieldCreator;
    private GameField gameField;
    private List<Observer> observers;
    private boolean solvedPuzzle;

    public GameServiceImpl() {
        observers = new ArrayList<>();
        gameFieldService = new GameFieldService();
        fieldCreator = new FieldHandler();
        solvedPuzzle = false;
    }

    @Override
    public boolean solvedPuzzle() {
        return solvedPuzzle;
    }

    @Override
    public void shiftLine(boolean horizontal, int row, int steps) {
        gameFieldService.shiftLine(gameField, horizontal, row, steps);
        solvedPuzzle = gameFieldService.solvedPuzzle(gameField);
        notifyAllObserver();
    }

    @Override
    public GameField getGameField() {
        return gameField;
    }

    @Override
    public void startGame() {
        gameField = fieldCreator.generateSolvedField(6, 6);
        new GameFieldDestroyer().destroyField(gameField, 3, 5, 3);
        solvedPuzzle = gameFieldService.solvedPuzzle(gameField);
    }

    @Override
    public void startGame(GameField gameField) {
        this.gameField = gameField;
        solvedPuzzle = gameFieldService.solvedPuzzle(gameField);
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
