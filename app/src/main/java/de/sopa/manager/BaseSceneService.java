package de.sopa.manager;

import de.sopa.scene.BaseScene;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface BaseSceneService {
    void start();
    void end();

    BaseScene getCurrentScene();
}
