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

    void loadMenuSceneFromScoreScene();

    void loadMenuSceneFromLevelChoiceScene();

    void loadGameSceneFromMainMenuScene();

    void loadLevelChoiceSceneFromMenuScene();

    void loadLevelChoiceSceneFromGameScene();

    void loadScoreScreen(final Level level);
    void loadGameSceneFromLevelChoiceScene(final Level level);

    void loadLevelChoiceSceneFromScoreScene();

    void loadGameSceneFromScoreScene(Level level);

    void loadSettingsFromMenuScene();
}
