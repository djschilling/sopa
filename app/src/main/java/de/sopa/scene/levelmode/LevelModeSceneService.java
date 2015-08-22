package de.sopa.scene.levelmode;

import de.sopa.manager.BaseSceneService;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;


/**
 * David Schilling - davejs92@gmail.com.
 */
public interface LevelModeSceneService extends BaseSceneService {

    void loadGameSceneFromLevelChoiceScene(final Level level);


    void loadScoreScreen(final LevelResult level);


    void loadLevelChoiceSceneFromScoreScene();


    void loadGameSceneFromScoreScene(final Level level);


    void loadGameSceneFromGameScene(final Level level);


    void loadTutorialSceneFromLevelChoiceScene();


    void loadFirstLevelFromTutorial();


    void loadLevelChoiceFromTutorial();


    void loadLevelChoiceSceneFromGameScene();
}
