package de.sopa.scene;

import de.sopa.IOHandler;
import de.sopa.IOHandlerAndroid;
import de.sopa.manager.ResourcesManager;
import de.sopa.manager.SceneManager;
import de.sopa.model.GameService;
import de.sopa.observer.Observer;
import org.andengine.entity.sprite.Sprite;

/**
 * @author Raphael Schilling
 */
public class LevelChoiceScene extends BaseScene {
    @Override
    public void createScene() {
    }

    @Override
    public void onBackKeyPressed() {
        SceneManager.getInstance().loadMenuSceneFromLevelChoiceScene(engine);
    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return null;
    }

    @Override
    public void disposeScene() {

    }
}
