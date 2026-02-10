package com.sopaapp.manager;

import com.sopaapp.model.game.Level;
import com.sopaapp.model.game.LevelResult;
import com.sopaapp.model.game.TimeBasedGameService;
import com.sopaapp.model.justplay.JustPlayLevel;
import com.sopaapp.model.justplay.JustPlayLevelResult;

import com.sopaapp.scene.BaseScene;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public interface StoryService {

    BaseScene getCurrentScene();


    void createLoadingScene();


    void createMenuScene();


    void loadMenuSceneFromLevelChoiceScene();


    void loadLevelChoiceSceneFromMenuScene();


    void loadLevelChoiceSceneFromGameScene();


    void loadJustPlaySceneFromMenuScene();


    void loadJustPlayScoreSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult);


    void loadJustPlayLostSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult);


    void loadScoreScreen(final LevelResult level);


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


    void loadMenuSceneFromJustPlayGameScene();


    void loadJustPlaySceneFromJustPlayScoreScene();


    void loadMenuSceneFromJustPlayScoreScene();


    void loadJustPlaySceneFromJustPlayScene(TimeBasedGameService timeBasedGameService, JustPlayLevel justPlayLevel);


    void loadLevelModeCompleteSceneFromScoreScene();


    void loadLevelChoiceFromLevelModeCompleteScene();


    void loadJustPlaySceneFromJustPlayLostScene();
}
