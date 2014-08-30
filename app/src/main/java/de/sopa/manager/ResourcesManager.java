package de.sopa.manager;

import de.sopa.MainActivity;
import de.sopa.TileResourceLoader;
import java.util.Map;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * David Schilling - davejs92@gmail.com
 */
public class ResourcesManager {
    private static final ResourcesManager INSTANCE = new ResourcesManager();

    public Engine engine;
    public MainActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    private TileResourceLoader tileResourceLoader;

    public ITextureRegion splash_region;

    public ITextureRegion level_mode_region;
    public ITextureRegion play_region;

    public ITextureRegion loadingScreenBackgroundRegion;

    public Map<Character, TextureRegion> regionTileMap;
    public ITextureRegion tilesBorderRegion;
    public ITextureRegion saveButtonRegion;



    public void loadSplashSceneResources() {
        splash_region = tileResourceLoader.getTexture("scenes/splash/splash.png");
    }
    public void loadMenuSceneResources() {
        loadMenuSceneGraphics();
    }

    public void loadLoadingSceneResources() {
        loadingScreenBackgroundRegion = tileResourceLoader.getTexture("scenes/loading/LoadingScreen.png");
    }

    public void loadGameSceneResources() {
        loadGameSceneGraphics();
    }

    private void loadMenuSceneGraphics() {
        play_region = tileResourceLoader.getTexture("scenes/menu/JustPlay.png");
        level_mode_region = tileResourceLoader.getTexture("scenes/menu/LevelMode.png");
    }
    private void loadGameSceneGraphics() {
        regionTileMap = this.tileResourceLoader.getTileTextures();
        tilesBorderRegion = tileResourceLoader.getTexture("scenes/game/borders.png");
        saveButtonRegion = tileResourceLoader.getTexture("scenes/game/save.png");
    }

    public void unloadSplashSceneResources() {
        splash_region.getTexture().unload();
        splash_region = null;
    }

    public void unloadGameSceneResources(){
        unloadGameSceneTextures();
    }
    public void unloadMenuSceneResources(){
        unloadMenuSceneTextures();
    }

    private void unloadMenuSceneTextures() {
        level_mode_region.getTexture().unload();
        play_region.getTexture().unload();
        level_mode_region = null;
        play_region = null;
    }


    private void unloadGameSceneTextures() {
        for (TextureRegion textureRegion : regionTileMap.values()) {
            textureRegion.getTexture().unload();
        }
        regionTileMap = null;
        tilesBorderRegion.getTexture().unload();
        tilesBorderRegion = null;
        saveButtonRegion.getTexture().unload();
        saveButtonRegion = null;
    }

    public static void prepareManager(Engine engine, MainActivity activity, Camera camera, VertexBufferObjectManager vbom, TileResourceLoader tileResourceLoader) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
        getInstance().tileResourceLoader = tileResourceLoader;
    }

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }
}
