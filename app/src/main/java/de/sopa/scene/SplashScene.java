package de.sopa.scene;

import de.sopa.manager.SceneManager;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SplashScene extends BaseScene {

    private Sprite splash;

    @Override
    public void createScene() {
        splash = new Sprite(0, 0, resourcesManager.splash_region, vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        splash.setScale(1.5f);
        splash.setPosition(400, 240);
        attachChild(splash);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public SceneManager.SceneType getSceneType() {
        return SceneManager.SceneType.SCENE_SPLASH;
    }

    @Override
    public void disposeScene() {
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }
}
