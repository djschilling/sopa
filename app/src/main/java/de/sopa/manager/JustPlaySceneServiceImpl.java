package de.sopa.manager;

import de.sopa.helper.LevelCreator;
import de.sopa.model.game.Level;
import de.sopa.model.game.LevelDestroyer;
import de.sopa.scene.BaseScene;
import de.sopa.scene.game.JustPlayGameScene;
import de.sopa.scene.score.JustPlayScoreScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class JustPlaySceneServiceImpl implements JustPlaySceneService {

    private final Engine engine;
    private BaseScene currentScene;
    private JustPlayGameScene justPlayGameScene;
    private final LevelCreator levelCreator;
    private final LevelDestroyer levelDestroyer;
    private JustPlayScoreScene scoreScene;


    public JustPlaySceneServiceImpl(Engine engine) {
        this.engine = engine;
        this.levelCreator = new LevelCreator();
        this.levelDestroyer = new LevelDestroyer();
    }

    @Override
    public void start() {
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                ResourcesManager.getInstance().loadGameSceneResources();
                ResourcesManager.getInstance().loadJustPlayScoreResources();
                justPlayGameScene = new JustPlayGameScene(levelDestroyer.destroyField(levelCreator.generateSolvedField(6, 6), 2, 4));
                setScene(justPlayGameScene);
            }
        }));
    }

    @Override
    public void loadJustPlayScoreSceneSceneFromJustPlaySceneScene(final Level level) {
        justPlayGameScene.disposeScene();
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                scoreScene = new JustPlayScoreScene(level);
                setScene(scoreScene);
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
