package de.sopa.manager;


import de.sopa.model.GameField;
import de.sopa.scene.BaseScene;
import de.sopa.scene.GameScene;
import de.sopa.scene.LevelChoiceScene;
import de.sopa.scene.LoadingScene;
import de.sopa.scene.MainMenuScene;
import de.sopa.scene.SplashScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 */
public class SceneServiceImpl implements SceneService {

    private BaseScene splashScene;
    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene choiceScene;

    private BaseScene currentScene;

    private Engine engine;

    public SceneServiceImpl(Engine engine) {
        this.engine = engine;
    }

    private void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
    }


    @Override
    public BaseScene getCurrentScene() {
        return currentScene;
    }

    @Override
    public void createSplashScene() {
        ResourcesManager.getInstance().loadSplashSceneResources();
        splashScene = new SplashScene();
        currentScene = splashScene;
    }

    private void disposeSplashScene() {
        ResourcesManager.getInstance().unloadSplashSceneResources();
        splashScene.disposeScene();
        splashScene = null;
    }

    @Override
    public void createMenuScene() {
        ResourcesManager.getInstance().loadMenuSceneResources();
        menuScene = new MainMenuScene();
        ResourcesManager.getInstance().loadLoadingSceneResources();
        loadingScene = new LoadingScene();
        setScene(menuScene);
        disposeSplashScene();
    }

    @Override
    public void loadMenuSceneFromGameScene() {
        setScene(loadingScene);
        disposeGameScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadMenuSceneResources();
                menuScene = new MainMenuScene();
                setScene(menuScene);
            }
        }));
    }

    @Override
    public void loadMenuSceneFromLevelChoiceScene() {
        setScene(loadingScene);
        disposeLevelChoiceScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
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

    @Override
    public void loadGameScene() {
        setScene(loadingScene);
        disposeMenuScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                gameScene = new GameScene(null);
                setScene(gameScene);
            }
        }));
    }
    public void loadGameSceneFromLevelChoiceScene(final GameField gameField) {
        disposeLevelChoiceScene();
        setScene(loadingScene);
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                gameScene = new GameScene(gameField);
                setScene(gameScene);
            }
        }));
    }
    private void disposeMenuScene() {
        ResourcesManager.getInstance().unloadMenuSceneResources();
        menuScene.disposeScene();
        menuScene = null;
    }

    public void loadLevelChoiceSceneFromMenuScene() {
        setScene(loadingScene);
        disposeMenuScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
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

    public void loadLevelChoiceSceneFromGameScene() {
        setScene(loadingScene);
        disposeGameScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadLevelChoiceSceneResources();
                choiceScene = new LevelChoiceScene();
                setScene(choiceScene);
            }
        }));
    }
}
