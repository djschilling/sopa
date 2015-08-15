package de.sopa.scene.loading;

import de.sopa.manager.ResourcesManager;
import de.sopa.scene.BaseScene;
import org.andengine.engine.Engine;

/**
 * David Schilling - davejs92@gmail.com
 **/
public class LoadingSceneServiceImpl implements LoadingSceneService {
    private final Engine engine;
    private BaseScene loadingScene;

    public LoadingSceneServiceImpl(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void start() {
        setScene(loadingScene);
    }

    @Override
    public void end() {

    }

    @Override
    public BaseScene getCurrentScene() {
        return loadingScene;
    }

    @Override
    public void initialStart() {
        ResourcesManager.getInstance().loadLoadingSceneResources();
        loadingScene = new LoadingScene();

    }

    private void setScene(BaseScene scene) {
        engine.setScene(scene);
        loadingScene = scene;
    }
}
