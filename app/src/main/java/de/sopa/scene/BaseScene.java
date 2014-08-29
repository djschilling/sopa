package de.sopa.scene;

import android.app.Activity;
import de.sopa.manager.ResourcesManager;
import de.sopa.manager.SceneManager;
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

    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;

    public BaseScene() {
        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        createScene();
    }

    /**
     * Called when scene is created.
     */
    public abstract void createScene();

    /**
     * Called when Android Back Button is pressed.
     */
    public abstract void onBackKeyPressed();

    /**
     *
     * @return the type of the scene.
     */
    public abstract SceneManager.SceneType getSceneType();

    public abstract void disposeScene();
}
