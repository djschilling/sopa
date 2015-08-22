package de.sopa.scene;

import android.app.Activity;

import de.sopa.helper.LevelService;

import de.sopa.manager.ResourcesManager;
import de.sopa.manager.SettingsService;
import de.sopa.manager.StoryService;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;

import org.andengine.entity.scene.Scene;

import org.andengine.opengl.vbo.VertexBufferObjectManager;


/**
 * This abstract scene should always be used when creating new scenes.
 *
 * <p>It holds resources which are often needed in scenes.</p>
 *
 * @author  David Schilling - davejs92@gmail.com
 */
public abstract class BaseScene extends Scene {

    protected final StoryService storyService;
    protected final LevelService levelService;
    protected final SettingsService settingsService;
    protected Engine engine;
    protected Activity activity;
    protected ResourcesManager resourcesManager;
    protected VertexBufferObjectManager vbom;
    protected Camera camera;
    protected BaseScene baseScene;

    public BaseScene() {

        this.resourcesManager = ResourcesManager.getInstance();
        this.engine = resourcesManager.engine;
        this.activity = resourcesManager.activity;
        this.vbom = resourcesManager.vbom;
        this.camera = resourcesManager.camera;
        this.storyService = resourcesManager.storyService;
        this.levelService = resourcesManager.levelService;
        this.baseScene = this;
        this.settingsService = resourcesManager.settingsService;
    }

    /**
     * Called when Android Back Button is pressed.
     */
    public abstract void onBackKeyPressed();


    public abstract void disposeScene();
}
