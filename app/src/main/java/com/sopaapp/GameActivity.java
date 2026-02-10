package com.sopaapp;

import android.view.KeyEvent;

import com.sopaapp.database.LevelInfoDataSource;

import com.sopaapp.helper.LevelFileService;
import com.sopaapp.helper.LevelService;
import com.sopaapp.helper.LevelServiceImpl;

import com.sopaapp.highscore.JustPlayScoreServiceImpl;

import com.sopaapp.manager.ResourceLoader;
import com.sopaapp.manager.ResourcesManager;
import com.sopaapp.manager.SettingsService;
import com.sopaapp.manager.StoryService;
import com.sopaapp.manager.StoryServiceImpl;

import com.sopaapp.scene.game.GameScene;
import com.sopaapp.scene.levelmode.LevelChoiceScene;
import com.sopaapp.scene.levelmode.ScoreScreen;

import com.sopaapp.scene.menu.MainMenuScene;
import com.sopaapp.scene.settings.SettingsScene;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

import org.andengine.entity.scene.Scene;

import org.andengine.ui.activity.BaseGameActivity;


/**
 * @author  Raphael Schilling
 * @author  David Schilling - davejs92@gmail.com
 */
public class GameActivity extends BaseGameActivity {

    private static final float CAMERA_WIDTH = 1080;
    private static final float CAMERA_HEIGHT = 1920;
    private Camera camera;
    private LevelInfoDataSource levelInfoDataSource;

    @Override
    public EngineOptions onCreateEngineOptions() {

        camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

        EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED,
                new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
        engineOptions.getAudioOptions().setNeedsMusic(true);

        return engineOptions;
    }


    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {

        levelInfoDataSource = new LevelInfoDataSource(this);
        levelInfoDataSource.open();

        LevelService levelService = new LevelServiceImpl(new LevelFileService(this), levelInfoDataSource);
        SettingsService settingsService = new SettingsService(getApplicationContext());
        ResourcesManager.prepareManager(mEngine, this, camera, getVertexBufferObjectManager(),
            new ResourceLoader(getTextureManager(), getAssets(), getFontManager()), new StoryServiceImpl(mEngine),
            levelService, settingsService, new JustPlayScoreServiceImpl(levelInfoDataSource));
        levelService.updateLevelInfos();

        if (settingsService.isFirstTime()) {
            levelService.unlockLevel(1);
        }

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }


    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {

        StoryService storyService = ResourcesManager.getInstance().storyService;
        storyService.createLoadingScene();
        pOnCreateSceneCallback.onCreateSceneFinished(storyService.getCurrentScene());
    }


    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {

        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        mEngine.unregisterUpdateHandler(pTimerHandler);
                        ResourcesManager.getInstance().storyService.createMenuScene();
                    }
                }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ResourcesManager.getInstance().storyService.getCurrentScene().onBackKeyPressed();
        }

        return false;
    }


    @Override
    protected void onDestroy() {

        super.onDestroy();
        System.exit(0);
    }


    @Override
    public synchronized void onResumeGame() {

        if (ResourcesManager.getInstance().settingsService.isMute()) {
            ResourcesManager.getInstance().musicService.muteMusic();
        }

        if ((mEngine.getScene() instanceof MainMenuScene) || (mEngine.getScene() instanceof SettingsScene) || (mEngine.getScene() instanceof LevelChoiceScene)) {
            ResourcesManager.getInstance().musicService.playMusic();
        }

        levelInfoDataSource.open();
        super.onResumeGame();
    }


    @Override
    public synchronized void onPauseGame() {

        ResourcesManager.getInstance().musicService.stopMusic();
        levelInfoDataSource.close();
        super.onPauseGame();
    }
}
