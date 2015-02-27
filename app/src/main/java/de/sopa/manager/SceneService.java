package de.sopa.manager;

import de.sopa.model.game.Level;
import de.sopa.scene.BaseScene;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface SceneService {

    BaseScene getCurrentScene();

    void createLoadingScene();

    void createMenuScene();

    void loadMenuSceneFromGameScene();

    void loadMenuSceneFromLevelChoiceScene();

    void loadLevelChoiceSceneFromMenuScene();

    void loadLevelChoiceSceneFromGameScene();

    void loadScoreScreen(final Level level);
    void loadGameSceneFromLevelChoiceScene(final Level level);

    void loadLevelChoiceSceneFromScoreScene();

    void loadGameSceneFromScoreScene(Level level);

    void loadSettingsFromMenuScene();

    void loadMenuSceneFromSettingsScene();

    void loadMenuSceneFromScoreScene();

    void loadGameSceneFromGameScene(Level level);

    void loadTutorialSceneFromLevelChoiceScene();

    void loadFirstLevelFromTutorial();

    void loadLevelChoiceFromTutorial();

    void loadCreditsFromMenuScene();

    void loadMenuSceneFromCreditsScene();
}
