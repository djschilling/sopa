package de.sopa.scene;

import android.app.Activity;
import de.sopa.helper.LevelService;
import de.sopa.manager.ResourcesManager;
import de.sopa.manager.SceneService;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * David Schilling - davejs92@gmail.com
 *
 * This abstract scene should always be used when creating new scenes.
 *
 * It holds resources which are often needed in scenes.
 */
public abstract class BaseScene extends Scene {

    protected final SceneService sceneService;
    protected final LevelService levelService;
    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    protected BaseScene baseScene;

    public BaseScene(Object o) {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        this.sceneService = resourcesManager.sceneService;
        this.levelService = resourcesManager.levelService;
        this.baseScene = this;
        createScene(o);
    }
    public BaseScene() {
        this(null);
    }


    /**
     * Called when scene is created.
     * @param o
     */
    public abstract void createScene(Object o);

    /**
     * Called when Android Back Button is pressed.
     */
    public abstract void onBackKeyPressed();


    public abstract void disposeScene();
}
