package de.sopa.manager;

import de.sopa.model.game.Level;

/**
 * David Schilling - davejs92@gmail.com
 **/
public interface JustPlaySceneService extends BaseSceneService{

    void loadJustPlaySceneSceneFromJustPlaySceneScene(final Level level);

    void loadJustPlayScoreSceneSceneFromJustPlaySceneScene(Level level);
}
