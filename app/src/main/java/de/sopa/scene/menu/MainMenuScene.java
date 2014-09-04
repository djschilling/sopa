package de.sopa.scene.menu;

import de.sopa.scene.BaseScene;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.color.Color;

/**
 * David Schilling - davejs92@gmail.com
 */
public class MainMenuScene extends BaseScene {

    @Override
    public void createScene(Object o) {
        createBackground();
        createMenuChildScene();
        if(resourcesManager.musicIsPlaying == false){
            resourcesManager.menuMusic.play();
            resourcesManager.musicIsPlaying = true;
        }
    }

    @Override
    public void onBackKeyPressed() {
        System.exit(0);
    }


    @Override
    public void disposeScene() {
    }

    private void createBackground() {
        setBackground(new Background(Color.BLACK));
    }

    private void createMenuChildScene() {
        final ButtonSprite playItemSprite = new ButtonSprite(0, 0, resourcesManager.play_region, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadGameSceneFromMainMenuScene();
            }
        });

                playItemSprite.setPosition(camera.getWidth() / 2 - playItemSprite.getWidthScaled() /2, camera.getHeight() / 2 - playItemSprite.getHeightScaled());
        attachChild(playItemSprite);
        registerTouchArea(playItemSprite);
        final ButtonSprite levelItemSprite = new ButtonSprite(0, 0, resourcesManager.level_mode_region, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadLevelChoiceSceneFromMenuScene();
            }
        });

        final ButtonSprite settingsButton = new ButtonSprite(0,0,resourcesManager.settingsRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                sceneService.loadSettingsFromMenuScene();
            }
        });

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                levelItemSprite.registerEntityModifier(new MoveXModifier(1f, camera.getWidth(), 0));
                playItemSprite.registerEntityModifier(new MoveXModifier(1f,-camera.getWidth(),0));
                  settingsButton.registerEntityModifier(new MoveYModifier(1f, camera.getHeight(),camera.getHeight()/2 + settingsButton.getHeight()  ));
            }
        }));
        attachChild(settingsButton);
        registerTouchArea(settingsButton);
        levelItemSprite.setPosition(camera.getWidth() / 2 - levelItemSprite.getWidthScaled() /2, camera.getHeight() / 2);
        attachChild(levelItemSprite);
        registerTouchArea(levelItemSprite);
    }
}
