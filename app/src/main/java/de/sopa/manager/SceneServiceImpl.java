package de.sopa.manager;


import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;
import de.sopa.scene.BaseScene;
import de.sopa.scene.choicelevel.LevelChoiceScene;
import de.sopa.scene.game.LevelModeGameScene;
import de.sopa.scene.loading.LoadingScene;
import de.sopa.scene.menu.MainMenuScene;
import de.sopa.scene.score.ScoreScreen;
import de.sopa.scene.settings.SettingsScene;
import de.sopa.scene.tutorial.TutorialScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class SceneServiceImpl implements SceneService {

    private BaseScene menuScene;
    private BaseScene gameScene;
    private BaseScene loadingScene;
    private BaseScene choiceScene;
    private BaseScene scoreScene;
    private BaseScene settingsScene;

    private BaseScene currentScene;

    private Engine engine;
    private BaseScene tutorialScene;
    private CreditsScene creditsScene;

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
    public void createLoadingScene() {
        ResourcesManager.getInstance().loadLoadingSceneResources();
        loadingScene = new LoadingScene();
        currentScene = loadingScene;
    }


    @Override
    public void createMenuScene() {
        ResourcesManager.getInstance().loadMenuSceneResources();
        menuScene = new MainMenuScene();
        ResourcesManager.getInstance().loadLoadingSceneResources();
        setScene(menuScene);
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


    public void loadScoreScreen(final LevelResult level) {
        disposeGameScene();
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
    public void loadCreditsFromMenuScene() {
        setScene(loadingScene);
        disposeMenuScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadLevelCreditsSceneResources();
                creditsScene = new CreditsScene();
                setScene(creditsScene);
            }
        }));
    }

    @Override
    public void loadMenuSceneFromCreditsScene() {
        setScene(loadingScene);
        disposeCreditsScene();
        ResourcesManager.getInstance().unloadCreditsSceneResources();
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

    private void disposeCreditsScene() {
        ResourcesManager.getInstance().unloadCreditsSceneResources();
        creditsScene.disposeScene();
        creditsScene = null;
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

    @Override
    public void loadLevelChoiceFromTutorial() {
        setScene(loadingScene);
        disposeTutorialScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadLevelChoiceSceneResources();
                choiceScene = new LevelChoiceScene();
                setScene(choiceScene);
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
