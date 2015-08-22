package de.sopa.scene.loading;

import de.sopa.manager.ResourcesManager;

import de.sopa.scene.BaseScene;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;

import org.andengine.opengl.util.GLState;

import org.andengine.util.color.Color;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class LoadingScene extends BaseScene {

    public LoadingScene() {

        super();

        float spriteSTartY = (camera.getHeight() - camera.getWidth()) / 2;
        setBackground(new Background(Color.BLACK));
        this.attachChild(new Sprite(0, spriteSTartY, camera.getWidth(), camera.getWidth(),
                ResourcesManager.getInstance().loadingScreenBackgroundRegion, vbom) {

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
    public void disposeScene() {

        final LoadingScene loadingScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        loadingScene.detachChildren();
                    }
                }));
        detachChildren();
    }
}
