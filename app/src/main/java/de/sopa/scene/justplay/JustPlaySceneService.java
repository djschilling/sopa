package de.sopa.scene.justplay;

import de.sopa.manager.BaseSceneService;
import de.sopa.model.game.Level;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface JustPlaySceneService extends BaseSceneService {

    void loadJustPlayScoreSceneSceneFromJustPlaySceneScene(Level level);

    void loadJustPlaySceneFromJustPlayScoreScene();
}
