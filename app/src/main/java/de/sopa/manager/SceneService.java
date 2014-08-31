package de.sopa.manager;

import de.sopa.model.Level;
import de.sopa.scene.BaseScene;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface SceneService {

    BaseScene getCurrentScene();

    void createSplashScene();

    void createMenuScene();

    void loadMenuSceneFromGameScene();

    void loadMenuSceneFromLevelChoiceScene();

    void loadGameScene();

    void loadLevelChoiceSceneFromMenuScene();

    void loadLevelChoiceSceneFromGameScene();

    void loadScoreScreen();
    void loadGameSceneFromLevelChoiceScene(final Level level);
}
