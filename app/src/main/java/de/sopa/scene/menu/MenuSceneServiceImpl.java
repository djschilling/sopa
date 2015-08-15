package de.sopa.scene.menu;

import de.sopa.manager.ResourcesManager;
import de.sopa.scene.BaseScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class MenuSceneServiceImpl implements MenuSceneService {
    private final Engine engine;
    private BaseScene currentScene;
    private BaseScene menuScene;

    public MenuSceneServiceImpl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void start() {
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

    public void startSynchron() {
        ResourcesManager.getInstance().loadMenuSceneResources();
        menuScene = new MainMenuScene();
        setScene(menuScene);
    }

    @Override
    public void end() {
        ResourcesManager.getInstance().unloadMenuSceneResources();
        menuScene.disposeScene();
        menuScene = null;
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
