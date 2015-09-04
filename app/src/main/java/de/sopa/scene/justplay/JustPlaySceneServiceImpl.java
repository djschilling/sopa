package de.sopa.scene.justplay;

import de.sopa.manager.ResourcesManager;

import de.sopa.model.game.TimeBasedGameService;
import de.sopa.model.justplay.JustPlayLevel;
import de.sopa.model.justplay.JustPlayLevelResult;
import de.sopa.model.justplay.JustPlayServiceImpl;

import de.sopa.scene.BaseScene;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;


/**
 * David Schilling - davejs92@gmail.com.
 */
public class JustPlaySceneServiceImpl implements JustPlaySceneService {

    private final Engine engine;
    private JustPlayServiceImpl justPlayService;
    private BaseScene currentScene;
    private JustPlayGameScene justPlayGameScene;
    private JustPlayScoreScene scoreScene;
    private JustPlayLostScene lostScene;

    public JustPlaySceneServiceImpl(Engine engine) {

        this.engine = engine;
    }

    @Override
    public void start() {

        this.justPlayService = new JustPlayServiceImpl();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        ResourcesManager.getInstance().loadGameSceneResources();
                        ResourcesManager.getInstance().loadJustPlayScoreResources();
                        justPlayGameScene = new JustPlayGameScene(justPlayService.getNextLevel());
                        setScene(justPlayGameScene);
                    }
                }));
    }


    @Override
    public void loadJustPlayScoreSceneFromJustPlayScene(final JustPlayLevelResult justPlayLevelResult) {

        justPlayGameScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {

                engine.unregisterUpdateHandler(pTimerHandler);
                scoreScene = new JustPlayScoreScene(justPlayService.calculateResult(justPlayLevelResult));
                setScene(scoreScene);
            }
        }));
    }

    @Override
    public void loadJustPlayLostSceneFromJustPlayScene(final JustPlayLevelResult justPlayLevelResult) {
        justPlayGameScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {

                engine.unregisterUpdateHandler(pTimerHandler);
                lostScene = new JustPlayLostScene(justPlayService.calculateResult(justPlayLevelResult));
                setScene(lostScene);
            }
        }));
    }


    @Override
    public void loadJustPlaySceneFromJustPlayScoreScene() {

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        justPlayGameScene = new JustPlayGameScene(justPlayService.getNextLevel());
                        setScene(justPlayGameScene);
                    }
                }));
    }


    @Override
    public void loadJustPlaySceneFromJustPlayScene(final TimeBasedGameService timeBasedGameService,
        final JustPlayLevel justPlayLevel) {

        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {

                    @Override
                    public void onTimePassed(final TimerHandler pTimerHandler) {

                        engine.unregisterUpdateHandler(pTimerHandler);
                        justPlayGameScene.disposeScene();
                        justPlayGameScene = new JustPlayGameScene(timeBasedGameService, justPlayLevel);
                        setScene(justPlayGameScene);
                    }
                }));
    }


    @Override
    public void end() {

        ResourcesManager.getInstance().unloadGameSceneResources();

        if (justPlayGameScene != null) {
            justPlayGameScene.disposeScene();
            justPlayGameScene = null;
        }

        ResourcesManager.getInstance().unloadJustPlayScoreResources();

        if (scoreScene != null) {
            scoreScene.disposeScene();
            scoreScene = null;
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
