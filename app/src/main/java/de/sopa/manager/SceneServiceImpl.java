package de.sopa.manager;


import de.sopa.model.game.Level;
import de.sopa.model.game.LevelResult;
import de.sopa.scene.BaseScene;
import de.sopa.scene.loading.LoadingScene;
import org.andengine.engine.Engine;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */
public class SceneServiceImpl implements SceneService {

    private final JustPlaySceneService justPlaySceneService;
    private final LevelModeSceneService levelModeSceneService;
    private final MenuSceneService menuSceneService;
    private final CreditsSceneServiceImpl creditsSceneService;
    private SettingSceneService settingsSceneService;
    private BaseScene loadingScene;

    private BaseScene currentScene;
    private Engine engine;
    private BaseSceneService currentSceneService;

    public SceneServiceImpl(Engine engine) {
        this.engine = engine;
        this.justPlaySceneService = new JustPlaySceneServiceImpl(engine);
        this.levelModeSceneService = new LevelModeSceneServiceImpl(engine);
        this.menuSceneService = new MenuSceneServiceImpl(engine);
        this.creditsSceneService = new CreditsSceneServiceImpl(engine);
        this.settingsSceneService = new SettingSceneServiceImpl(engine);
    }

    private void setScene(BaseScene scene) {
        engine.setScene(scene);
        currentScene = scene;
    }

    private void startSceneService(BaseSceneService sceneService) {
        sceneService.start();
        currentSceneService = sceneService;
        currentScene = null;
    }


    @Override
    public BaseScene getCurrentScene() {
        return currentScene != null? currentScene: currentSceneService.getCurrentScene();
    }

    @Override
    public void createLoadingScene() {
        ResourcesManager.getInstance().loadLoadingSceneResources();
        loadingScene = new LoadingScene();
        currentScene = loadingScene;
    }


    @Override
    public void createMenuScene() {
        ResourcesManager.getInstance().loadLoadingSceneResources();
        menuSceneService.startSynchron();
        this.currentSceneService = menuSceneService;
    }

    @Override
    public void loadMenuSceneFromLevelChoiceScene() {
        setScene(loadingScene);
        endSceneService(levelModeSceneService);
        startSceneService(menuSceneService);
    }

    public void loadGameSceneFromLevelChoiceScene(final Level level) {
        levelModeSceneService.loadGameSceneFromLevelChoiceScene(level);
    }

    public void loadScoreScreen(final LevelResult level) {
        levelModeSceneService.loadScoreScreen(level);
    }


    public void loadLevelChoiceSceneFromMenuScene() {
        setScene(loadingScene);
        endSceneService(menuSceneService);
        startSceneService(levelModeSceneService);
    }

    @Override
    public void loadCreditsFromMenuScene() {
        setScene(loadingScene);
        menuSceneService.end();
        startSceneService(creditsSceneService);
    }

    @Override
    public void loadMenuSceneFromCreditsScene() {
        setScene(loadingScene);
        endSceneService(creditsSceneService);
        startSceneService(menuSceneService);
    }

    @Override
    public void loadLevelChoiceSceneFromScoreScene() {
        levelModeSceneService.loadLevelChoiceSceneFromScoreScene();
    }


    @Override
    public void loadGameSceneFromScoreScene(final Level level) {
        levelModeSceneService.loadGameSceneFromScoreScene(level);
    }

    @Override
    public void loadSettingsFromMenuScene() {
        setScene(loadingScene);
        endSceneService(menuSceneService);
        startSceneService(settingsSceneService);
    }

    @Override
    public void loadMenuSceneFromSettingsScene() {
        setScene(loadingScene);
        endSceneService(settingsSceneService);
        startSceneService(menuSceneService);
    }

    @Override
    public void loadMenuSceneFromScoreScene() {
        setScene(loadingScene);
        endSceneService(levelModeSceneService);
        startSceneService(menuSceneService);
    }

    @Override
    public void loadGameSceneFromGameScene(final Level level) {
        levelModeSceneService.loadGameSceneFromGameScene(level);
    }

    @Override
    public void loadTutorialSceneFromLevelChoiceScene() {
        levelModeSceneService.loadTutorialSceneFromLevelChoiceScene();
    }

    @Override
    public void loadFirstLevelFromTutorial() {
        levelModeSceneService.loadFirstLevelFromTutorial();
    }

    @Override
    public void loadLevelChoiceFromTutorial() {
        levelModeSceneService.loadLevelChoiceFromTutorial();
    }


    public void loadLevelChoiceSceneFromGameScene() {
        levelModeSceneService.loadLevelChoiceSceneFromGameScene();
    }

    @Override
    public void loadJustPlaySceneSceneFromMenuScene() {
        setScene(loadingScene);
        endSceneService(menuSceneService);
        startSceneService(justPlaySceneService);
    }


    @Override
    public void loadMenuSceneFromJustPlayGameScene() {
        setScene(loadingScene);
        endSceneService(justPlaySceneService);
        menuSceneService.start();
    }

    @Override
    public void loadJustPlaySceneFromJustPlayScoreScene() {
        justPlaySceneService.loadJustPlaySceneFromJustPlayScoreScene();
    }

    @Override
    public void loadJustPlayScoreSceneSceneFromJustPlaySceneScene(final Level level) {
        justPlaySceneService.loadJustPlayScoreSceneSceneFromJustPlaySceneScene(level);
    }


    private void endSceneService(BaseSceneService sceneService) {
        sceneService.end();
        this.currentSceneService = null;
    }
}
