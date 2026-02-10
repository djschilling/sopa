package com.sopaapp.model.game;

import com.sopaapp.observer.JustPlaySceneObserver;


/**
 * David Schilling - davejs92@gmail.com.
 */
public interface TimeBasedGameService {

    int getRemainingTime();


    void start();


    void attach(JustPlaySceneObserver observer);


    void detatch(JustPlaySceneObserver observer);


    void stop();
}
