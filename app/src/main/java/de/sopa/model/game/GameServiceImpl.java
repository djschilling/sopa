package de.sopa.model.game;

import de.sopa.observer.GameSceneObserver;

import java.util.ArrayList;
import java.util.List;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class GameServiceImpl implements GameService {

    private final GameFieldService gameFieldService;
    private Level level;
    private List<GameSceneObserver> observers;
    private boolean solvedPuzzle;

    public GameServiceImpl(Level level) {

        observers = new ArrayList<>();
        gameFieldService = new GameFieldService();
        solvedPuzzle = false;
        this.level = level;
        solvedPuzzle = gameFieldService.solvedPuzzle(level);
        level.resetMovesCounter();
    }

    @Override
    public boolean solvedPuzzle() {

        return solvedPuzzle;
    }



    @Override
    public void shiftLine(boolean horizontal, int row, int steps, boolean silent) {

        if (!solvedPuzzle) {
            gameFieldService.shiftLine(level, horizontal, row, steps);
            solvedPuzzle = gameFieldService.solvedPuzzle(level);

            if (!silent) {
                notifyAllObserver();
            }
        }
    }


    @Override
    public Level getLevel() {

        return level;
    }


    @Override
    public void attach(GameSceneObserver observer) {

        observers.add(observer);
    }


    @Override
    public void detatch(GameSceneObserver observer) {

        observers.remove(observer);
    }


    @Override
    public void notifyAllObserver() {

        for (GameSceneObserver observer : observers) {
            observer.updateGameScene();
        }
    }
}
