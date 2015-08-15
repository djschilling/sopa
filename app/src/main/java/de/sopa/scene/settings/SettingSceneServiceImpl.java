package de.sopa.scene.settings;

import de.sopa.manager.ResourcesManager;
import de.sopa.scene.BaseScene;
import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class SettingSceneServiceImpl implements SettingSceneService {

    private final Engine engine;
    private BaseScene settingsScene;

    public SettingSceneServiceImpl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void start() {
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
    public void end() {
        settingsScene.disposeScene();
        settingsScene = null;
        ResourcesManager.getInstance().unloadSettingsScene();
    }

    @Override
    public BaseScene getCurrentScene() {
        return settingsScene;
    }

    private void setScene(BaseScene scene) {
        engine.setScene(scene);
        settingsScene = scene;
    }

}
