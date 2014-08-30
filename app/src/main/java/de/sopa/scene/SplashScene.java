package de.sopa.scene;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SplashScene extends BaseScene {

    private Sprite splash;

    @Override
    public void createScene(Object o) {
        splash = new Sprite(0, 0, camera.getWidth(), camera.getHeight(), resourcesManager.splash_region, vbom){
            @Override
            protected void preDraw(GLState pGLState, Camera pCamera) {
                super.preDraw(pGLState, pCamera);
                pGLState.enableDither();
            }
        };
        attachChild(splash);
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }
}
