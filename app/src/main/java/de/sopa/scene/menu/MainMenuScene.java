package de.sopa.scene.menu;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;

import android.widget.Toast;
import de.sopa.scene.BaseScene;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.text.Text;

import org.andengine.util.color.Color;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class MainMenuScene extends BaseScene {

    private static final String SOPA_TWITTER_URL = "https://twitter.com/sopagame";
    private static final String LINK_TO_STORE = "https://play.google.com/store/apps/details?id=de.sopa";

    public MainMenuScene() {

        super();
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

                    @Override
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

        final ButtonSprite levelModeSprite = new ButtonSprite(camera.getWidth(), 0, resourcesManager.level_mode_region,
                vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        storyService.loadLevelChoiceSceneFromMenuScene();
                    }
                });

        final ButtonSprite justPlaySprite = new ButtonSprite(camera.getWidth(), 0, resourcesManager.just_play_region,
                vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        storyService.loadJustPlaySceneFromMenuScene();
                    }
                });
        final ButtonSprite highscoreButton = new ButtonSprite(-camera.getWidth(), 0, resourcesManager.highscoreRegion,
                vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                    }
                });


        final ButtonSprite creditsButton = new ButtonSprite(0, camera.getHeight(), resourcesManager.creditsRegion, vbom,
                new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

//                        storyService.loadCreditsFromMenuScene();
                        googleService.showLeaderboard();
                    }
                });

        final ButtonSprite settingsSprite = new ButtonSprite(0, 0, resourcesManager.settingsRegion,
                vbom, new ButtonSprite.OnClickListener() {

            @Override
            public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                storyService.loadSettingsFromMenuScene();
            }
        });

        final ButtonSprite twitterLogo = new ButtonSprite(camera.getWidth(), 0, resourcesManager.twitterLogoRegion,
                vbom, new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        final Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(SOPA_TWITTER_URL));
                        activity.startActivity(intent);
                    }
                });
        final ButtonSprite shareLogo = new ButtonSprite(0, 0, resourcesManager.shareLogoTexture, vbom,
                new ButtonSprite.OnClickListener() {

                    @Override
                    public void onClick(ButtonSprite pButtonSprite, float pTouchAreaLocalX, float pTouchAreaLocalY) {

                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "I played SOPA: " + LINK_TO_STORE);
                        activity.startActivity(Intent.createChooser(shareIntent, "Share your thoughts"));
                    }
                });

        final Text sopaText = new Text(camera.getWidth() / 2 - 595 / 2, camera.getHeight() * 0.07f,
                resourcesManager.sopaFont, "SOPA", vbom);

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        levelModeSprite.registerEntityModifier(new MoveXModifier(1f, -camera.getWidth(), 0));
                        justPlaySprite.registerEntityModifier(new MoveXModifier(1f, camera.getWidth(), 0));
                        highscoreButton.registerEntityModifier(new MoveXModifier(1f, -camera.getWidth(), 0));
                        creditsButton.registerEntityModifier(
                            new MoveYModifier(1f, camera.getHeight(),
                                camera.getHeight() * 0.4f + 2 * creditsButton.getHeight()));
                        settingsSprite.registerEntityModifier(new AlphaModifier(1f, 0f, 1f));
                        shareLogo.registerEntityModifier(new AlphaModifier(1f, 0f, 1f));
                        twitterLogo.registerEntityModifier(new AlphaModifier(1f, 0f, 1f));
                        shareLogo.registerEntityModifier(
                            new MoveXModifier(1f, -shareLogo.getWidth(), 0));
                        settingsSprite.registerEntityModifier(new MoveYModifier(1f, camera.getHeight(),
                                camera.getHeight() * 0.8f));
                        twitterLogo.registerEntityModifier(
                            new MoveXModifier(1f, camera.getWidth(), camera.getWidth() - twitterLogo.getWidth()));
                        sopaText.registerEntityModifier(new ScaleModifier(1f, 0f, 1f));
                    }
                }));

        levelModeSprite.setPosition(1080, camera.getHeight() * 0.4f - levelModeSprite.getHeightScaled());
        attachChild(levelModeSprite);
        registerTouchArea(levelModeSprite);

        justPlaySprite.setPosition(1080, camera.getHeight() * 0.4f);
        attachChild(justPlaySprite);
        registerTouchArea(justPlaySprite);

        highscoreButton.setPosition(-camera.getWidth(), camera.getHeight() * 0.4f + highscoreButton.getHeightScaled());
        attachChild(highscoreButton);
        registerTouchArea(highscoreButton);

        attachChild(creditsButton);
        registerTouchArea(creditsButton);


        settingsSprite.setPosition(camera.getWidth() / 2 - settingsSprite.getWidth() / 2, camera.getHeight());
        attachChild(settingsSprite);
        registerTouchArea(settingsSprite);

        twitterLogo.setPosition(1080, camera.getHeight() * 0.8f);
        attachChild(twitterLogo);
        registerTouchArea(twitterLogo);

        shareLogo.setPosition(1080, camera.getHeight() * 0.8f);
        attachChild(shareLogo);
        registerTouchArea(shareLogo);

        sopaText.setScale(0f);
        attachChild(sopaText);
    }
}
