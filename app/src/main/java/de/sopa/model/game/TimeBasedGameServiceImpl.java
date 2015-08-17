package de.sopa.model.game;

import de.sopa.observer.JustPlaySceneObserver;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class TimeBasedGameServiceImpl implements TimeBasedGameService {

    private int remainingTime;
    private List<JustPlaySceneObserver> observers;
    private Timer timer;

    public TimeBasedGameServiceImpl(int remainingTime) {
        this.remainingTime = remainingTime;
        observers = new ArrayList<>();
    }

    @Override
    public int getRemainingTime() {
        return remainingTime;
    }

    @Override
    public void start() {
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                remainingTime--;
                if(remainingTime == 0) {
                    timer.cancel();
                }
                update();
            }
        };

        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    public void attach(JustPlaySceneObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detatch(JustPlaySceneObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void stop() {
        timer.cancel();
    }

    public void update() {
        for (JustPlaySceneObserver observer : observers) {
            observer.updateJustPlayScene();
        }
    }
}
