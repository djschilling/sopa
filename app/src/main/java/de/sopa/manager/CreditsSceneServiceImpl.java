package de.sopa.manager;

import de.sopa.scene.BaseScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class CreditsSceneServiceImpl implements CreditsSceneService {

    private final Engine engine;
    private BaseScene creditsScene;
    private BaseScene currentScene;

    public CreditsSceneServiceImpl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void start() {
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
    public void end() {
        ResourcesManager.getInstance().unloadCreditsSceneResources();
        creditsScene.disposeScene();
        creditsScene = null;
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
