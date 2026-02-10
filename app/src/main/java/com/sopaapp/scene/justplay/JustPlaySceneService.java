package com.sopaapp.scene.justplay;

import com.sopaapp.manager.BaseSceneService;

import com.sopaapp.model.game.TimeBasedGameService;
import com.sopaapp.model.justplay.JustPlayLevel;
import com.sopaapp.model.justplay.JustPlayLevelResult;


/**
 * David Schilling - davejs92@gmail.com.
 */
public interface JustPlaySceneService extends BaseSceneService {

    void loadJustPlayScoreSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult);


    void loadJustPlayLostSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult);


    void loadJustPlaySceneFromJustPlayScoreScene();


    void loadJustPlaySceneFromJustPlayScene(TimeBasedGameService timeBasedGameService, JustPlayLevel justPlayLevel);


    void loadJustPlaySceneFromJustPlayLostScene();
}
