package de.sopa.scene.justplay;

import de.sopa.manager.BaseSceneService;
import de.sopa.model.justplay.JustPlayLevelResult;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface JustPlaySceneService extends BaseSceneService {

    void loadJustPlayScoreSceneFromJustPlayScene(JustPlayLevelResult justPlayLevelResult);

    void loadJustPlaySceneFromJustPlayScoreScene();
}
