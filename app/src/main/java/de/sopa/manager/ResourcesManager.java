package de.sopa.manager;

import android.graphics.Color;
import android.media.MediaPlayer;
import de.sopa.GameActivity;
import de.sopa.R;
import de.sopa.helper.LevelService;
import java.util.Map;
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
    public ITextureRegion levelChoiceRegionLocked;
    public ITextureRegion starRegion;
    public ITextureRegion starSWRegion;
    public ITextureRegion backToChoiceRegion;
    public ITextureRegion muteRegion;
    public ITextureRegion nextLevelRegion;
    public ITextureRegion levelChoiceRegionSW;
    public ITextureRegion backToMenuRegionA;
    public ITextureRegion backToMenuRegionP;
    public ITextureRegion restartRegion;
    private ITextureRegion unMuteRegion;

    public IFont scoreFont;
    public IFont levelChoiceFont;
    public IFont movesScoreFont;
    public IFont settingsFont;

    public IFont levelChoiceSWFont;
    public LevelService levelService;
    private boolean preparedTextures = false;
    public int justPlayMoves = 1;
    public MusicService musicService;

    public void loadSplashSceneResources() {
        splash_region = resourceLoader.getTexture("scenes/splash/CouchStudio.png");
        splash_region.getTexture().load();
    }

    public void loadMenuSceneResources() {
        loadMenuSceneGraphics();
    }


    public void loadLoadingSceneResources() {
        loadingScreenBackgroundRegion = resourceLoader.getTexture("scenes/loading/LoadingScreen2.png");
        loadingScreenBackgroundRegion.getTexture().load();
        //TODO: Unload loadingScreenBackgroundRegion
    }

    public void loadGameSceneResources() {
        loadGameSceneGraphics();
        scoreFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 200, Color.WHITE, 2, Color.RED);

    }

    public void loadLevelChoiceSceneResources() {
        prepareTextures();
        levelChoiceRegion.getTexture().load();
        levelChoiceRegionSW.getTexture().load();
        levelChoiceRegionLocked.getTexture().load();
        levelChoiceArrowLeftRegion.getTexture().load();
        levelChoiceArrowRightRegion.getTexture().load();
        levelChoiceFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 110, 0xFFca540f, 0, 0xFFca540f);
        levelChoiceSWFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 110, 0xFF808080, 0, 0xFF808080);
    }

    public void loadScoreSceneResources() {
        prepareTextures();
        movesScoreFont = resourceLoader.getFont("DroidSans-Bold.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 100, Color.WHITE, 0, 0x00000000);
        starRegion.getTexture().load();
        starSWRegion.getTexture().load();
        nextLevelRegion.getTexture().load();
        backToChoiceRegion.getTexture().load();
        backToMenuRegionA.getTexture().load();
        backToMenuRegionP.getTexture().load();

    }

    private void loadMenuSceneGraphics() {
        prepareTextures();
        play_region.getTexture().load();
        level_mode_region.getTexture().load();
        settingsRegion.getTexture().load();
    }


    private void loadGameSceneGraphics() {
        prepareTextures();
        regionTileMap = this.resourceLoader.getTileTextures();
        for (TextureRegion textureRegion : regionTileMap.values()) {
            textureRegion.getTexture().load();
        }
        tilesBorderRegion.getTexture().load();
        saveButtonRegion.getTexture().load();
        restartRegion.getTexture().load();
    }

    public void unloadSplashSceneResources() {
        splash_region.getTexture().unload();
        splash_region = null;
    }

    public void unloadGameSceneResources() {
        unloadGameSceneTextures();
    }

    public void unloadLevelChoiceSceneTextures() {
        levelChoiceRegion.getTexture().unload();
        levelChoiceRegionSW.getTexture().unload();
        levelChoiceRegionLocked.getTexture().unload();
        levelChoiceArrowLeftRegion.getTexture().unload();
        levelChoiceArrowRightRegion.getTexture().unload();
        levelChoiceFont.getTexture().unload();
        levelChoiceSWFont.getTexture().unload();

        levelChoiceFont = null;
        levelChoiceSWFont = null;
    }


    public void unloadMenuSceneResources() {
        unloadMenuSceneTextures();
    }

    private void unloadMenuSceneTextures() {
        level_mode_region.getTexture().unload();
        play_region.getTexture().unload();
        settingsRegion.getTexture().unload();
    }

    private void unloadGameSceneTextures() {
        for (TextureRegion textureRegion : regionTileMap.values()) {
            textureRegion.getTexture().unload();
        }
        restartRegion.getTexture().unload();
        tilesBorderRegion.getTexture().unload();
        saveButtonRegion.getTexture().unload();
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
        getInstance().musicService = new MusicService(MediaPlayer.create(activity.getApplicationContext(), R.raw.theme), true);
    }

    public static ResourcesManager getInstance() {
        return INSTANCE;
    }

    public void unloadScoreSceneResources() {
        movesScoreFont.getTexture().unload();
        starRegion.getTexture().unload();
        starSWRegion.getTexture().unload();
        nextLevelRegion.getTexture().unload();
        backToChoiceRegion.getTexture().unload();
        backToMenuRegionA.getTexture().unload();
        backToMenuRegionP.getTexture().unload();
    }

    public void loadSettingsScene() {
        prepareTextures();
        muteRegion.getTexture().load();
        unMuteRegion.getTexture().load();
        settingsFont = resourceLoader.getFont("Impact.ttf", TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA, 100, Color.BLUE, 0, Color.BLUE);
    }

    public void unloadSettingsScene() {
        muteRegion.getTexture().unload();
        unMuteRegion.getTexture().unload();
        settingsFont.unload();
        settingsFont = null;
    }

    public void prepareTextures() {
        if (!preparedTextures) {
            levelChoiceRegion = resourceLoader.getTexture("scenes/levelChoice/Level.png");
            levelChoiceRegionSW = resourceLoader.getTexture("scenes/levelChoice/LevelSW.png");
            levelChoiceRegionLocked = resourceLoader.getTexture("scenes/levelChoice/LevelSW.png");
            levelChoiceArrowLeftRegion = resourceLoader.getTexture("scenes/levelChoice/ArrowLeft.png");
            levelChoiceArrowRightRegion = resourceLoader.getTexture("scenes/levelChoice/ArrowRight.png");

            starRegion = resourceLoader.getTexture("scenes/score/star.png");
            starSWRegion = resourceLoader.getTexture("scenes/score/starSW.png");
            nextLevelRegion = resourceLoader.getTexture("scenes/score/NextLevel.png");
            backToChoiceRegion = resourceLoader.getTexture("scenes/score/LevelChoice.png");
            backToMenuRegionA = resourceLoader.getTexture("scenes/score/BackA.png");
            backToMenuRegionP = resourceLoader.getTexture("scenes/score/BackP.png");

            play_region = resourceLoader.getTexture("scenes/menu/JustPlay.png");
            level_mode_region = resourceLoader.getTexture("scenes/menu/LevelMode.png");
            settingsRegion = resourceLoader.getTexture("scenes/menu/Settings.png");

            regionTileMap = this.resourceLoader.getTileTextures();
            tilesBorderRegion = resourceLoader.getTexture("scenes/game/borders.png");
            saveButtonRegion = resourceLoader.getTexture("scenes/game/save.png");
            restartRegion = resourceLoader.getTexture("scenes/game/Restart.png");

            muteRegion = resourceLoader.getTexture("scenes/settings/mute.png");
            unMuteRegion = resourceLoader.getTexture("scenes/settings/mute.png");

        }
        preparedTextures = true;
    }
}
