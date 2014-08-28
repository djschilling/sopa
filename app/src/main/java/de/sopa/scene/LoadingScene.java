package de.sopa.scene;

import de.sopa.manager.SceneManager;
import org.andengine.entity.scene.background.Background;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LoadingScene extends BaseScene{
    @Override
    public void createScene() {
        setBackground(new Background(Color.WHITE));
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_LOADING;
    }

    @Override
    public void disposeScene() {

    }
}
