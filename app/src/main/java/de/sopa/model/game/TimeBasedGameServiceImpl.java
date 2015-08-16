package de.sopa.model.game;

import de.sopa.observer.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class TimeBasedGameServiceImpl implements TimeBasedGameService {

    private final GameServiceImpl gameService;
    private int remainingTime;
    private List<Observer> observers;

    public TimeBasedGameServiceImpl(Level level, int remainingTime) {
        gameService = new GameServiceImpl(level);
        this.remainingTime = remainingTime;
        observers = new ArrayList<>();
    }

    @Override
    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public void start() {
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                remainingTime--;
                if(remainingTime == 0 || solvedPuzzle()) {
                    timer.cancel();
                }
                update();
            }
        };

        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public boolean solvedPuzzle() {
        return remainingTime > 0 && gameService.solvedPuzzle();
    }

    @Override
    public boolean lostLevel() {
        return remainingTime == 0 && !solvedPuzzle();
    }

    @Override
    public void shiftLine(boolean horizontal, int row, int steps) {
        gameService.shiftLine(horizontal, row, steps);
    }

    @Override
    public Level getLevel() {
        return gameService.getLevel();
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
        gameService.attach(observer);
    }

    @Override
    public void detatch(Observer observer) {
        observers.remove(observer);
        gameService.detatch(observer);
    }

    @Override
    public void update() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
