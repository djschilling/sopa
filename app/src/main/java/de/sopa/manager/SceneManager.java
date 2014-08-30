package de.sopa.manager;

import de.sopa.scene.*;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SceneManager {

    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene choiceScene;

    private static final SceneManager INSTANCE = new SceneManager();


    private BaseScene currentScene;

    private Engine engine;

    private SceneManager() {
        engine = ResourcesManager.getInstance().engine;
    }

    public enum SceneType {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
        SCENE_CHOICE
    }

    public void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
    }

    public void setScene(SceneType sceneType) {
        switch (sceneType) {
            case SCENE_MENU:
                setScene(menuScene);
                break;
            case SCENE_GAME:
                setScene(gameScene);
                break;
            case SCENE_SPLASH:
                setScene(splashScene);
                break;
            case SCENE_LOADING:
                setScene(loadingScene);
                break;
            case SCENE_CHOICE:
                setScene(choiceScene);
            default:
                break;
        }
    }

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourcesManager.getInstance().loadSplashSceneResources();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene() {
        ResourcesManager.getInstance().unloadSplashSceneResources();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createMenuScene() {
        ResourcesManager.getInstance().loadMenuSceneResources();
        menuScene = new MainMenuScene();
        ResourcesManager.getInstance().loadLoadingSceneResources();
        loadingScene = new LoadingScene();
        setScene(menuScene);
        disposeSplashScene();
    }

    public void loadMenuSceneFromGameScene(final Engine mEngine) {
        setScene(loadingScene);
        disposeGameScene();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuSceneResources();
                menuScene = new MainMenuScene();
                setScene(menuScene);
            }
        }));
    }

    public void loadMenuSceneFromLevelChoiceScene(final Engine mEngine) {
        setScene(loadingScene);
        disposeLevelChoiceScene();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuSceneResources();
                menuScene = new MainMenuScene();
                setScene(menuScene);
            }
        }));
    }
    private void disposeGameScene() {
        ResourcesManager.getInstance().unloadGameSceneResources();
        gameScene.disposeScene();
        gameScene = null;
    }

    public void loadGameScene(final Engine mEngine) {
        setScene(loadingScene);
        disposeMenuScene();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                gameScene = new GameScene();
                setScene(gameScene);
            }
        }));
    }

    private void disposeMenuScene() {
        ResourcesManager.getInstance().unloadMenuSceneResources();
        menuScene.disposeScene();
        menuScene = null;
    }
    public void loadLevelChoiceScene(final Engine mEngine) {
        setScene(loadingScene);
        disposeMenuScene();
        mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadLevelChoiceSceneResources();
                choiceScene = new LevelChoiceScene();
                setScene(choiceScene);
            }
        }));
    }
    private void disposeLevelChoiceScene() {
        ResourcesManager.getInstance().unloadLevelChoiceSceneResources();
        choiceScene.disposeScene();
        choiceScene = null;
    }

}
