package de.sopa.scene.splash;

import de.sopa.scene.BaseScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
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
        final SplashScene splashScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                splashScene.detachChildren();
            }
        }));
        splash.detachSelf();
        splash.dispose();
        this.detachSelf();
        this.dispose();
    }
}
