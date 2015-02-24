package de.sopa.manager;


import de.sopa.model.game.Level;
import de.sopa.scene.BaseScene;
import de.sopa.scene.choicelevel.LevelChoiceScene;
import de.sopa.scene.game.LevelModeGameScene;
import de.sopa.scene.game.TutorialScene;
import de.sopa.scene.loading.LoadingScene;
import de.sopa.scene.menu.MainMenuScene;
import de.sopa.scene.score.ScoreScreen;
import de.sopa.scene.settings.SettingsScene;
import de.sopa.scene.splash.SplashScene;
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
    private BaseScene tutorialScene;

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
        setScene(loadingScene);
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

    @Override
    public void loadMenuSceneFromSettingsScene() {
        setScene(loadingScene);
        disposeSettingsScene();
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
    public void loadGameSceneFromGameScene(final Level level) {
        setScene(loadingScene);
        disposeGameScene();
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
    public void loadTutorialSceneFromLevelChoiceScene() {
        setScene(loadingScene);
        disposeLevelChoiceScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadTutorialSceneResources();
                tutorialScene = new TutorialScene();
                setScene(tutorialScene);
            }
        }));
    }

    @Override
    public void loadFirstLevelFromTutorial() {
        setScene(loadingScene);
        disposeTutorialScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                gameScene = new LevelModeGameScene(ResourcesManager.getInstance().levelService.getLevelById(1));
                setScene(gameScene);
            }
        }));

    }

    private void disposeTutorialScene() {
        ResourcesManager.getInstance().unloadTutorialScene();
        tutorialScene.disposeScene();
        tutorialScene = null;

    }

    private void disposeSettingsScene() {
        ResourcesManager.getInstance().unloadSettingsScene();
    }

    private void disposeLevelChoiceScene() {
        ResourcesManager.getInstance().unloadLevelChoiceSceneTextures();
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
