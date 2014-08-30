package de.sopa.manager;

import de.sopa.model.GameField;
import de.sopa.scene.BaseScene;
import org.andengine.ui.IGameInterface;

/**
 * David Schilling - davejs92@gmail.com
 */
public interface SceneService {

    BaseScene getCurrentScene();

    void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback);

    void createMenuScene();

    public void loadMenuSceneFromGameScene();

    public void loadMenuSceneFromLevelChoiceScene();

    public void loadGameScene();

    public void loadLevelChoiceScene();

    void loadGameSceneFromLevelChoiceScene(final GameField gameField);
}
