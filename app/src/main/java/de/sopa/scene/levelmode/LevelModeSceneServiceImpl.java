package de.sopa.scene.levelmode;

import de.sopa.manager.ResourcesManager;

import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;

import de.sopa.scene.BaseScene;
import de.sopa.scene.game.LevelModeGameScene;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;


/**
 * David Schilling - davejs92@gmail.com.
 */
public class LevelModeSceneServiceImpl implements LevelModeSceneService {

    private final Engine engine;
    private BaseScene gameScene;
    private BaseScene currentScene;
    private BaseScene scoreScene;
    private BaseScene choiceScene;
    private BaseScene tutorialScene;
    private BaseScene levelCompleteScene;

    public LevelModeSceneServiceImpl(Engine engine) {

        this.engine = engine;
    }

    @Override
    public void loadGameSceneFromLevelChoiceScene(final Level level) {

        choiceScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        gameScene = new LevelModeGameScene(level);
                        setScene(gameScene);
                        choiceScene = null;
                    }
                }));
    }


    @Override
    public void loadScoreScreen(final LevelResult level) {

        gameScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        scoreScene = new ScoreScreen(level);
                        setScene(scoreScene);
                    }
                }));
    }


    @Override
    public void loadLevelModeCompleteSceneFromScoreScene() {
        scoreScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                levelCompleteScene = new LevelModeCompleteScene();
                setScene(levelCompleteScene);
            }
        }));

    }

    @Override
    public void loadLevelChoiceFromLevelModeCompleteScene() {
        levelCompleteScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                choiceScene = new LevelChoiceScene();
                setScene(choiceScene);
            }
        }));
    }

    @Override
    public void loadLevelChoiceSceneFromScoreScene() {

        scoreScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        choiceScene = new LevelChoiceScene();
                        setScene(choiceScene);
                    }
                }));
    }


    @Override
    public void loadGameSceneFromScoreScene(final Level level) {

        scoreScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        gameScene = new LevelModeGameScene(level);
                        setScene(gameScene);
                    }
                }));
    }


    @Override
    public void loadGameSceneFromGameScene(final Level level) {

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        gameScene = new LevelModeGameScene(level);
                        setScene(gameScene);
                    }
                }));
    }


    @Override
    public void loadTutorialSceneFromLevelChoiceScene() {

        choiceScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        tutorialScene = new TutorialScene();
                        setScene(tutorialScene);
                    }
                }));
    }


    @Override
    public void loadFirstLevelFromTutorial() {

        tutorialScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        gameScene = new LevelModeGameScene(ResourcesManager.getInstance().levelService.getLevelById(1));
                        setScene(gameScene);
                    }
                }));
    }


    @Override
    public void loadLevelChoiceFromTutorial() {

        tutorialScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        choiceScene = new LevelChoiceScene();
                        setScene(choiceScene);
                    }
                }));
    }


    @Override
    public void loadLevelChoiceSceneFromGameScene() {

        gameScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {

                engine.unregisterUpdateHandler(pTimerHandler);
                choiceScene = new LevelChoiceScene();
                setScene(choiceScene);
            }
        }));
    }


    @Override
    public void start() {

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {

                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadLevelChoiceSceneResources();
                ResourcesManager.getInstance().loadScoreSceneResources();
                ResourcesManager.getInstance().loadTutorialSceneResources();
                ResourcesManager.getInstance().loadGameSceneResources();
                ResourcesManager.getInstance().loadLevelModeCompleteResources();
                choiceScene = new LevelChoiceScene();
                setScene(choiceScene);
            }
        }));
    }

    public void onTimePassed(final TimerHandler pTimerHandler) {
            engine.unregisterUpdateHandler(pTimerHandler);
            ResourcesManager.getInstance().loadLevelChoiceSceneResources();
            ResourcesManager.getInstance().loadScoreSceneResources();
            ResourcesManager.getInstance().loadTutorialSceneResources();
            ResourcesManager.getInstance().loadGameSceneResources();
            ResourcesManager.getInstance().loadLevelModeCompleteResources();
            choiceScene = new LevelChoiceScene();
            setScene(choiceScene);
            }


    @Override
    public void end() {

        ResourcesManager.getInstance().unloadScoreSceneResources();

        ResourcesManager.getInstance().unloadTutorialScene();

        if (tutorialScene != null) {
            tutorialScene.disposeScene();
            tutorialScene = null;
        }

        ResourcesManager.getInstance().unloadLevelChoiceSceneTextures();

        if (choiceScene != null) {
            choiceScene.disposeScene();
            choiceScene = null;
        }

        ResourcesManager.getInstance().unloadGameSceneResources();

        if (gameScene != null) {
            gameScene.disposeScene();
            gameScene = null;
        }

        ResourcesManager.getInstance().unLoadLevelModeCompleteResources();
        if(levelCompleteScene != null) {
            levelCompleteScene.disposeScene();
            levelCompleteScene = null;
        }
    }


    @Override
    public BaseScene getCurrentScene() {

        return currentScene;
    }


    private void setScene(BaseScene scene) {

        engine.setScene(scene);
        currentScene = scene;
    }
}
