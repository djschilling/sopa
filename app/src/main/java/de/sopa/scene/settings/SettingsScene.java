package de.sopa.scene.settings;

import de.sopa.scene.BaseScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.entity.sprite.ButtonSprite;


/**
 * @author  Raphael Schilling
 * @author  David Schilling - davejs92@gmail.com
 */
public class SettingsScene extends BaseScene {

    private ButtonSprite muteButton;
    private ButtonSprite unmuteButton;
    private ButtonSprite playLoggedIn;
    private ButtonSprite playLoggedNotIn;

    public SettingsScene() {

        super();
        muteButton = new ButtonSprite(camera.getWidth() * 0.37f, (camera.getHeight() * 0.8f),
                resourcesManager.muteRegion, vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        settingsService.switchMute();
                        muteButton.setVisible(false);
                        unmuteButton.setVisible(true);
                        resourcesManager.musicService.unmuteMusic();
                    }
                });
        unmuteButton = new ButtonSprite(camera.getWidth() * 0.37f, (camera.getHeight() * 0.8f),
                resourcesManager.unMuteRegion, vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        settingsService.switchMute();
                        muteButton.setVisible(true);
                        unmuteButton.setVisible(false);
                        resourcesManager.musicService.muteMusic();
                    }
                });

        if (settingsService.isMute()) {
            unmuteButton.setVisible(false);
        } else {
            muteButton.setVisible(false);
        }

        attachChild(muteButton);
        attachChild(unmuteButton);
        registerTouchArea(muteButton);
        registerTouchArea(unmuteButton);

        playLoggedIn = new ButtonSprite(camera.getWidth() * 0.37f, (camera.getHeight() * 0.6f),
                resourcesManager.unMuteRegion, vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        googleService.disconnect();
                        settingsService.switchGameServiceAllowed();
                        storyService.loadMenuSceneFromSettingsScene();
                    }
                });
        playLoggedNotIn = new ButtonSprite(camera.getWidth() * 0.37f, (camera.getHeight() * 0.6f),
                resourcesManager.muteRegion, vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        googleService.connect();
                        settingsService.switchGameServiceAllowed();
                        storyService.loadMenuSceneFromSettingsScene();
                    }
                });

        if (googleService.isConnected()) {
            playLoggedNotIn.setVisible(false);
        } else {
            playLoggedIn.setVisible(false);
        }

        attachChild(playLoggedIn);
        attachChild(playLoggedNotIn);
        registerTouchArea(playLoggedIn);
        registerTouchArea(playLoggedNotIn);
    }

    @Override
    public void onBackKeyPressed() {

        storyService.loadMenuSceneFromSettingsScene();
    }


    @Override
    public void disposeScene() {

        final SettingsScene settingsScene = this;
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        settingsScene.detachChildren();
                    }
                }));
    }
}
