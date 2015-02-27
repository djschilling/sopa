package de.sopa.scene.menu;

import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class MainMenuScene extends BaseScene {

    @Override
    public void createScene(Object o) {
        createBackground();
        createMenuChildScene();
        resourcesManager.musicService.playMusic();
    }


    @Override
    public void onBackKeyPressed() {
        System.exit(0);
    }


    @Override
    public void disposeScene() {
        final MainMenuScene mainMenuScene = this;

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                mainMenuScene.detachChildren();
            }
        }));
    }

    private void createBackground() {
        setBackground(new Background(Color.BLACK));
    }

    private void createMenuChildScene() {

        final ButtonSprite playItemSprite = new ButtonSprite(0, 0, resourcesManager.level_mode_region, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadLevelChoiceSceneFromMenuScene();
            }
        });

        final ButtonSprite settingsSprite = new ButtonSprite(0, 0, resourcesManager.settingsRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadSettingsFromMenuScene();
            }
        });

        final ButtonSprite creditsButton = new ButtonSprite(0, 0, resourcesManager.creditsRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadCreditsFromMenuScene();
            }
        });

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                playItemSprite.registerEntityModifier(new MoveXModifier(1f, -camera.getWidth(), 0));
                settingsSprite.registerEntityModifier(new MoveXModifier(1f, camera.getWidth(), 0));
                creditsButton.registerEntityModifier(new MoveYModifier(1f, camera.getHeight(), camera.getHeight() / 2 + creditsButton.getHeight()));
            }
        }));

        playItemSprite.setPosition(camera.getWidth() / 2 - playItemSprite.getWidthScaled() / 2, camera.getHeight() / 2 - playItemSprite.getHeightScaled());
        attachChild(playItemSprite);
        registerTouchArea(playItemSprite);

        settingsSprite.setPosition(camera.getWidth() / 2 - settingsSprite.getWidthScaled() / 2, camera.getHeight() / 2);
        attachChild(settingsSprite);
        registerTouchArea(settingsSprite);

        attachChild(creditsButton);
        registerTouchArea(creditsButton);
    }
}
