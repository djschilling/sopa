package com.sopaapp.manager;

import com.sopaapp.scene.BaseScene;


/**
 * David Schilling - davejs92@gmail.com.
 */
public interface BaseSceneService {

    void start();


    void end();


    BaseScene getCurrentScene();
}
