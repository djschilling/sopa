package de.sopa.manager;


import de.sopa.helper.LevelCreator;
import de.sopa.model.GameFieldDestroyer;
import de.sopa.model.Level;
import de.sopa.scene.*;
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
    private BaseScene scoreScene;
    private BaseScene settingsScene;

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
    public void loadMenuSceneFromScoreScene() {
        setScene(loadingScene);
        disposeScoreScreen();
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
    public void loadGameSceneFromMainMenuScene() {
        setScene(loadingScene);
        disposeMenuScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                Level level = new LevelCreator().generateSolvedField(6, 6);
                new GameFieldDestroyer().destroyField(level, 3, 5);
                gameScene = new JustPlayGameScene(level);
                setScene(gameScene);
            }
        }));
    }
    public void loadGameSceneFromLevelChoiceScene(final Level level) {
        disposeLevelChoiceScene();
        setScene(loadingScene);
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                gameScene = new LevelModeGameScene(level);
                setScene(gameScene);
            }
        }));
    }


    public void loadScoreScreen(final Level level) {
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadScoreSceneResources();
                scoreScene = new ScoreScreen(level);
                setScene(scoreScene);
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

    @Override
    public void loadLevelChoiceSceneFromScoreScene() {
        setScene(loadingScene);
        disposeScoreScreen();
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

    @Override
    public void loadGameSceneFromScoreScene(final Level level) {
        disposeScoreScreen();
        setScene(loadingScene);
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                gameScene = new LevelModeGameScene(level);
                setScene(gameScene);
            }
        }));
    }

    @Override
    public void loadSettingsFromMenuScene() {
        disposeMenuScene();
        setScene(loadingScene);
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadSettingsScene();
                settingsScene = new SettingsScene();
                setScene(settingsScene);
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

    private void disposeScoreScreen() {
        ResourcesManager.getInstance().unloadScoreSceneResources();

    //TODO:Dispose Score Screen
    }
}
