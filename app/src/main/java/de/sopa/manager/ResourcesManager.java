package de.sopa.manager;

import android.graphics.Color;
import de.sopa.GameActivity;
import de.sopa.helper.LevelService;

import java.util.Map;

import org.andengine.audio.music.Music;
import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * David Schilling - davejs92@gmail.com
 */
public class ResourcesManager {
    private static final ResourcesManager INSTANCE = new ResourcesManager();
    public SceneService sceneService;

    public Engine engine;
    public GameActivity activity;
    public Camera camera;
    public VertexBufferObjectManager vbom;

    private ResourceLoader resourceLoader;

    public ITextureRegion splash_region;

    public ITextureRegion level_mode_region;
    public ITextureRegion play_region;
    public ITextureRegion settingsRegion;
    public ITextureRegion loadingScreenBackgroundRegion;

    public Map<Character, TextureRegion> regionTileMap;
    public ITextureRegion tilesBorderRegion;
    public ITextureRegion saveButtonRegion;
    public ITextureRegion levelChoiceRegion;
    public ITextureRegion levelChoiceArrowRightRegion;
    public ITextureRegion levelChoiceArrowLeftRegion;
    public TextureRegion levelChoiceRegionLocked;
    public ITextureRegion starRegion;
    public TextureRegion starSWRegion;
    public TextureRegion backToChoiceRegion;
    public ITextureRegion muteRegion;
    public TextureRegion nextLevelRegion;
    public TextureRegion levelChoiceRegionSW;
    private TextureRegion unMuteRegion;

    public IFont scoreFont;
    public IFont levelChoiceFont;
    public IFont scoreCompleteFont;

    public IFont levelChoiceSWFont;

    public LevelService levelService;
    public Music menuMusic;
    public boolean musicIsPlaying = false;

    public void loadSplashSceneResources() {
        splash_region = resourceLoader.getTexture("scenes/splash/CouchStudio.png");
    }
    public void loadMenuSceneResources() {
        loadMenuSceneGraphics();
        loadMenuSceneMusic();
    }


    public void loadLoadingSceneResources() {
        loadingScreenBackgroundRegion = resourceLoader.getTexture("scenes/loading/LoadingScreen2.png");
    }

    public void loadGameSceneResources() {
        loadGameSceneGraphics();
        scoreFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 200, Color.WHITE, 2, Color.RED);

    }

    public void loadLevelChoiceSceneResources() {
        levelChoiceRegion = resourceLoader.getTexture("scenes/levelChoice/Level.png");
        levelChoiceRegionSW = resourceLoader.getTexture("scenes/levelChoice/LevelSW.png");
        levelChoiceRegionLocked = resourceLoader.getTexture("scenes/levelChoice/LevelSW.png");
        levelChoiceArrowLeftRegion = resourceLoader.getTexture("scenes/levelChoice/ArrowLeft.png");
        levelChoiceArrowRightRegion = resourceLoader.getTexture("scenes/levelChoice/ArrowRight.png");
        levelChoiceFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 110, 0xFFca540f, 0, 0xFFca540f);
        levelChoiceSWFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 110, 0xFF808080, 0, 0xFF808080);
    }
    public void loadScoreSceneResources() {
        scoreCompleteFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 220, Color.WHITE, 0, 0x00000000);
        starRegion =  resourceLoader.getTexture("scenes/score/star.png");
        starSWRegion =  resourceLoader.getTexture("scenes/score/starSW.png");
        nextLevelRegion = resourceLoader.getTexture("scenes/score/NextLevel.png");
        backToChoiceRegion = resourceLoader.getTexture("scenes/score/LevelChoice.png");
    }

    private void loadMenuSceneGraphics() {
        play_region = resourceLoader.getTexture("scenes/menu/JustPlay.png");
        level_mode_region = resourceLoader.getTexture("scenes/menu/LevelMode.png");
        settingsRegion = resourceLoader.getTexture("scenes/menu/Settings.png");
    }
    private void loadMenuSceneMusic() {
        menuMusic = resourceLoader.getMusic("scenes/menu/Menu_Theme.mp3", engine.getMusicManager(), activity);
    }


    private void loadGameSceneGraphics() {
        regionTileMap = this.resourceLoader.getTileTextures();
        tilesBorderRegion = resourceLoader.getTexture("scenes/game/borders.png");
        saveButtonRegion = resourceLoader.getTexture("scenes/game/save.png");
    }

    public void unloadSplashSceneResources() {
        splash_region.getTexture().unload();
        splash_region = null;
    }
    public void unloadGameSceneResources(){
        unloadGameSceneTextures();
    }

    public void unloadLevelChoiceSceneResources() {
        levelChoiceRegion.getTexture().unload();
        levelChoiceArrowLeftRegion.getTexture().unload();
        levelChoiceArrowRightRegion.getTexture().unload();
        levelChoiceRegion = null;
        levelChoiceArrowRightRegion = null;
        levelChoiceArrowLeftRegion = null;
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
        scoreFont.getTexture().unload();
        scoreFont = null;
    }

    public static void prepareManager(Engine engine, GameActivity activity, Camera camera, VertexBufferObjectManager vbom, ResourceLoader resourceLoader, SceneService sceneService, LevelService levelService) {
        getInstance().engine = engine;
        getInstance().activity = activity;
        getInstance().camera = camera;
        getInstance().vbom = vbom;
        getInstance().resourceLoader = resourceLoader;
        getInstance().sceneService = sceneService;
        getInstance().levelService = levelService;
    }

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public void unloadScoreSceneResources() {
        scoreCompleteFont.getTexture().unload();
        starRegion.getTexture().unload();
        starSWRegion.getTexture().unload();
        nextLevelRegion.getTexture().unload();
        backToChoiceRegion.getTexture().unload();

        scoreCompleteFont = null;
        starRegion = null;
        starSWRegion = null;
        nextLevelRegion = null;
        backToChoiceRegion = null;
    }

    public void loadSettingsScene() {
        muteRegion = resourceLoader.getTexture("scenes/settings/mute.png");
        unMuteRegion = resourceLoader.getTexture("scenes/settings/mute.png");
    }
}
