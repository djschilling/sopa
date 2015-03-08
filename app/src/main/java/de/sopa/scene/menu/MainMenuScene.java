package de.sopa.scene.menu;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import de.sopa.scene.BaseScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.color.Color;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class MainMenuScene extends BaseScene {

    private static final String SOPA_TWITTER_URL = "https://twitter.com/sopagame";
    private static final String LINK_TO_STORE = "https://play.google.com/store/apps/details?id=de.sopa";

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

        final ButtonSprite twitterLogo = new ButtonSprite(camera.getWidth() * 0.7f, camera.getHeight() * 0.8f, resourcesManager.twitterLogoRegion, vbom, new ButtonSprite.OnClickListener() {
            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(SOPA_TWITTER_URL));
                activity.startActivity(intent);
            }
        });
        Text shareText = new Text(camera.getWidth() * 0.03f, camera.getHeight() * 0.8f, resourcesManager.shareFont, "Share", vbom) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                if (pSceneTouchEvent.isActionDown()) {
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "I played SOPA: " + LINK_TO_STORE);
                    activity.startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));
                }
                return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
            }
        };
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                playItemSprite.registerEntityModifier(new MoveXModifier(1f, -camera.getWidth(), 0));
                settingsSprite.registerEntityModifier(new MoveXModifier(1f, camera.getWidth(), 0));
                creditsButton.registerEntityModifier(new MoveYModifier(1f, camera.getHeight(), camera.getHeight() * 0.4f + creditsButton.getHeight()));
            }
        }));

        playItemSprite.setPosition(camera.getWidth() / 2 - playItemSprite.getWidthScaled() / 2, camera.getHeight() * 0.4f - playItemSprite.getHeightScaled());
        attachChild(playItemSprite);
        registerTouchArea(playItemSprite);

        settingsSprite.setPosition(camera.getWidth() / 2 - settingsSprite.getWidthScaled() / 2, camera.getHeight() * 0.4f);
        attachChild(settingsSprite);
        registerTouchArea(settingsSprite);

        attachChild(creditsButton);
        registerTouchArea(creditsButton);

        attachChild(twitterLogo);
        registerTouchArea(twitterLogo);

        attachChild(shareText);
        registerTouchArea(shareText);
    }
}
