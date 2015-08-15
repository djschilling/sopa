package de.sopa.manager;

import de.sopa.helper.LevelCreator;
import de.sopa.model.game.Level;
import de.sopa.model.game.LevelDestroyer;
import de.sopa.scene.BaseScene;
import de.sopa.scene.game.JustPlayGameScene;
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
                justPlayGameScene = new JustPlayGameScene(levelDestroyer.destroyField(levelCreator.generateSolvedField(6, 6), 2, 4));
                setScene(justPlayGameScene);
            }
        }));
    }

    @Override
    public void loadJustPlaySceneSceneFromJustPlaySceneScene(final Level level) {
        engine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                engine.unregisterUpdateHandler(pTimerHandler);
                justPlayGameScene = new JustPlayGameScene(level);
                setScene(justPlayGameScene);
            }
        }));

    }

    @Override
    public void end() {
        ResourcesManager.getInstance().unloadGameSceneResources();
        justPlayGameScene.disposeScene();
        justPlayGameScene = null;
        this.currentScene = null;
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
