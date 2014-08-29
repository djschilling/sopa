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
    public ITextureRegion splash_region;

    public ITextureRegion play_region;

    public Map<Character, TextureRegion> regionTileMap;
    private TileResourceLoader tileResourceLoader;
    public ITextureRegion loadingScreenBackgroundRegion;
    public ITextureRegion level_mode_region;
    public ITextureRegion gameBackgroundRegion;
    public ITextureRegion tilesBorderRegion;


    public void loadMenuResources() {
        loadMenuGraphics();
    }

    public void loadGameResources() {
        loadGameGraphics();
    }

    private void loadMenuGraphics() {
        play_region = tileResourceLoader.getTexture("screens/JustPlay.png");
        level_mode_region = tileResourceLoader.getTexture("screens/LevelMode.png");
    }
    private void loadGameGraphics() {
        regionTileMap = this.tileResourceLoader.getTileTextures();
        gameBackgroundRegion = tileResourceLoader.getTexture("screens/stonebackoground.png");
        tilesBorderRegion = tileResourceLoader.getTexture("tiles/borders.png");
    }

    public void loadLoadingResources() {
        loadingScreenBackgroundRegion = tileResourceLoader.getTexture("screens/LoadingScreen.png");
    }

    public void loadSplashScreen() {
        splash_region = tileResourceLoader.getTexture("splash/splash.png");
    }

    public void unloadSplashScreen() {
        splash_region = null;
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

    public void unloadGameTextures() {
        for (TextureRegion textureRegion : regionTileMap.values()) {
            textureRegion.getTexture().unload();
        }
        regionTileMap = null;
    }

    public void unloadMenuTextures() {

    }
}
