package de.sopa.manager;

import de.sopa.scene.BaseScene;
import de.sopa.scene.GameScene;
import de.sopa.scene.LoadingScene;
import de.sopa.scene.MainMenuScene;
import de.sopa.scene.SplashScene;
import org.andengine.engine.Engine;
import org.andengine.ui.IGameInterface;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SceneManager {

    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;

    private static final SceneManager INSTANCE = new SceneManager();

    private SceneType currentSceneType = SceneType.SCENE_SPLASH;

    private BaseScene currentScene;

    private Engine engine = ResourcesManager.getInstance().engine;

    public enum SceneType {
        SCENE_SPLASH,
        SCENE_MENU,
        SCENE_GAME,
        SCENE_LOADING,
    }

    public void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
        currentSceneType = scene.getSceneType();
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
            default:
                break;
        }
    }

    //---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------

    public static SceneManager getInstance() {
        return INSTANCE;
    }

    public SceneType getCurrentSceneType() {
        return currentSceneType;
    }

    public BaseScene getCurrentScene() {
        return currentScene;
    }

    public void createSplashScene(IGameInterface.OnCreateSceneCallback pOnCreateSceneCallback) {
        ResourcesManager.getInstance().loadSplashScreen();
        splashScene = new SplashScene();
        currentScene = splashScene;
        pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
    }

    private void disposeSplashScene() {
        ResourcesManager.getInstance().unloadSplashScreen();
        splashScene.disposeScene();
        splashScene = null;
    }

    public void createMenuScene(SceneType fromScene) {
        ResourcesManager.getInstance().loadMenuResources();
        menuScene = new MainMenuScene();
        loadingScene = new LoadingScene();
        setScene(menuScene);
        if (fromScene == SceneType.SCENE_SPLASH) {
            disposeSplashScene();
        }
        if(fromScene == SceneType.SCENE_GAME) {
            disposeGameScene();
        }
    }

    private void disposeGameScene() {
        ResourcesManager.getInstance().unloadGameScreen();
        gameScene.disposeScene();
        gameScene = null;
    }

    public void loadGameScene() {
        ResourcesManager.getInstance().loadGameResources();
        gameScene = new GameScene();
        setScene(gameScene);
    }

}
