package de.sopa.scene.settings;

import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;

/**
 * Created by raphael on 03.09.14.
 */
public class SettingsScene extends BaseScene {

    @Override
    public void createScene(Object o) {
        ButtonSprite mute =  new ButtonSprite(camera.getWidth()/2+300/2,(camera.getHeight()/2+300/2), resourcesManager.muteRegion,vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                //TODO: Mute The Music
            }
        });
        attachChild(mute);
        registerTouchArea(mute);
    }

    @Override
    public void onBackKeyPressed() {
        sceneService.loadMenuSceneFromSettingsScene();
    }

    @Override
    public void disposeScene() {
        final SettingsScene settingsScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                settingsScene.detachChildren();
            }
        }));

    }
}
