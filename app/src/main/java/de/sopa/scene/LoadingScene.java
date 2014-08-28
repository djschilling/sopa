package de.sopa.scene;

import de.sopa.MainActivity;
import de.sopa.manager.ResourcesManager;
import de.sopa.manager.SceneManager;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LoadingScene extends BaseScene {
    @Override
    public void createScene() {
        int foo = (MainActivity.CAMERA_HEIGHT - MainActivity.CAMERA_WIDTH) / 2;
        setBackground(new Background(Color.WHITE));
        this.attachChild(new Sprite(0, foo, MainActivity.CAMERA_WIDTH, MainActivity.CAMERA_WIDTH, ResourcesManager.getInstance().loadingScreenBackgroundRegion, vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        });
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
