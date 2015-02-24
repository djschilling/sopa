package de.sopa.scene.settings;

import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.sprite.ButtonSprite;

/**
 * Created by raphael on 03.09.14.
 */
public class SettingsScene extends BaseScene {

    private ButtonSprite muteButton;
    private ButtonSprite unmuteButton;

    @Override
    public void createScene(Object o) {
        muteButton =  new ButtonSprite(camera.getWidth()/2+300/2,(camera.getHeight()/2+300/2), resourcesManager.muteRegion,vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                settingsService.switchMute();
                muteButton.setVisible(false);
                unmuteButton.setVisible(true);
                resourcesManager.musicService.unmuteMusic();
            }
        });
        unmuteButton =  new ButtonSprite(camera.getWidth()/2+300/2,(camera.getHeight()/2+300/2), resourcesManager.unMuteRegion,vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                settingsService.switchMute();
                muteButton.setVisible(true);
                unmuteButton.setVisible(false);
                resourcesManager.musicService.muteMusic();
            }
        });
        if(settingsService.isMute()){
            unmuteButton.setVisible(false);
        } else {
            muteButton.setVisible(false);
        }
        attachChild(muteButton);
        attachChild(unmuteButton);
        registerTouchArea(muteButton);
        registerTouchArea(unmuteButton);
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
