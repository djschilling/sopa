package de.sopa;

import android.view.KeyEvent;

import de.sopa.database.LevelInfoDataSource;

import de.sopa.helper.LevelFileService;
import de.sopa.helper.LevelService;
import de.sopa.helper.LevelServiceImpl;

import de.sopa.manager.ResourceLoader;
import de.sopa.manager.ResourcesManager;
import de.sopa.manager.SettingsService;
import de.sopa.manager.StoryService;
import de.sopa.manager.StoryServiceImpl;

import de.sopa.scene.game.GameScene;
import de.sopa.scene.levelmode.ScoreScreen;

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
            levelService, settingsService);
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

        if (!(mEngine.getScene() instanceof GameScene) && !(mEngine.getScene() instanceof ScoreScreen)) {
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
